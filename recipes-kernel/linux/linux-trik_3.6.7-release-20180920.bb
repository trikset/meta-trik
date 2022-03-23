SECTION = "Kernel"
DESCRIPTION = "Linux Kernel for DaVinci TRIK linux"
LICENSE = "GPLv2"
KERNEL_IMAGETYPE = "uImage"
KERNEL_VERSION ="3.6.7"

PR = "r4"

inherit kernel

KERNEL_MODULE_AUTOLOAD += "jex_epwm"
KERNEL_MODULE_AUTOLOAD += "jcx_pwm"

MULTI_CONFIG_BASE_SUFFIX = ""
SRCREV="${AUTOREV}"
SRC_URI = "git://github.com/trikset/trik-linux;protocol=https;protocol=https;branch=trik-linux-3.6.7-master \
	   file://defconfig \
           file://display_settings.sh \
           "

S = "${WORKDIR}/git"
#lxdialog-based menuconfig fails to build with new curses5  in kernel 3.6
KCONFIG_CONFIG_COMMAND = "nconfig"
LIC_FILES_CHKSUM="file://COPYING;beginline=1;endline=355;md5=bad9197b13faffd10dfc69bd78fd072e"

PACKAGES += "${PN}-data"
FILES_${PN}-data = "${datadir} ${sysconfdir}"
RDEPENDS_${PN} += "${PN}-data"

do_install_append(){
    install -p -D -m 0755 -t ${D}${sysconfdir}/trik/ ${WORKDIR}/display_settings.sh
}
