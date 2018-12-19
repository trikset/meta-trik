DESCRIPTION = "TCP/IP over UART using PPP"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1234567890"

SRC_URI="file://init_tty.sh \
         file://winclient.chat \
         file://autologin \
         file://LICENSE"

do_install() {
	install -m 0755 -D -t ${D}/${sysconfdir}/trik/ ${WORKDIR}/init_tty.sh ${WORKDIR}/autologin
	
	install -m 0644 -D -t ${D}/${sysconfdir}/trik/ppp ${WORKDIR}/winclient.chat
}
