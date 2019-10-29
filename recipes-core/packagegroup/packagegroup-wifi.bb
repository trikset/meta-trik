SUMMARY = "Wifi tools"
DESCRIPTION = "The minimal set of packages required to manupulate wifi"
LICENSE = "LGPL"
PR = "r1"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup


MACHINE_ESSENTIAL_EXTRA_RDEPENDS ?= ""
MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS ?= ""


RDEPENDS_${PN} = "\
  netcat-openbsd \
  wpa-supplicant \
  wireless-tools \
  hostapd \
  trik-webpanel \
  linux-firmware-trik-wl1271 \
  tcpdump \
  ${MACHINE_ESSENTIAL_EXTRA_RDEPENDS} \
 "

RRECOMMENDS_${PN} = "\
  ${MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS} \
 "

