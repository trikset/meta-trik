FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://trik-smb.conf \
            file://nmb.service.d/10-nonblocking.conf \
"

do_install:append() {
	install -m 0644 ${WORKDIR}/trik-smb.conf ${D}${sysconfdir}/samba/smb.conf

	install -d ${D}${systemd_system_unitdir}/nmb.service.d
	install -m 0644 ${WORKDIR}/nmb.service.d/10-nonblocking.conf \
		${D}${systemd_system_unitdir}/nmb.service.d/
}

FILES:${PN}-base += "${systemd_system_unitdir}/nmb.service.d"
