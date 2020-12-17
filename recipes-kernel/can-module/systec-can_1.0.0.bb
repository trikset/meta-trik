SUMMARY = "SocketCAN Driver for USB-CANmodul series"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe"
inherit module

SRC_URI = "https://www.systec-electronic.com/fileadmin/Redakteur/Unternehmen/Support/Downloadbereich/Treiber/systec_can-V${PV}.tar.bz2 \
	file://Makefile_fix.patch \
  "

SRC_URI[md5sum] = "69fa5e0c5962f81d5c3121fc739eaa77"
SRC_URI[sha256sum] = "407da4796c2651af064f17a41eb8599bde6049829ddaef41663f37ff0b317db6"

S= "${WORKDIR}/systec_can/"
module_do_install_append(){
	oe_runmake  INSTALL_MOD_PATH="${D}" firmware_install
}
PACKAGES += "linux-firmware-${PN}"

FILES_linux-firmware-${PN} = "/lib/firmware/*.fw"
