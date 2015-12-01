SUMMARY = "SocketCAN Driver for USB-CANmodul series"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe"
inherit module

PR= "r0"
PV= "0.9.1"

SRC_URI = "http://www.systec-electronic.com/uploads/60/06/60062f954039143d3f2f0ed8cce2c078/systec_can-V${PV}.tar.bz2 \
	   file://Makefile_fix.patch"
SRC_URI[md5sum] = "763acee6386e554b13438d2713b05578"
SRC_URI[sha256sum] = "a13424906b39637f6d609d621be89c8d57835a2f53b51e490623dd31b38fd2dd"

S= "${WORKDIR}/systec_can/"
module_do_install_append(){
	oe_runmake  INSTALL_MOD_PATH="${D}" firmware_install
}
PACKAGES += "linux-firmware-${PN}"

FILES_linux-firmware-${PN} = "/lib/firmware/*.fw"
