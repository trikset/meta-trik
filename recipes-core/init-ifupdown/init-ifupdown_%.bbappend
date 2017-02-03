FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://trik-wlan0.cfg"
PR .= ".trik0"
do_install_append() {
    install -m 0644 -d ${D}${sysconfdir}/network/interfaces.d
    install -m 0644 ${WORKDIR}/trik-wlan0.cfg ${D}${sysconfdir}/network/interfaces.d/
}
