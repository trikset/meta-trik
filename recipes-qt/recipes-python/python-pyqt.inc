SUMMARY = "Python Qt5 Bindings"
AUTHOR = "Phil Thomson @ riverbank.co.uk"
HOMEPAGE = "http://riverbankcomputing.co.uk"
SECTION = "devel/python"
LICENSE = "GPLv2 & GPLv3 & GPL_EXCEPTION"
LIC_FILES_CHKSUM = "\
    file://GPL_EXCEPTION.TXT;md5=b73b0be471db679533dc94781c14af58 \
    file://GPL_EXCEPTION_ADDENDUM.TXT;md5=c1e04ec2aa0911061005a801abf81e40 \
    file://OPENSOURCE-NOTICE.TXT;md5=6ad9123620cc04a22c394753ad4767d7 \
    file://LICENSE.GPL2;md5=577ff65f6653562af318bfc3944b1f20 \
    file://LICENSE.GPL3;md5=feee51612c3c1191a1d5f41156fa2c75 \
"
SRC_URI = "\
    ${SOURCEFORGE_MIRROR}/pyqt/PyQt5_gpl-${PV}.tar.gz \
"
# PyQt 5.8
#SRC_URI[md5sum] = "ac04c0bfc1f05f5a1c2a1edd2640235c"
#SRC_URI[sha256sum] = "9edf2816105a4111a447452df2bef7ce47d7efb307c75c3e74c27b8d31d7f66e"

#PyQt 5.6.
SRC_URI[md5sum] = "dbfc885c0548e024ba5260c4f44e0481"
SRC_URI[sha256sum] = "2e481a6c4c41b96ed3b33449e5f9599987c63a5c8db93313bd57a6acbf20f0e1"

S = "${WORKDIR}/PyQt5_gpl-${PV}"

PARALLEL_MAKE = ""

inherit qmake5 pythonnative python-dir distro_features_check
DEPENDS += "sip sip-native python"
REQUIRED_DISTRO_FEATURES = "x11"

DISABLED_FEATURES = "PyQt_Desktop_OpenGL PyQt_Accessibility PyQt_SessionManager"

DISABLED_FEATURES_append_arm = " PyQt_qreal_double"

PYQT_MODULES = "QtCore QtGui QtDeclarative QtNetwork QtSvg"
PYQT_MODULES_aarch64 = "QtCore QtGui QtDeclarative QtNetwork QtSvg"

export BUILD_SYS
export HOST_SYS
export STAGING_LIBDIR
export STAGING_INCDIR
do_configure() {
    echo "py_platform = linux" > pyqt.cfg
    echo "py_inc_dir = %(sysroot)/$includedir/python%(py_major).%(py_minor)" >> pyqt.cfg
    echo "py_pylib_dir = %(sysroot)/${libdir}/python%(py_major).%(py_minor)" >> pyqt.cfg
    echo "py_pylib_lib = python%(py_major).%(py_minor)mu" >> pyqt.cfg
    echo "pyqt_module_dir = ${D}/${libdir}/python%(py_major).%(py_minor)/site-packages" >> pyqt.cfg
    echo "pyqt_bin_dir = ${D}/${bindir}" >> pyqt.cfg
    echo "pyqt_sip_dir = ${D}/${datadir}/sip/PyQt5" >> pyqt.cfg
    echo "pyuic_interpreter = ${D}/${bindir}/python%(py_major).%(py_minor)" >> pyqt.cfg
    echo "pyqt_disabled_features = ${DISABLED_FEATURES}" >> pyqt.cfg
    echo "qt_shared = True" >> pyqt.cfg
    echo "[Qt ${PV}]" >> pyqt.cfg
    echo "pyqt_sip_dirt_modules = ${PYQT_MODULES}" >> pyqt.cfg
    echo yes | python ${S}/configure.py --verbose --qmake  ${OE_QMAKE_QMAKE} --configuration pyqt.cfg --sysroot ${STAGING_DIR_HOST}
}
do_install() {
     oe_runmake install
}

RDEPENDS_${PN} = "python-core python-sip"

FILES_${PN} += "${libdir}/${PYTHON_DIR}/site-packages ${datadir}/sip/PyQt5/"
FILES_${PN}-dbg += "${libdir}/${PYTHON_DIR}/site-packages/*/.debug/"

