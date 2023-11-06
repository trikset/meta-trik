require u-boot-trik-common_${PV}.inc
require ${COREBASE}/meta/recipes-bsp/u-boot/u-boot.inc

DEPENDS += "bc-native dtc-native"

SRC_URI_trikboard += "file://update_uboot.sh"

P="${datadir}/${PN}"
FILES:${PN} += "${datadir}/trik"

do_install:append () {
  install -p -D -m 0755 -t ${D}${datadir}/trik/init.d/ ${WORKDIR}/update_uboot.sh
}

do_deploy:append() {
   install -p -D -m 0644 -t ${DEPLOYDIR} ${B}/${UBOOT_BINARY}
}
