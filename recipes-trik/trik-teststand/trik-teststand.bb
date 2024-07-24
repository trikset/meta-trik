DECRIPTION = "Test utility for TRIK controller"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d2794c0df5b907fdace235a619d80314"
RDEPENDS:${PN} += ""
#RRECOMMENDS:${PN} = "vlc"

inherit qmake5
DEPENDS += "trik-runtime-qt5"
#RREPLACES:${PN} += "trik-runtime"
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


#FILES:${PN} += "/home/root/trik/"
#FILES:${PN}-conf += "${sysconfdir}"
#FILES:${PN}-dev += "${includedir}/trikRuntime/*"
#FILES:${PN} += "${libdir}"
#FILES:${PN}-dev += "/home/root/trik/*.so"
#FILES:${PN}-dbg += "/home/root/trik/.debug"

#pkg_postinst:${PN} () {
#        killall trikGui || true
#        sleep 3
#        killall -9 trikGui 2> /dev/null || true
#}
