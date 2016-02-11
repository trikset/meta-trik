DECRIPTION = "Runtime for TRIK controller"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=175792518e4ac015ab6696d16c4f607e"
RDEPENDS_${PN} = "locale-default espeak alsa-utils"
RRECOMMENDS_${PN} = "vlc" 

inherit qt4e

TAG = "${PV}"

SRC_URI = "git://github.com/trikset/trikRuntime.git;protocol=https;tag=${TAG} \
          file://trikGui.sh \
          file://qws_display.sh"


S = "${WORKDIR}/git"

do_compile(){
        export CROSS_COMPILE="${TARGET_PREFIX}"
        oe_runmake
}

do_install() {
#        oe_runmake INSTALL_ROOT=${D}/home/root/trik/ install
	install -d -m 0755 ${D}/home/root/trik/
	cp -rf  ${S}/bin/arm-release/* ${D}/home/root/trik/

        install -d ${D}/${sysconfdir}/trik/
        install -m 0755 ${WORKDIR}/trikGui.sh ${D}/${sysconfdir}/trik/

        install -d ${D}/${sysconfdir}/profile.d/
        install -m 0755 ${WORKDIR}/qws_display.sh ${D}/${sysconfdir}/profile.d/
}


FILES_${PN} = "/home/root/trik/"
FILES_${PN} += "/etc/*"
FILES_${PN}-dev += "/home/root/trik/*.so"
FILES_${PN}-dbg += "/home/root/trik/.debug"

pkg_postinst_${PN} () {
        killall trikGui || true
        sleep 3
        killall -9 trikGui 2> /dev/null || true
}

