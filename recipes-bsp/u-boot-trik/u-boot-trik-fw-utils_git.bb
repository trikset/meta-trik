SUMMARY = "U-Boot bootloader fw_printenv/setenv utilities"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"
SECTION = "bootloader"
DEPENDS = "mtd-utils"

BRANCH = "trik-u-boot-2013.01.y"
SRCREV = "${AUTOREV}"
FWENV = "file://fw_env.config"
SRC_URI_trikboard = "git://github.com/trikset/trik-u-boot.git;branch=${BRANCH} \
			${FWENV}"
S = "${WORKDIR}/git"

UBOOT_MACHINE = "trikboard_config"

EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX} HOSTCC="${CC}" V=1 HOSTSTRIP=true'

inherit uboot-config

do_compile () {
  oe_runmake ${UBOOT_MACHINE}
  oe_runmake env
}

do_install () {
  install -d ${D}${base_sbindir}
  install -m 755 ${S}/tools/env/fw_printenv ${D}${base_sbindir}/fw_printenv
  install -m 755 ${S}/tools/env/fw_printenv ${D}${base_sbindir}/fw_setenv
  #TODO: this option copy fw_env.config like u-boot   
  if [ -e ${WORKDIR}/fw_env.config ] ; then
        install -d ${D}${sysconfdir}
        install -m 644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}/fw_env.config
  fi
}

PACKAGE_ARCH = "${MACHINE_ARCH}"




