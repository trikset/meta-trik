include ${COREBASE}/meta/recipes-bsp/u-boot/u-boot.inc

SECTION= "bootloader"

PROVIDES = "virtual/bootloader"

LICENSE = "GPLv2+"

COMPATIBLE_MACHINE = "trikboard"

PACKAGE_ARCH = "${MACHINE_ARCH}"
BRANCH = "trik-u-boot-2013.01.y"
SRCREV = "${AUTOREV}"
# u-boot.inc does not set INC_PR variable, so pretend that it is "r0"
PR = "r0.2"
SRC_URI_trikboard = "git://github.com/trikset/trik-u-boot.git;protocol=https;branch=${BRANCH} \
                     file://reset_uboot.sh file://update_uboot.sh \
                     file://u-boot-gzip.ais \
                     file://u-boot.cmd \
                     "

#UBOOT_LOCALVERSION = "-git${@d.getVar('SRCPV', True).partition('+')[2][0:7]}"
#SPL_BINARY="spl/u-boot-spl.ais"
#for morty
UBOOT_MAKE_TARGET="${B}/u-boot-gzip.ais"
#UBOOT_MAKE_TARGET="u-boot-gzip.ais"
PARALLEL_MAKE=""
LIC_FILES_CHKSUM="file://COPYING;beginline=1;endline=306;md5=1707d6db1d42237583f50183a5651ecb"

#S = "${WORKDIR}/git"

UBOOT_MACHINE = "trikboard_config"

#BBCLASSEXTEND = "native nativesdk"
P="${datadir}/${PN}"
PACKAGES += "${PN}-data"
FILES_${PN}-data = "${datadir} /u-boot.run"
RDEPENDS_${PN} += "${PN}-data"

do_compile_append() {
  mkimage -T script -C none -n 'Demo Script File' -d ${WORKDIR}/u-boot.cmd ${WORKDIR}/u-boot.scr
}

do_install_append () {
  install -p -D -t ${D}${P} ${WORKDIR}/reset_uboot.sh ${WORKDIR}/u-boot-gzip.ais
  install -p -D -m 0755 -t ${D}${datadir}/trik/init.d/ ${WORKDIR}/update_uboot.sh
  install -p -D -m 0644 -t ${D}${P} ${WORKDIR}/u-boot.scr
  ln -s ${P}/u-boot.scr ${D}/u-boot.run
}

do_deploy_append() {
#  install -p -D -m 0644 -t ${DEPLOYDIR} ${UBOOT_MAKE_TARGET}
   install -p -D -m 0644 -t ${DEPLOYDIR} ${WORKDIR}/u-boot-gzip.ais
}

