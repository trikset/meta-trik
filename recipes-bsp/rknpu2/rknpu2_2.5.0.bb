SUMMARY = "Rockchip RKNPU2 runtime and API headers"
DESCRIPTION = "Prebuilt aarch64 userspace runtime (librknnrt) and API headers for the Rockchip RKNPU2 NPU"
HOMEPAGE = "https://github.com/airockchip/rknn-toolkit2"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE;md5=de9639f691adad4a331d323b41a41dba"

RKNN_TAG = "v${PV}"
RKNN_RAW = "https://raw.githubusercontent.com/airockchip/rknn-toolkit2/${RKNN_TAG}"

SRC_URI = " \
    ${RKNN_RAW}/rknpu2/runtime/Linux/librknn_api/aarch64/librknnrt.so;name=lib \
    ${RKNN_RAW}/rknpu2/runtime/Linux/librknn_api/include/rknn_api.h;name=api \
    ${RKNN_RAW}/rknpu2/runtime/Linux/librknn_api/include/rknn_custom_op.h;name=customop \
    ${RKNN_RAW}/rknpu2/runtime/Linux/librknn_api/include/rknn_matmul_api.h;name=matmul \
    ${RKNN_RAW}/LICENSE;name=license \
"
SRC_URI[lib.sha256sum] = "4b6cc575bedf8952b13db4b1190fa3b3d2ac213f00be774ff6ccd19d651970c0"
SRC_URI[api.sha256sum] = "2d4160cd868efd7b83a72587e411c16d6972da5876e067bd73665cea778ce666"
SRC_URI[customop.sha256sum] = "af5983da0ca244ca31dc3162aa683322b0285531196c7a770f29cd2e3b8ccaa6"
SRC_URI[matmul.sha256sum] = "aaadd9a7118de30a06b222996b6731db77095d00f5931a7a98c83a67f14a4d42"
SRC_URI[license.sha256sum] = "d005b4a9a787ac1023fc7d353d9f263047e892b4edb9207303915e4a29b93c8e"

S = "${WORKDIR}"

COMPATIBLE_HOST = "aarch64.*-linux"
PACKAGE_ARCH = "${MACHINE_ARCH}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_SYSROOT_STRIP = "1"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${libdir}
    install -m 0755 ${S}/librknnrt.so ${D}${libdir}/

    install -d ${D}${includedir}
    install -m 0644 ${S}/rknn_api.h ${S}/rknn_custom_op.h ${S}/rknn_matmul_api.h ${D}${includedir}/
}

FILES:${PN} = "${libdir}/librknnrt.so"
FILES:${PN}-dev = "${includedir}/rknn_api.h ${includedir}/rknn_custom_op.h ${includedir}/rknn_matmul_api.h"
FILES_SOLIBSDEV = ""
RDEPENDS:${PN}-dev += "${PN}"

INSANE_SKIP:${PN} += "already-stripped ldflags dev-so"
