DESCRIPTION = "Meta package for building an installable TRIK toolchain"
LICENSE = "MIT"
PR = "r2"

LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"

require recipes-qt4/meta/meta-toolchain-qt.bb

TOOLCHAIN_HOST_TASK += "nativesdk-packagegroup-trik-toolchain-host"

TOOLCHAIN_TARGET_TASK += "packagegroup-trik-toolchain-target"

TOOLCHAIN_OUTPUTNAME = "${SDK_NAME}-toolchain-trik-${DISTRO_VERSION}"

toolchain_create_sdk_env_script_append() {
    echo 'source ${SDKPATHNATIVE}/environment-setup.d/*' >> $script
}
