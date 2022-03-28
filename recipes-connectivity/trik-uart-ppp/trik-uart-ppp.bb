DESCRIPTION = "TCP/IP over UART using PPP"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

PV = "1.0"
PR = "r1"

RDEPENDS:${PN} += "base-files bash"

SRC_URI=" \
         file://init_ttyS1.sh \
         file://winclient.chat \
         file://autologin"

FILES:${PN} += "${datadir}"

do_install() {
	install -m 0755 -D -t ${D}/${sysconfdir}/trik/ ${WORKDIR}/autologin
	install -m 0755 -D -t ${D}/${sysconfdir}/trik/ ${WORKDIR}/init_ttyS1.sh
	install -m 0644 -D -t ${D}/${sysconfdir}/ppp ${WORKDIR}/winclient.chat
	install -m 0644 -D -t ${D}/${sysconfdir}/default ${WORKDIR}/ttyS1
}
