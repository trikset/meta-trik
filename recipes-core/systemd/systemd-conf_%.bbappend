FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI += "file://systemd-networkd-wait-online.service.d/10-trik-no-block.conf"

do_install:append() {
	install -d ${D}${sysconfdir}/systemd/system/systemd-networkd-wait-online.service.d
	install -m 0644 ${WORKDIR}/systemd-networkd-wait-online.service.d/10-trik-no-block.conf \
		${D}${sysconfdir}/systemd/system/systemd-networkd-wait-online.service.d/
}

FILES:${PN} += "${sysconfdir}/systemd/system/systemd-networkd-wait-online.service.d"
