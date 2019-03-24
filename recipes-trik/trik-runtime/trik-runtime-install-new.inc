do_install() {
        install -d ${D}/home/root/trik
        cp -rf ${B}/bin/arm-release/* ${D}/home/root/trik
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
        cp -rf ${B}/bin/arm-release/lib*.so* ${D}${libdir}

        for lib in $(ls ${D}${libdir}); do
            ln -sf ${libdir}/${lib} ${D}/home/root/trik/${lib}
        done

        install -m 0755 -D -t ${D}/${datadir}/trik/init.d/ ${WORKDIR}/compress_logs.sh
}


