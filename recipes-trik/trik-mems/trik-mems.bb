DESCRIPTION = "Mems init scripts"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

#There is a script to run everithing from /usr/share/trik/init.d there
RDEPENDS_${PN} += "base-files"

SRC_URI="file://init_mems.sh \
         file://mems_options.sh"

do_install() {
	install -m 0755 -D -t ${D}/${datadir}/trik/init.d/ ${WORKDIR}/init_mems.sh	
	install -m 0755 -D -t ${D}/etc/default/trik/ ${WORKDIR}/mems_options.sh 
}
