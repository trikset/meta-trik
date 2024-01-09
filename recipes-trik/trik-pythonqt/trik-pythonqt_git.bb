DECRIPTION = "PythonQt with TRIK build patches"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=fbc093901857fcd118f065f900982c24"

inherit pkgconfig qmake5

DEPENDS += "python3 qtbase"
SRCREV="${AUTOREV}"
SRC_URI = "git://github.com/trikset/trik-pythonqt.git;protocol=https;branch=a593de3f2d6760053a8931454f0392f1181f41ca \
           file://cpython-path.patch \
           "
#SRC_URI = "git:///home/iakov/trik/trik-pythonqt;branch=trik_qt58"

S = "${WORKDIR}/git"
EXTRA_QMAKEVARS_PRE += "ARCHITECTURE=arm"
#EXTRA_OEMAKE += "-C src"
PYTHONQTALL_CONFIG = "PythonQtCore PythonQtGui PythonQtMultimedia"
export PYTHONQTALL_CONFIG
do_install() {
	oe_libinstall -so -C ${WORKDIR}/build/src 'libPythonQt-Qt5*-Python*' ${D}${libdir}
	install -m 0644 -D -t ${D}${includedir}/pythonqt ${S}/src/*.h

}
