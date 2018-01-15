#!/bin/sh -e

hex2dec() {
    local res
    res=`printf "%d" "$1" 2>/dev/null`
    # do not return correct substring of incorrect string
    # if previous printf was incorrect, make another attempt
    if [ $? -ne 0 ] ; then
       res=`printf "%d" "0x$1" 2>/dev/null`
    fi
    echo $res
}


# If MSP does not respond we try to reset it and to re-flash
# Assume i2c-tools are installed ...
old=`i2cget -y 2  0x48 0xee w || echo 0xffff`

MCU=10
case ${old} in
 0xff* | 0x10* ) MCU=10 ;;
 0x28*) MCU=28 ;;
 *) echo ERROR in version detection ;;
esac

FWNAME=`/bin/ls /etc/trik/msp430/msp-firmware-$MCU*.txt | head -n 1`

getNewMspFwVer() {
    local ver
    ver=${FWNAME#*/msp-firmware-}
    ver=${ver%.txt}
    echo $ver
}

new=`getNewMspFwVer`

if [ -z "$new" ] ; then
  echo Missing new firmware!
elif test `hex2dec $new` -ne `hex2dec $old` ; then
    echo "Updating MSP firmware from $old to $new"
    /etc/trik/msp430/msp_reset.sh
    /usr/sbin/msp-flasher -o $FWNAME || echo Problem ONE
    /usr/sbin/msp-flasher -o $FWNAME || echo Problem TWO
fi
