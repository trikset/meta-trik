FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_install_append() {
	echo "tg:5:respawn:/etc/trik/trikGui.sh" >> ${D}${sysconfdir}/inittab
	echo "strm:5:respawn:/etc/trik/current-mjpg-streamer" >> ${D}${sysconfdir}/inittab
	echo "ms:5:respawn:/etc/trik/current-media-sensor" >> ${D}${sysconfdir}/inittab
	echo "gs:5:respawn:/etc/trik/current-gamepad-service" >> ${D}${sysconfdir}/inittab
}

