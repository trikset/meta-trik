HOMEPAGE 	 = "http://trikset.com"
LICENSE		 = "Apache-2.0"
LIC_FILES_CHKSUM ="file://${COREBASE}/meta/files/common-licenses/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

SENSOR_RELEASE_DATE = "1410030"

PV = "git${SENSOR_RELEASE_DATE}"
TAG="${PN}_${SENSOR_RELEASE_DATE}"
SRC_URI	="git://github.com/dmitry42nd/sensors-webcam.git;branch=${PN};tag=${TAG}"

S ="${WORKDIR}/git"
do_compile() {
	:
}
do_install() {
	install -d -m 0755 ${D}/etc/trik/sensors/${PN}/
	cp ${S}/dsp_server.xe674 ${D}/etc/trik/sensors/${PN}/
	install -m 0755 ${S}/${PN} ${D}/etc/trik/sensors/${PN}/
	
	install -d -m 0755 ${D}/etc/init.d/
	install -m 0755 ${S}/${PN}.sh ${D}/etc/init.d/
	
	install -d -m 0755 ${D}/etc/default/
	install -m 0755 ${S}/${PN}.default ${D}/etc/default/
}


FILES_${PN} = "/etc/*"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

INSANE_SKIP_${PN} = "ldflags arch"
