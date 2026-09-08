SUMMARY = "DSP firmware for TRIK (loaded by remoteproc)"
SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/trikset/trik-media-sensors.git;protocol=https;destsuffix=trik-media-sensors;branch=main"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
PR = "2"

S = "${WORKDIR}/trik-media-sensors"

S_DSP = "${S}/bin/dsp"
S_ARM = "${S}/bin/arm"

FILES:${PN} += "/lib/firmware /etc/trik/"
INSANE_SKIP:${PN} += "arch"

do_compile() {
	:
}

do_install() {
	install -d -m 0755 ${D}/lib/firmware
	cp ${S_DSP}/release/server_dsp.xe674 ${D}/lib/firmware/rproc-dsp-fw

	install -d -m 0755 ${D}/etc/trik/sensors/${PN}/

	install -d -m 0755 ${D}${libdir}/pkgconfig
	cp -R ${S}/ipc-libs/lib/* ${D}${libdir}/
	find ${D}${libdir} -type d -exec chmod 755 {} \;
	find ${D}${libdir} -type f -exec chmod 644 {} \;

	install -d -m 0755 ${D}${includedir}
	cp -R ${S}/ipc-libs/include/* ${D}/${includedir}/
	chmod -R 0644 ${D}/${includedir}

	install -d -m 0755 ${D}${bindir}
	install -m 0755 ${S}/ipc-libs/bin/lad_omapl138 ${D}/${bindir}/
}
