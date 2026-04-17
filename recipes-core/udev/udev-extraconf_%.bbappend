# look for files in the layer first
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
               file://wlan.rules \
               file://wlan-rename.sh \
               "

do_install:append() {
      install -m 0644 ${WORKDIR}/wlan.rules ${D}${sysconfdir}/udev/rules.d/
      install -D -m 0755 ${WORKDIR}/wlan-rename.sh ${D}${sysconfdir}/udev/scripts/wlan-rename.sh
}
