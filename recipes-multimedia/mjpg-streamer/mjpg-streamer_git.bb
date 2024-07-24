SUMMARY = "MJPG-streamer takes JPGs from Linux-UVC compatible webcams, filesystem or other input plugins and streams \
           them as M-JPEG via HTTP to webbrowsers, VLC and other software"
HOMEPAGE = "https://github.com/trikset/mjpg-streamer/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=751419260aa954499f7abaabaa882bbe"

BRANCH = "trik-master"
SRCREV  = "${AUTOREV}"
SRC_URI = "git://github.com/trikset/mjpg-streamer.git;protocol=https;branch=${BRANCH} \
	   file://mjpg-streamer"
PR = "r2"
DEPENDS = "libv4l"
RDEPENDS:${PN} += "bash"

# Seems like build config problem. Actually, we do not need it.
DEPENDS += "libsdl"

RRECOMMENDS:${PN} += "jpeg-encoder-ov7670"
RDEPENDS:${PN} += "bash"

inherit cmake

S ="${WORKDIR}/git/mjpg-streamer-experimental"

do_install:append() {
	install -d ${D}/${sysconfdir}/init.d/
	install -m 0755 ${WORKDIR}/mjpg-streamer ${D}/${sysconfdir}/init.d/

	ln -sf mjpg-streamer ${D}/etc/init.d/mjpg-streamer-ov7670
	ln -sf mjpg-streamer ${D}/etc/init.d/mjpg-streamer-webcam
}

FILES:${PN} += "/usr/lib/*"



