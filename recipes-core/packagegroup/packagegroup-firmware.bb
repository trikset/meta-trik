SUMMARY = "Firmware package"
DESCRIPTION = "Firmware for usb-wifi(ath9k,rt73),wl12xx,bluetooth"
LICENSE = "LGPL-2.0-only"
PR = "r2"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup


MACHINE_ESSENTIAL_EXTRA_RDEPENDS ?= ""
MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS ?= ""

RDEPENDS:${PN} = "\
  linux-firmware-wl12xx \
  linux-firmware-ath9k \
  linux-firmware-ralink \
  linux-firmware-msp \
  packagegroup-ti-drivers \
  ${MACHINE_ESSENTIAL_EXTRA_RDEPENDS} \
 "

RRECOMMENDS:${PN} = "\
  ${MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS} \
 "

