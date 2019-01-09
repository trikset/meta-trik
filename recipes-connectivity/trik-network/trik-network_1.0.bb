HOMEPAGE = "http://trikset.com"
SECTION = "kernel/userland"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"
SUMMARY = "Shell scripts and configuration files for managing network on TRIK controller"

inherit update-rc.d
INITSCRIPT_NAME = "trik-network"

SRC_URI="file://trik-network.init \
         file://set_wifi_mode.sh \
         file://init_wifi.sh \
         "

INITSCRIPT_PARAMS = "start 94 2 3 4 5 . "

FILES_{PN}="${sysconfdir}/init.d/trik-network ${sysconfdir}/trik/set_wifi_mode.sh ${sysconfdir}/trik/init_wifi.sh"

S = "${WORKDIR}"

do_configure() {
   :
}
do_compile() {
	:
}
do_install() {
	install -d -m 755 ${D}${sysconfdir}/init.d
	install -m 755 ${S}/trik-network.init ${D}${sysconfdir}/init.d/trik-network
	install -d -m 755 ${D}${sysconfdir}/trik/
	install -m 755 ${S}/set_wifi_mode.sh ${D}${sysconfdir}/trik/
	install -m 755 ${S}/init_wifi.sh ${D}${sysconfdir}/trik/

    install -d ${D}/etc/modules-load.d/
    echo wl12xx > ${D}/etc/modules-load.d/wl12xx.conf

     install -d ${D}/etc/modprobe.d/
     echo "blacklist wl12xx" >> ${D}/etc/modprobe.d/blacklist.conf
     echo "blacklist ipv6"   >> ${D}/etc/modprobe.d/blacklist.conf

}
