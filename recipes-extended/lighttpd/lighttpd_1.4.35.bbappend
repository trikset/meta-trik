SUMMARY += "(TRIK edition)"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

RDEPENDS_${PN} += " \
        lighttpd-module-cgi \
        lighttpd-module-rewrite \
        lighttpd-module-redirect \
"

SRC_URI_append = "file://wpa-configurator.sh \
        file://wpa-writer.sh \
        file://wlan-scanner.sh \
        file://lock.png \
        file://wpa_base.css \
        file://wpa-configurator.js \
        file://wifi-configurator.html \
        file://wifi-configurator.css \
        file://admin.html \
        file://admin.css \
        file://handler.js \
"
do_install_append() {
    install -d ${D}/www/pages/network
    install -d ${D}/www/pages/images
    install -d ${D}/www/pages/styles
    install -d ${D}/www/pages/js
    install -m 0755 ${WORKDIR}/wpa-configurator.sh ${D}/www/pages/network/
    install -m 0755 ${WORKDIR}/wpa-writer.sh ${D}/www/pages/network/
    install -m 0755 ${WORKDIR}/wlan-scanner.sh ${D}/www/pages/network/
    install -m 0644 ${WORKDIR}/lock.png ${D}/www/pages/images/
    install -m 0644 ${WORKDIR}/wpa_base.css ${D}/www/pages/styles/
    install -m 0644 ${WORKDIR}/wpa-configurator.js ${D}/www/pages/js/
    install -m 0644 ${WORKDIR}/wifi-configurator.html ${D}/www/pages/
    install -m 0644 ${WORKDIR}/admin.html ${D}/www/pages/
    install -m 0644 ${WORKDIR}/wifi-configurator.css ${D}/www/pages/styles/
    install -m 0644 ${WORKDIR}/admin.css ${D}/www/pages/styles/
    install -m 0644 ${WORKDIR}/handler.js ${D}/www/pages/js/
}
