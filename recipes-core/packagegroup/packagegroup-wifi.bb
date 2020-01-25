SUMMARY = "Wifi tools"
DESCRIPTION = "The minimal set of packages required to manupulate wifi"
LICENSE = "LGPLv2"
PR = "r1"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup


MACHINE_ESSENTIAL_EXTRA_RDEPENDS ?= ""
MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS ?= ""


RDEPENDS_${PN} = "\
  netcat-openbsd \
  wpa-supplicant \
  packagegroup-base-wifi \
  hostapd \
  trik-webpanel \
  tcpdump \
  ti-wifi-utils \
  ${MACHINE_ESSENTIAL_EXTRA_RDEPENDS} \
 "

RRECOMMENDS_${PN} = "\
  ${MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS} \
 "

