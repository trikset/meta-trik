HOMEPAGE 	 = "http://trikset.com"
LICENSE		 = "Apache-2.0"
LIC_FILES_CHKSUM ="file://${COREBASE}/meta/files/common-licenses/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"
RDEPENDS_${PN}   = "common-sensors"
#COMPATIBLE_MACHINE = "trikboard"
SENSOR_RELEASE_DATE = "220613"

#PR="r2"

PV = "git${SENSOR_RELEASE_DATE}"
TAG="${SENSOR_RELEASE_DATE}"
SRC_URI	="git://github.com/trikset/trik-media-sensors-arm.git;branch=master;tag=${TAG};protocol=https;destsuffix=arm "
SRC_URI += "git://github.com/trikset/trik-media-sensors-dsp.git;branch=master;tag=${TAG};protocol=https;destsuffix=dsp"

# ORLY????
RDEPENDS_${PN} += "libv4l"

S_DSP_trikboard ="${WORKDIR}/dsp"
S_ARM_trikboard ="${WORKDIR}/arm"
do_compile() {
	:
}


do_install() {
    PN_MY=$(echo ${PN} | sed "s#-ov7670##g")
	install -d -m 0755 ${D}/etc/trik/sensors/${PN}/
	cp ${S_DSP}/release/ov7670/${PN_MY}.xe674 ${D}/etc/trik/sensors/${PN}/dsp_server.xe674
	install -m 0755 ${S_ARM}/release/ov7670/${PN} ${D}/etc/trik/sensors/${PN}/
	install -d -m 0755 ${D}/etc/init.d/
	ln -sf media-sensor ${D}/etc/init.d/${PN}
	install -d -m 0755 ${D}/etc/default/
	install -m 0755 ${S_ARM}/release/ov7670/${PN}.default ${D}/etc/default/
}


#FILES_${PN} = "/etc/*"
#INHIBIT_PACKAGE_STRIP = "1"
#INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

INSANE_SKIP_${PN}_trikboard = "ldflags arch"
