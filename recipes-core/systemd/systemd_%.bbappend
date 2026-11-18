do_install:append() {
	sed -i '/^enable getty@\.service$/d' ${D}${systemd_unitdir}/system-preset/90-systemd.preset
}
