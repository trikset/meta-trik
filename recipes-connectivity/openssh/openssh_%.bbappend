FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://sshd_check_keys"

do_install:append () {
	install -D -m 0755 ${WORKDIR}/sshd_check_keys ${D}${libexecdir}/${BPN}/sshd_check_keys
}

FILES:${PN}-sshd += "${libexecdir}/${BPN}/sshd_check_keys"
