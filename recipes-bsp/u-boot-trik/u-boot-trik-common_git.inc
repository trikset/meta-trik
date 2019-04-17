HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
SECTION = "bootloaders"
DEPENDS += "flex-native bison-native"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"
PE = "1"

COMPATIBLE_MACHINE = "trikboard"

BRANCH = "trik-u-boot-2018.05"
SRCREV = "${AUTOREV}"

SRC_URI_trikboard = "git://github.com/trikset/trik-u-boot.git;branch=${BRANCH}"

S = "${WORKDIR}/git"
