include ${COREBASE}/meta/recipes-bsp/u-boot/u-boot.inc

COMPATIBLE_MACHINE = "trikboard"

BRANCH = "trik-u-boot-2013.01.y"
SRCREV = "${AUTOREV}"
# u-boot.inc does not set INC_PR variable, so pretend that it is "r0"
PR = "r0.4"
SRC_URI_trikboard = "git://github.com/trikset/trik-u-boot.git;protocol=https;branch=${BRANCH} \
                     file://update_uboot.sh \
                     file://u-boot.cmd \
                     "

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM="file://COPYING;beginline=1;endline=306;md5=1707d6db1d42237583f50183a5651ecb"

P="${datadir}/${PN}"
PACKAGES += "${PN}-data"
FILES_${PN}-data = "${datadir} /u-boot.run"
RDEPENDS_${PN} += "${PN}-data"
DEPENDS += "dtc-native"

do_compile_append() {
  mkimage -T script -C none -n 'Demo Script File' -d ${WORKDIR}/u-boot.cmd ${WORKDIR}/u-boot.scr
}

do_install_append () {
  install -p -D -m 0755 -t ${D}${datadir}/trik/init.d/ ${WORKDIR}/update_uboot.sh
  install -p -D -m 0644 -t ${D}${P} ${WORKDIR}/u-boot.scr
  ln -s ${P}/u-boot.scr ${D}/u-boot.run
}
