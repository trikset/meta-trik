#!/bin/sh

MCU_VERSION=`i2cget -y 2  0x48 0xee w`

case ${MCU_VERSION} in
     0x10* ) IS_OLD_CONTROLLER=1 ;;
     0x28* ) IS_OLD_CONTROLLER='' ;;
     *) echo ERROR in version detection; exit 1 ;;
esac

VIDEO0=/dev/video0
VIDEO1=/dev/video1

case $1 in  
  0|"$VIDEO0")
    BUS=0x1
    VIDEO_PATH=$VIDEO0
    echo 0 > /sys/class/gpio/gpio109/value
    usleep 10000
    echo 1 > /sys/class/gpio/gpio109/value
    ;;
  1|"$VIDEO1")
    BUS=0x2
    VIDEO_PATH=$VIDEO1
    echo 0 > /sys/class/gpio/gpio33/value
    usleep 10000
    echo 1 > /sys/class/gpio/gpio33/value
    ;;
  *)
    USAGE_TEXT="Inits ov7670. Usage:\n\t 1, $VIDEO1 \n\t 0, $VIDEO0\n"
    printf "$USAGE_TEXT"
    exit 1
    ;;
esac

cam () {
  i2cset -y $BUS 0x21 $1 $2 || exit $?
}


# Resets all registers to default values
cam 0x12 0x80
#sleep 1
cam 0x12 0x00

#CCIR656 enable
cam 0x04 0x40

# Output range: [01] to [FE]
cam 0x40 0x80

# set QVGA according to
# Table 2-2. (but without input clock divider
# and without SCALING_PCLK_DELAY)
# OV7670/OV7171 CMOS VGA (640x480) CameraChip��™
# Implementation Guide
cam 0x12 0 # YUV
cam 0xc  4
cam 0x3e 0x19
cam 0x70 0x3A
cam 0x71 0x35
cam 0x72 0x11
cam 0x73 0xf1
# this was set to 2 in the guide, but it makes
# image width less that 320. Setting this to 0
# helps but may spoil left/right boundary of
# the video
cam 0xa2 0x00

# make the original window (before downsampling)
# 6 pixels wider in order to reach 320 pixel width
# at the output. Use reg 0x32 for that
cam 0x3a 0x8
#cam 0x17 0x11
#cam 0x18 0x61
cam 0x32  0xb0  # 0xb0 = 0x80 + (6 << 3)


#cam 0x14 0x0a # max gain is 2x

cam 0xb0 0x84 #change green/purple stuff on true colors
# Output sequence 01: Y V Y U,
# Sensor automatically sets output window when
# resolution changes.
#cam 0x3a 0x09

# output drive 1x  // 4x
cam 0x09 0x0

#not so bright
# Automatic Gain Ceiling 4x
cam 0x14 0x1a

#turn on all stuff (awb/agc/aec)
cam 0x13 0x87

#simple AWB
cam 0x6f 0x6f

#pretty agc/aec
cam 0xa5 0x05
cam 0x24 0x95
cam 0x25 0x33
cam 0x26 0xe3
cam 0x9f 0x78 
cam 0xa0 0x68 
cam 0xa6 0xd8 
cam 0xa7 0xd8 
cam 0xa9 0x90 
cam 0xaa 0x94 

#banding filter
cam 0x9d 0x98
cam 0x9e 0x7f
cam 0xa5 0x02
cam 0xab 0x03
cam 0x3b 0x12

#double YUV
cam 0x41 0x0a

#yuv
cam 0x4f 0x80
cam 0x50 0x70
cam 0x51 0x1a
cam 0x52 0x28
cam 0x53 0x15
cam 0x54 0x40

#cam 0x20 0x0f //darker sharper

v4l2-ctl -d $VIDEO_PATH -s pal 1 > /dev/null
echo "ov7670 successfully initialised" 
echo
