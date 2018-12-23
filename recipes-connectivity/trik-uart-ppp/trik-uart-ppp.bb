DESCRIPTION = "TCP/IP over UART using PPP"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

SRC_URI="file://init_tty.sh \
         file://winclient.chat \
         file://autologin"

do_install() {
	install -m 0755 -D -t ${D}/${sysconfdir}/trik/ ${WORKDIR}/init_tty.sh ${WORKDIR}/autologin
	
	install -m 0644 -D -t ${D}/${sysconfdir}/ppp ${WORKDIR}/winclient.chat
}
