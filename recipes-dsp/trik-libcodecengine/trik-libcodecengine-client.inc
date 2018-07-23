DESCRIPTION="TRIK SDK client library for DSP access"
LICENSE="Apache-2.0"
LIC_FILES_CHKSUM="file://${WORKDIR}/include/libcodecengine-client/codecengine-client.h;md5=9018bee80a861fe367996142145ee4d8"
SRC_URI="https://github.com/trikset/trik-libcodecengine-client/releases/download/${PV}/${BPN}-dev.tar.xz"
SRC_URI[md5sum] = "de698b58760503020b70ff465b77175d"
ALLOW_EMPTY_${PN}="1"
do_install_append() {
 install -dpm 0666 ${D}${libdir}
 install -dpm 0666 ${D}${includedir}
 cp -r ${WORKDIR}/lib/* ${D}${libdir}
 cp -r ${WORKDIR}/include/* ${D}${includedir}
}
