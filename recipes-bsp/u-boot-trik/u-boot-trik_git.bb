include ${COREBASE}/meta/recipes-bsp/u-boot/u-boot.inc

SECTION= "bootloader"

PROVIDES = "virtual/bootloader"

LICENSE = "GPLv2+"

COMPATIBLE_MACHINE = "trikboard"

PACKAGE_ARCH = "${MACHINE_ARCH}"
BRANCH = "trik-u-boot-2013.01.y"
SRCREV = "${AUTOREV}"
SRC_URI_trikboard = "git://github.com/trikset/trik-u-boot.git;branch=${BRANCH} \
                     file://reset_uboot.sh file://update_uboot.sh"


SPL_BINARY="spl/u-boot-spl.ais"
UBOOT_MAKE_TARGET="u-boot-gzip.ais"
PARALLEL_MAKE=""
LIC_FILES_CHKSUM="file://COPYING;beginline=1;endline=306;md5=1707d6db1d42237583f50183a5651ecb"

S = "${WORKDIR}/git"

UBOOT_MACHINE = "trikboard_config"

BBCLASSEXTEND = "native nativesdk"
P="${datadir}/${PN}"
PACKAGES += "${PN}-data"
FILES_${PN}-data = "${datadir}"
do_configure_prepend () {
 git -C ${S} rev-parse HEAD > ${S}/.scmversion
}


do_compile_append () {
     ./tools/mkimage -s -n /dev/null -T aisimage -e 0x80000000 -d spl/u-boot-spl.bin spl/u-boot-spl.ais
}

do_install_append () {
  install -p -D -t ${D}${P} ${S}/u-boot-gzip.ais ${S}/spl/u-boot-spl.ais ${WORKDIR}/reset_uboot.sh
  install -p -D -m 0755 -t ${D}${datadir}/trik/init.d/ ${WORKDIR}/update_uboot.sh
}


