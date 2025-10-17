#!/bin/sh -e

hex2dec() {
    res=`printf "%d" "$1" 2>/dev/null`
    # do not return correct substring of incorrect string
    # if previous printf was incorrect, make another attempt
    if [ $? -ne 0 ] ; then
       res=`printf "%d" "0x$1" 2>/dev/null`
    fi
    echo $res
}

getNewMspFwVer() {
    ver=${1#*/msp-firmware-}
    ver=${ver%.txt}
    echo $ver
}

tryUpdate() {
    set -eu
    # If MSP does not respond we try to reset it and to re-flash
    # Assume i2c-tools are installed ...
    old=$(i2cget -y 2  0x48 0xee w || echo 0xffff)
    
    MCU=10
    case ${old} in
     0xff* | 0x10* ) MCU=10 ;;
     0x28*) MCU=28 ;;
     *) echo "ERROR in version detection" ; exit 1 ;;
    esac

    FWNAME=$(/bin/ls /etc/trik/msp430/msp-firmware-$MCU*.txt | sort | tail -n 1)
    new=$(getNewMspFwVer $FWNAME)
    if [ -z "$new" ] ; then
      echo Missing new firmware!
      return 2
    elif test "$(hex2dec $new)" -ne "$(hex2dec $old)" ; then
        echo "Updating MSP firmware from $old to $new"
        /etc/trik/msp430/msp_reset.sh
        /usr/sbin/msp-flasher -o "$FWNAME"
    else
        echo "MSP firmware ver. $new is up to date."
    fi
    return 0
}

tryUpdate
# If MCU is fresh, there is no i2c responder and firmware variant is forced to 0x10*.
# Now, i2c is available and we can tell proper MCU variant.
# Thus, perform a second attempt to update firmware. It does not hurt anyway.
sleep 3 # give some time for MSP to boot
tryUpdate
