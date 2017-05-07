DESCRIPTION = "Meta package for building an installable TRIK toolchain"
LICENSE = "MIT"
PR = "r2"

LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"

require recipes-qt/meta/meta-toolchain-qt5.bb

TOOLCHAIN_HOST_TASK += "nativesdk-packagegroup-trik-toolchain-host"
TOOLCHAIN_TARGET_TASK += "packagegroup-trik-toolchain-target"

#TOOLCHAIN_TARGET_TASK += "packagegroup-ros-comm"
TOOLCHAIN_OUTPUTNAME = "${SDK_NAME}-toolchain-trik-${DISTRO_VERSION}"

