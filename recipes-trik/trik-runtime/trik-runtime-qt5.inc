DESCRIPTION = "Runtime for TRIK controller"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=175792518e4ac015ab6696d16c4f607e"
RDEPENDS_${PN} = "locale-default espeak alsa-utils v4l-utils liberation-fonts mjpg-streamer"

#RRECOMMENDS_${PN} = "vlc"

inherit qmake5 pkgconfig
DEPENDS += "qtmultimedia qtscript qtserialport"
DEPENDS += "qtquick1 qtquickcontrols2 qtquickcontrols"
#RREPLACES_${PN} += "trik-runtime"
RCONFLICTS_${PN} += "trik-runtime-qt4"
RPROVIDES_${PN} += "trik-runtime"
TAG ?= "${PV}-${TRIK_RUNTIME_UPDATE}"
GIT_REVISION ?= "tag=${TAG};nobranch=1"
SRC_URI = "gitsm://github.com/trikset/trikRuntime.git;protocol=https;${GIT_REVISION} \
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
EXTRA_QMAKEVARS_PRE = "-r CONFIG+=release CONFIG+=ltcg CONFIG+=use_gold_linker"

do_compile(){
        export CROSS_COMPILE="${TARGET_PREFIX}"
        oe_runmake
}

FILES_${PN} += "${datadir}"


FILES_${PN} += "/home/root/trik/"
#FILES_${PN}-conf += "${sysconfdir}"
#FILES_${PN}-dev += "${includedir}/trikRuntime/*"
#FILES_${PN} += "${libdir}"
FILES_${PN}-dev += "/home/root/trik/*.so"
#FILES_${PN}-dbg += "/home/root/trik/.debug"

pkg_postinst_${PN} () {
        killall trikGui || true
        sleep 3
        killall -9 trikGui 2> /dev/null || true
}

