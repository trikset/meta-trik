SUMMARY		 = "Init script  for ov7670 camera "
SRCREV = "${AUTOREV}"
SRC_URI = "file://media-sensor file://init-ov7670-320x240.sh file://media-sensor-dummy"
SRC_URI += "git://github.com/trikset/trik-media-sensors.git;protocol=https;destsuffix=trik-media-sensors;branch=main"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
PR="2"
RDEPENDS:${PN} += "bash"

S_REPO = "${WORKDIR}/trik-media-sensors"

S_DSP ="${S_REPO}/bin/dsp"
S_ARM ="${S_REPO}/bin/arm"

FILES:${PN} += "/lib/firmware"
INSANE_SKIP:${PN} += "arch"

do_compile() {
        :
}


do_install() {
	install -d -m 0755 ${D}/etc/trik/
	install -d -m 0755 ${D}/etc/init.d/
	install -m 0755 ${WORKDIR}/init-ov7670-320x240.sh ${D}/etc/trik/
	install -m 0755 ${WORKDIR}/media-sensor ${D}/etc/init.d/
	install -m 0755 ${WORKDIR}/media-sensor-dummy ${D}/etc/init.d/
	
	install -d -m 0755 ${D}/etc/trik/sensors/${PN}/
	
	install -d -m 0755 ${D}/lib/firmware
      	cp ${S_DSP}/release/server_dsp.xe674 ${D}/lib/firmware/rproc-dsp-fw

	install -m 0755 ${S_ARM}/release/app_host ${D}/etc/trik/sensors/${PN}/app_host
	
	install -d -m 0755 ${D}/${libdir}/pkgconfig
	cp -R ${S_REPO}/ipc-libs/lib/* ${D}/${libdir}/
	find ${D}/${libdir} -type d -exec chmod 755 {} \;
	find ${D}/${libdir} -type f -exec chmod 644 {} \;

	install -d -m 0755 ${D}${includedir}
	cp -R ${S_REPO}/ipc-libs/include/* ${D}/${includedir}/
	chmod -R 0644 ${D}/${includedir}
	
	install -d -m 0755 ${D}${bindir}
	install -m 0755 ${S_REPO}/ipc-libs/bin/lad_omapl138 ${D}/${bindir}/
}

