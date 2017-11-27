#!/bin/sh -e
FWNAME=`/bin/ls /etc/trik/msp430/msp-firmware-*.hex | /usr/bin/head -n 1`
getNewMspFwVer() {
    local ver
    ver=${FWNAME#*/msp-firmware-}
    ver=${ver%.hex}
    echo $ver
}

hex2dec() {
    printf "%d" "$1" 2>/dev/null || printf "%d" "0x$1" 2>/dev/null
}

# let's assume i2c-tools are installed ....
NVER=`getNewMspFwVer`

# if MSP does not respond we try to reset it and to re-flash
OVER=`i2cget -y 2  0x48 0xee || echo 0`

if test `hex2dec $NVER` -ne `hex2dec $OVER` ; then 
    echo "Updating MSP firmware to version " $NVER
    /etc/trik/msp430/msp_reset.sh
    /usr/sbin/msp-flasher -o $FWNAME
fi

