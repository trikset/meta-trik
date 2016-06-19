SUMMARY = "MJPG-streamer takes JPGs from Linux-UVC compatible webcams, filesystem or other input plugins and streams \
           them as M-JPEG via HTTP to webbrowsers, VLC and other software"
HOMEPAGE = "https://github.com/trikset/mjpg-streamer/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=751419260aa954499f7abaabaa882bbe"

BRANCH = "trik-master"
SRCREV  = "${AUTOREV}"
SRC_URI = "git://github.com/trikset/mjpg-streamer.git;protocol=https;branch=${BRANCH} \
	   file://mjpg-streamer-ov7670.sh"
PR = "r2"
DEPENDS = "jpeg "
RRECOMMENDS_${PN} = "jpeg-encoder-ov7670"

inherit cmake

S ="${WORKDIR}/git/mjpg-streamer-experimental"

do_install_append() {
	install -d ${D}/${sysconfdir}/init.d/
	install -m 0755 ${WORKDIR}/mjpg-streamer-ov7670.sh ${D}/${sysconfdir}/init.d/
}

FILES_${PN} += "/usr/lib/*"



