require u-boot-trik-common_${PV}.inc
require ${COREBASE}/meta/recipes-bsp/u-boot/u-boot.inc

DEPENDS += "bc-native dtc-native u-boot-tools-native"

SRC_URI += "file://update_uboot.sh \
	    file://u-boot.cmd \
	    "

P="${datadir}/${PN}"
FILES:${PN} += "${datadir}/trik /u-boot.run"

do_compile:append() {
  mkimage -T script -C none -n 'Script File' -d ${WORKDIR}/u-boot.cmd ${WORKDIR}/u-boot.scr
}

do_install:append () {
  install -p -D -m 0755 -t ${D}${datadir}/trik/init.d/ ${WORKDIR}/update_uboot.sh
  install -p -D -m 0644 -t ${D}${P} ${WORKDIR}/u-boot.scr
  ln -s ${P}/u-boot.scr ${D}/u-boot.run
}

do_deploy:append() {
   install -p -D -m 0644 -t ${DEPLOYDIR} ${B}/${UBOOT_BINARY}
}
