#!/bin/sh
#Set/reset hostname from MAC


TRIK_USER_PARTION=_path

if [ ! -d ${TRIK_USER_PARTION} ]; then
        mkdir -p ${}
fi
if [ ! -f ${TRIK_USER_PARTION}/hostname ]; then
        echo "trik-$(cat /sys/class/net/wlan0/address | tail -c 9 | sed 's/://g')" > ${TRIK_USER_PARTION}/hostname
fi

if [ ! -L /etc/hostname ]; then
        ln -s -f ${TRIK_USER_PARTION}/hostname /etc/hostname
fi


