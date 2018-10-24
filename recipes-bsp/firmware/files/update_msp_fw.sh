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

getNewMspFwVer() {
    local ver
    ver=${1#*/msp-firmware-}
    ver=${ver%.txt}
    echo $ver
}

tryUpdate() {
    # If MSP does not respond we try to reset it and to re-flash
    # Assume i2c-tools are installed ...
    old=`i2cget -y 2  0x48 0xee w || echo 0xffff`
    
    MCU=10
    case ${old} in
     0xff* | 0x10* ) MCU=10 ;;
     0x28*) MCU=28 ;;
     *) echo ERROR in version detection ;;
    esac

    FWNAME=`/bin/ls /etc/trik/msp430/msp-firmware-$MCU*.txt | sort | tail -n 1`
    new=`getNewMspFwVer $FWNAME`
    if [ -z "$new" ] ; then
      echo Missing new firmware!
      return 2
    elif test `hex2dec $new` -ne `hex2dec $old` ; then
        echo "Updating MSP firmware from $old to $new"
        /etc/trik/msp430/msp_reset.sh
        /usr/sbin/msp-flasher -o $FWNAME || echo Problem ONE
        /usr/sbin/msp-flasher -o $FWNAME || echo Problem TWO
        return 1;
    else
        echo "MSP firmware ver. $new is up to date."
        return 0
    fi
}

tryUpdate || tryUpdate



