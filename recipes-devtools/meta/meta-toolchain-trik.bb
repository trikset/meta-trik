DESCRIPTION = "Meta package for building an installable TRIK toolchain"
LICENSE = "MIT"
PR = "r2"

LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"

require recipes-qt/meta/meta-toolchain-qt5.bb


TOOLCHAIN_OUTPUTNAME = "${SDK_NAME}-toolchain-trik-${DISTRO_VERSION}"

