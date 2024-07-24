HOMEPAGE 	 = "http://trikset.com"
LICENSE		 = "Apache-2.0"
LIC_FILES_CHKSUM ="file://${COREBASE}/meta/files/common-licenses/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"
RDEPENDS:${PN}   = "common-sensors"
SENSOR_RELEASE_DATE = "180723"

#PR="r1"

PV = "git${SENSOR_RELEASE_DATE}"
TAG="${SENSOR_RELEASE_DATE}"
SRC_URI ="git://github.com/trikset/trik-media-sensors-arm.git;protocol=https;rev=17b549e0dc745d9786b287411060237630f23fd6;destsuffix=arm "
SRC_URI += "git://github.com/trikset/trik-media-sensors-dsp.git;protocol=https;rev=159fee9f664cb405c65aff495893a35dbefacbe1;destsuffix=dsp"

# ORLY????
RDEPENDS:${PN}:append = " libv4l "

S_DSP ="${WORKDIR}/dsp"
S_ARM ="${WORKDIR}/arm"
do_compile() {
        :
}


do_install() {
    PN_MY=$(echo ${PN} | sed "s#-webcam##g")
      install -d -m 0755 ${D}/etc/trik/sensors/${PN}/
      cp ${S_DSP}/release/webcam/${PN_MY}.xe674 ${D}/etc/trik/sensors/${PN}/dsp_server.xe674
      install -m 0755 ${S_ARM}/release/webcam/${PN} ${D}/etc/trik/sensors/${PN}/

      install -d -m 0755 ${D}/etc/init.d/
      ln -sf media-sensor ${D}/etc/init.d/${PN}

      install -d -m 0755 ${D}/etc/default/
      install -m 0755 ${S_ARM}/release/webcam/${PN}.default ${D}/etc/default/
}


#FILES:${PN} = "/etc/*"
#INHIBIT_PACKAGE_STRIP = "1"
#INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

INSANE_SKIP:${PN} = "ldflags arch"
