HOMEPAGE = "http://trikset.com"
SECTION = "kernel/userland"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"
SUMMARY = "Shell scripts and configuration files for managing network on TRIK controller"

RDEPENDS_${PN} += "bash ti-wifi-utils"

SRC_URI="\
         file://set_wifi_mode.sh \
         file://20-init_wifi.sh \
	 file://10-generate_mac4wifi.sh \
         "

FILES_${PN} += "${datadir}/trik"

S = "${WORKDIR}"

do_configure() {
   :
}
do_compile() {
	:
}
do_install() {
	install -D -m 755 -t ${D}${datadir}/trik/init.d/ \
	            ${S}/10-generate_mac4wifi.sh \
		    ${S}/20-init_wifi.sh

	install -D -m 755 -t ${D}${sysconfdir}/trik/ \
	            ${S}/set_wifi_mode.sh
         install -d ${D}/etc/modules-load.d/
    echo wl12xx > ${D}/etc/modules-load.d/wl12xx.conf

     install -d ${D}/etc/modprobe.d/
     echo "blacklist wl12xx" >> ${D}/etc/modprobe.d/blacklist.conf
     echo "blacklist ipv6"   >> ${D}/etc/modprobe.d/blacklist.conf

}
