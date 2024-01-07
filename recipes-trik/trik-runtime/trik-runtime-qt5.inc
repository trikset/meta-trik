DESCRIPTION = "Runtime for TRIK controller"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=175792518e4ac015ab6696d16c4f607e"
RDEPENDS:${PN} = "locale-default espeak alsa-utils v4l-utils liberation-fonts mjpg-streamer"

#RRECOMMENDS:${PN} = "vlc"

inherit qmake5 pkgconfig
DEPENDS += "qtmultimedia qtscript"
#RREPLACES:${PN} += "trik-runtime"
RCONFLICTS:${PN} += "trik-runtime-qt4"
RPROVIDES:${PN} += "trik-runtime"
TAG ?= "${PV}-${TRIK_RUNTIME_UPDATE}"
#GIT_REVISION ?= "tag=${TAG};nobranch=1"
SRCREV = "${AUTOREV}"
SRC_URI = "gitsm://github.com/IgnatSergeev/trikRuntime.git;protocol=https;branch=kirkstone \
          file://trikGui.sh \
          file://qws_display.sh \
          file://trik_runtime_path.sh \
          file://python_path.sh \
          file://log_manager.sh \
          file://compress_logs.sh \
          file://say \
          file://gamepad-service"

S = "${WORKDIR}/git"

QMAKE_PROFILES = "${S}/trikRuntime.pro"
EXTRA_QMAKEVARS_PRE = "-r CONFIG+=release CONFIG+=ltcg CONFIG+=use_gold_linker CONFIG+=sanitizer CONFIG+=sanitize_undefined"
OE_QMAKE_CXXFLAGS += "-O0"
OE_QMAKE_CFLAGS += "-O0"

do_compile(){
        export CROSS_COMPILE="${TARGET_PREFIX}"
        oe_runmake
}

FILES:${PN} += "${datadir}"


FILES:${PN} += "/home/root/trik/"
#FILES:${PN}-conf += "${sysconfdir}"
#FILES:${PN}-dev += "${includedir}/trikRuntime/*"
#FILES:${PN} += "${libdir}"
FILES:${PN}-dev += "/home/root/trik/*.so"
#FILES:${PN}-dbg += "/home/root/trik/.debug"

pkg_postinst:${PN} () {
        killall trikGui || true
        sleep 3
        killall -9 trikGui 2> /dev/null || true
}

