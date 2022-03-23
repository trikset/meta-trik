SECTION = "Kernel"
DESCRIPTION = "Linux Kernel for DaVinci TRIK linux"
LICENSE = "GPLv2"
KERNEL_IMAGETYPE = "uImage"
KERNEL_VERSION ="3.6.7"

PR = "r18"

inherit kernel

KERNEL_MODULE_AUTOLOAD += "jex_epwm"
KERNEL_MODULE_AUTOLOAD += "jcx_pwm"

MULTI_CONFIG_BASE_SUFFIX = ""
SRC_URI = "git://github.com/trikset/trik-linux.git;branch=trik-linux-3.6.7-release-2016-06-20;protocol=https;tag=${BPN}-${PV} \
	   file://defconfig"

S = "${WORKDIR}/git"
#lxdialog-based menuconfig fails to build with new curses5  in kernel 3.6
KCONFIG_CONFIG_COMMAND = "nconfig"
LIC_FILES_CHKSUM="file://COPYING;beginline=1;endline=355;md5=bad9197b13faffd10dfc69bd78fd072e"

