HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
SECTION = "bootloaders"
DEPENDS += "flex-native bison-native"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"
PE = "1"

COMPATIBLE_MACHINE = "trikboard"

BRANCH = "trik-u-boot-2021.10"
SRCREV = "${AUTOREV}"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI = "git://github.com/iakov/trik-u-boot.git;protocol=https;branch=${BRANCH}"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"
