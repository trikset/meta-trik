FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

do_install:append() {
	echo "tg:5:respawn:/etc/trik/trikGui.sh" >> ${D}${sysconfdir}/inittab
	echo "strm:5:respawn:/etc/trik/current-mjpg-streamer" >> ${D}${sysconfdir}/inittab
	echo "ms:5:respawn:/etc/trik/current-media-sensor" >> ${D}${sysconfdir}/inittab
	echo "gs:5:respawn:/etc/trik/current-gamepad-service" >> ${D}${sysconfdir}/inittab
	echo "s1:5:respawn:/etc/trik/init_ttyS1.sh" >> ${D}${sysconfdir}/inittab
}

