SUMMARY		 = "Init script  for ov7670 camera "
HOMEPAGE 	 = "http://trikset.com"
LICENSE		 = "Apache-2.0"
LIC_FILES_CHKSUM ="file://${COREBASE}/meta/files/common-licenses/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"


TAG="${PN}_150813"
SRC_URI	="git://github.com/dmitry42nd/sensors-ov7670.git;branch=${PN};tag=${TAG}"

S ="${WORKDIR}/git"
do_compile() {
	:
}
do_install() {
	install -d -m 0755 ${D}/etc/trik/
	install -d -m 0755 ${D}/etc/init.d/
	install -m 0755 ${S}/init-ov7670-320x240.sh ${D}/etc/trik/
	install -m 0755 ${S}/media-sensor ${D}/etc/init.d/
	install -m 0755 ${S}/media-sensor-dummy ${D}/etc/init.d/
}

