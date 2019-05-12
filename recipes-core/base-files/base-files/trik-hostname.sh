#!/bin/sh
#Set/reset hostname from MAC


TRIK_USER_PARTION=_path

if [ ! -d ${TRIK_USER_PARTION} ]; then
        mkdir -p ${TRIK_USER_PARTION}
fi
if [ ! -f ${TRIK_USER_PARTION}/hostname ]; then
        echo "trik-$(tr -d ':' < /sys/class/net/wlan0/address | tail -c 7 )" > ${TRIK_USER_PARTION}/hostname
fi

if [ ! -L /etc/hostname ]; then
        ln -s -f ${TRIK_USER_PARTION}/hostname /etc/hostname
fi


