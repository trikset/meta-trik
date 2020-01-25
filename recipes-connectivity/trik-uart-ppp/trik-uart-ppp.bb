DESCRIPTION = "TCP/IP over UART using PPP"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

RDEPENDS_${PN} += "base-files bash"

SRC_URI="file://tty_ppp.sh \
	 file://tty_login.sh \
         file://winclient.chat \
         file://autologin"

#FILES_${PN} += "${datadir}"
RDEPENDS_${PN} += "bash"

do_install() {
	install -m 0755 -D -t ${D}/${sysconfdir}/trik/ ${WORKDIR}/autologin
	
	install -m 0755 -D -t ${D}/${datadir}/trik/ ${WORKDIR}/tty_ppp.sh ${WORKDIR}/tty_login.sh
	
	ln ${D}/${datadir}/trik/tty_ppp.sh ${D}/${sysconfdir}/trik/init_tty.sh
	
	install -m 0644 -D -t ${D}/${sysconfdir}/ppp ${WORKDIR}/winclient.chat
}
