DECRIPTION = "Test utility for TRIK controller"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d2794c0df5b907fdace235a619d80314"
RDEPENDS_${PN} += ""
#RRECOMMENDS_${PN} = "vlc"

inherit qmake5
DEPENDS += "trik-runtime-qt5"
#RREPLACES_${PN} += "trik-runtime"
SRCREV="${AUTOREV}"
SRC_URI = "git://github.com/trikset/${PN}.git;protocol=https;branch=master"
S = "${WORKDIR}/git"
EXTRA_QMAKEVARS_PRE = "CONFIG+=nosanitizers"

#do_compile(){
#        export CROSS_COMPILE="${TARGET_PREFIX}"
#        oe_runmake
#}

#do_install() {
#}


#FILES_${PN} += "/home/root/trik/"
#FILES_${PN}-conf += "${sysconfdir}"
#FILES_${PN}-dev += "${includedir}/trikRuntime/*"
#FILES_${PN} += "${libdir}"
#FILES_${PN}-dev += "/home/root/trik/*.so"
#FILES_${PN}-dbg += "/home/root/trik/.debug"

#pkg_postinst_${PN} () {
#        killall trikGui || true
#        sleep 3
#        killall -9 trikGui 2> /dev/null || true
#}
