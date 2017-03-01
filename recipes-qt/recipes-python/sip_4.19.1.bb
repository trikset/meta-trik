SUMMARY = "SIP is a C++/Python Wrapper Generator"
AUTHOR = "Phil Thompson"
HOMEPAGE = "http://www.riverbankcomputing.co.uk/sip"
SECTION = "devel"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://LICENSE-GPL2;md5=e91355d8a6f8bd8f7c699d62863c7303"
DEPENDS_class-target = "python"

# riverbankcomputing is upstream, but keeps only latest version, sf usually have few older
#SRC_URI = "http://www.riverbankcomputing.com/static/Downloads/sip4/sip-${PV}.tar.gz"
SRC_URI = "${SOURCEFORGE_MIRROR}/project/pyqt/sip/sip-${PV}/sip-${PV}.tar.gz"
SRC_URI[md5sum] = "a721bc171e19c0daf610508b67ecee1d"
SRC_URI[sha256sum] = "501852b8325349031b769d1c03d6eab04f7b9b97f790ec79f3d3d04bf065d83e"

inherit pythonnative distro_features_check qmake5
REQUIRED_DISTRO_FEATURES = "x11"
BBCLASSEXTEND = "native"
CLEANBROKEN_${PN} = "1"
PACKAGES += "python-sip"


EXTRA_QMAKEVARS_POST += "CONFIG=console"

export BUILD_SYS
export HOST_SYS
export STAGING_LIBDIR
export STAGING_INCDIR
#B = "${S}"
do_configure_prepend_class-target() {
    stored_curdir=`pwd`
    cd ${S}
    echo "py_platform = linux" > sip.cfg
    echo "py_inc_dir = %(sysroot)/${includedir}/python%(py_major).%(py_minor)" >> sip.cfg
    echo "sip_bin_dir = ${D}/${bindir}" >> sip.cfg
    echo "sip_inc_dir = ${D}/${includedir}" >> sip.cfg
    echo "sip_module_dir = ${D}/${libdir}/python%(py_major).%(py_minor)/site-packages" >> sip.cfg
    echo "sip_sip_dir = ${D}/${datadir}/sip" >> sip.cfg
    ${PYTHON} configure.py  -u --use-qmake --configuration sip.cfg --sysroot ${STAGING_DIR_TARGET}
    cd $stored_curdir
}
_do_configure_prepend_class-nativesdk() {
    stored_curdir=`pwd`
    cd ${S}
    echo "py_platform = linux" > sip.cfg
    echo "py_inc_dir = %(sysroot)/${includedir}/python%(py_major).%(py_minor)" >> sip.cfg
    echo "sip_bin_dir = ${D}/${bindir}" >> sip.cfg
    echo "sip_inc_dir = ${D}/${includedir}" >> sip.cfg
    echo "sip_module_dir = ${D}/${libdir}/python%(py_major).%(py_minor)/site-packages" >> sip.cfg
    echo "sip_sip_dir = ${D}/${datadir}/sip" >> sip.cfg
    ${PYTHON} configure.py --use-qmake -u --configuration sip.cfg --sysroot ${STAGING_DIR_HOST}
    cd $stored_curdir
}
do_configure_prepend_class-native() {
    stored_curdir=`pwd`
    cd ${S}
    echo "py_platform = linux" > sip.cfg
    echo "py_inc_dir = ${includedir}/python%(py_major).%(py_minor)" >> sip.cfg
    echo "sip_bin_dir = ${D}/${bindir}" >> sip.cfg
    echo "sip_inc_dir = ${D}/${includedir}" >> sip.cfg
    echo "sip_module_dir = ${D}/${libdir}/python%(py_major).%(py_minor)/site-packages" >> sip.cfg
    echo "sip_sip_dir = ${D}/${datadir}/sip" >> sip.cfg
    ${PYTHON} configure.py -u --use-qmake --configuration sip.cfg --sysroot ${STAGING_DIR_NATIVE}
    cd $stored_curdir
}
do_install_class-native() {
    sip_do_install
}
do_install () {
  sip_do_install
}
sip_do_install() {
    STAGING_DIR_TARGET=${STAGING_DIR_TARGET:-"undefined"}
    STAGING_DIR_HOST=${STAGING_DIR_HOST:-"undefined"}
    bbnote ${STAGING_DIR_NATIVE}
    qmake5_base_do_install
    qmake5_base_fix_install ${D}
}

#FILES_${PN} = "/image/usr/*"
FILES_python-${BPN} = "${libdir}/${PYTHON_DIR}/site-packages/"
FILES_${PN}-dbg += "${libdir}/${PYTHON_DIR}/site-packages/.debug"

