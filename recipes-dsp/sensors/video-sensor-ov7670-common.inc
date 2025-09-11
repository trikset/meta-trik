HOMEPAGE 	 = "http://trikset.com"
LICENSE		 = "Apache-2.0"
LIC_FILES_CHKSUM ="file://${COREBASE}/meta/files/common-licenses/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"
RDEPENDS:${PN}   = "common-sensors"
SRCREV = "${AUTOREV}"
SRC_URI += "git://github.com/trikset/trik-media-sensors.git;protocol=https;destsuffix=trik-media-sensors;branch=main"
# ORLY????
RDEPENDS:${PN} += "libv4l"

S_ARM ="${WORKDIR}/trik-media-sensors/bin/arm"

do_compile() {
	:
}


do_install() {
    PN_MY=$(echo ${PN} | sed "s#-ov7670##g")
      install -d -m 0755 ${D}/etc/trik/sensors/${PN}/

      install -d -m 0755 ${D}/etc/init.d/
      ln -sf media-sensor ${D}/etc/init.d/${PN}
      ln -sf ../common-sensors/app_host ${D}/etc/trik/sensors/${PN}/${PN}

      install -d -m 0755 ${D}/etc/default/
      install -m 0755 ${S_ARM}/default/ov7670/${PN}.default ${D}/etc/default/
}


INSANE_SKIP:${PN} = "ldflags arch"
