# look for files in the layer first
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " \
               file://99-trik-firmware.rules\
               file://trik_fw_helper\
               "

do_install_append_trikboard() {
      install -m 0644 ${WORKDIR}/99-trik-firmware.rules ${D}${sysconfdir}/udev/rules.d/
      install -D -m 0755 ${WORKDIR}/trik_fw_helper ${D}${sysconfdir}/udev/scripts/trik_fw_helper
}
