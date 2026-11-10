#!/bin/sh
#Set/reset hostname from MAC

TRIK_HOSTNAME_DIR=/etc/trik
TRIK_HOSTNAME_FILE=${TRIK_HOSTNAME_DIR}/hostname
WLAN_MAC=/sys/class/net/wlan0/address

if [ ! -d ${TRIK_HOSTNAME_DIR} ]; then
        mkdir -p ${TRIK_HOSTNAME_DIR}
fi

if [ ! -f ${TRIK_HOSTNAME_FILE} ] && [ -s ${WLAN_MAC} ]; then
        echo "trik-$(tr -d ':' < ${WLAN_MAC} | tail -c 7 )" > ${TRIK_HOSTNAME_FILE}
fi

if [ ! -L /etc/hostname ]; then
        ln -s -f ${TRIK_HOSTNAME_FILE} /etc/hostname
fi

HN="$(cat ${TRIK_HOSTNAME_FILE} 2>/dev/null)"

if [ -n "${HN}" ]; then
        if grep -q '^127\.0\.1\.1[[:space:]]' /etc/hosts 2>/dev/null; then
                sed -i "s|^127\.0\.1\.1[[:space:]].*|127.0.1.1 ${HN}|" /etc/hosts
        else
                printf '127.0.1.1 %s\n' "${HN}" >> /etc/hosts
        fi
        hostname "${HN}"
fi
