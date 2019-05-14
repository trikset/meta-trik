SUMMARY = "SocketCAN Driver for USB-CANmodul series"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe"
inherit module

PR= "r2"
PV= "0.9.8"
SRC_URI = "https://www.systec-electronic.com/fileadmin/Redakteur/Unternehmen/Support/Downloadbereich/Treiber/systec_can-V${PV}.tar.bz2 \
   file://Makefile_fix.patch"

SRC_URI[md5sum] = "7abadcee12b91077ff53937cfaba83e3"
SRC_URI[sha256sum] = "eea2d95a75e1dc27c4ed17452a8d2b0e698cd0d86fdb703eb6c1f8cd1bb996df"

S= "${WORKDIR}/systec_can/"
module_do_install_append(){
	oe_runmake  INSTALL_MOD_PATH="${D}" firmware_install
}
PACKAGES += "linux-firmware-${PN}"

FILES_linux-firmware-${PN} = "/lib/firmware/*.fw"
