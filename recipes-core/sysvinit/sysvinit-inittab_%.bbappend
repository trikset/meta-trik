FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_install_append() {
	echo "tg:5:respawn:/etc/trik/trikGui.sh" >> ${D}${sysconfdir}/inittab
	echo "strm:5:respawn:/etc/init.d/mjpg-streamer-ov7670.sh start" >> ${D}${sysconfdir}/inittab
	echo "ms:5:respawn:/etc/trik/current-media-sensor" >> ${D}${sysconfdir}/inittab
}

