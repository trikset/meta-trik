DESCRIPTION = "Runtime for TRIK controller"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=175792518e4ac015ab6696d16c4f607e"
RDEPENDS_${PN} = "locale-default espeak alsa-utils v4l-utils liberation-fonts mjpg-streamer"

#RRECOMMENDS_${PN} = "vlc"

inherit qmake5 pkgconfig
DEPENDS += "qtmultimedia qtscript"
#RREPLACES_${PN} += "trik-runtime"
RCONFLICTS_${PN} += "trik-runtime-qt4"
RPROVIDES_${PN} += "trik-runtime"
TAG = "${PV}-${TRIK_RUNTIME_UPDATE}"

SRC_URI = "gitsm://github.com/trikset/trikRuntime.git;protocol=https;tag=${TAG};nobranch=1 \
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
EXTRA_QMAKEVARS_PRE = "-r CONFIG+=no-sanitizers"

do_compile(){
        export CROSS_COMPILE="${TARGET_PREFIX}"
        oe_runmake
}

FILES_${PN} += "${datadir}"

do_install() {
        install -d ${D}/home/root/trik
        cp -rf ${S}/bin/arm-release/* ${D}/home/root/trik
        install -d ${D}/home/root/trik/scripts

        install -d ${D}/${sysconfdir}/trik/
        install -m 0755 ${WORKDIR}/trikGui.sh ${D}/${sysconfdir}/trik/

        install -d ${D}/${sysconfdir}/init.d/
        install -m 0755 ${WORKDIR}/gamepad-service ${D}/${sysconfdir}/init.d/

        install -d ${D}/${sysconfdir}/profile.d/
        install -m 0755 ${WORKDIR}/qws_display.sh ${D}/${sysconfdir}/profile.d/
        install -m 0755 ${WORKDIR}/trik_runtime_path.sh ${D}/${sysconfdir}/profile.d/
        install -m 0755 ${WORKDIR}/python_path.sh ${D}/${sysconfdir}/profile.d/
        install -m 0755 ${WORKDIR}/log_manager.sh ${D}/${sysconfdir}/trik/
        install -m 0755 -t ${D}/${sysconfdir}/trik/ ${WORKDIR}/say

        LIBS="trikCommunicator \
              trikControl \
              trikHal \
              trikKernel \
              trikNetwork \
              trikScriptRunner \
              trikTelemetry \
              trikWiFi"

        install -d ${D}${includedir}/trikRuntime
        cp -rf ${S}/qslog/*.h ${D}${includedir}/trikRuntime
        for lib in $LIBS; do
            cp -rf ${S}/${lib}/include/* ${D}${includedir}/trikRuntime/${lib}
        done

        install -d ${D}${libdir}
        cp -rf ${S}/bin/arm-release/lib*.so* ${D}${libdir}

        for lib in $(ls ${D}${libdir}); do
            ln -sf ${libdir}/${lib} ${D}/home/root/trik/${lib}
        done

        install -m 0755 -D -t ${D}/${datadir}/trik/init.d/ ${WORKDIR}/compress_logs.sh
}


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
