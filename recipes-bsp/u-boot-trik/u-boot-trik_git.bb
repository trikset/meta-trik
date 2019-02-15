include ${COREBASE}/meta/recipes-bsp/u-boot/u-boot.inc

SECTION= "bootloader"

PROVIDES = "virtual/bootloader"

LICENSE = "GPLv2+"

COMPATIBLE_MACHINE = "trikboard"

PACKAGE_ARCH = "${MACHINE_ARCH}"
BRANCH = "trik-u-boot-2018.05"
SRCREV = "${AUTOREV}"
SRC_URI_trikboard = "git://github.com/trikset/trik-u-boot.git;branch=${BRANCH} \
                     file://reset_uboot.sh file://update_uboot.sh \
                     "

#UBOOT_LOCALVERSION = "-git${@d.getVar('SRCPV', True).partition('+')[2][0:7]}"
#SPL_BINARY="spl/u-boot-spl.ais"
#for morty
#UBOOT_MAKE_TARGET="${B}/u-boot-gzip.ais"
UBOOT_MAKE_TARGET="u-boot.ais"
PARALLEL_MAKE=""
LIC_FILES_CHKSUM = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"

S = "${WORKDIR}/git"

UBOOT_MACHINE = "trikboard_defconfig"

#BBCLASSEXTEND = "native nativesdk"
P="${datadir}/${PN}"
PACKAGES += "${PN}-data"
FILES_${PN}-data = "${datadir}"
RDEPENDS_${PN} += "${PN}-data"

do_install_append () {
  install -p -D -t ${D}${P} ${WORKDIR}/reset_uboot.sh ${B}/${UBOOT_MAKE_TARGET}
  install -p -D -m 0755 -t ${D}${datadir}/trik/init.d/ ${WORKDIR}/update_uboot.sh
}

do_deploy_append() {
   install -p -D -m 0644 -t ${DEPLOYDIR} ${B}/${UBOOT_MAKE_TARGET}
}

