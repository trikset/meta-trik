# look for files in this layer first
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
hostname = ""

COMPATIBLE_MACHINE = "trikboard"

inherit user-partion update-rc.d

INITSCRIPT_NAME = "init-trik"
INITSCRIPT_PARAMS="start 38 S ."

SRC_URI += "file://init-trik file://trik-hostname.sh"


do_install_append () {
#	install -v -m 0777 -d ${D}${TRIK_USER_PARTION_CREATION_DIR}
#	generate_fstab >> ${D}${sysconfdir}/fstab
	
	sed -ie 's,_path,${TRIK_USER_PARTION_CREATION_DIR},g' ${S}/trik-hostname.sh

        install -m 0755 -D -t ${D}${sysconfdir}/init.d ${S}/init-trik
        install -m 0755 -D -t ${D}${datadir}/trik/init.d/ ${S}/trik-hostname.sh

}
#generate_fstab() {
#	echo "# additional entries for trik user partion"
#	echo "/dev/mmcblk0p2 ${TRIK_USER_PARTION_CREATION_DIR} vfat defaults,sync,noauto,uid=65534,gid=65534  0  0"
#} 
FILES_${PN} += "${sysconfdir}/init.d/ ${datadir}/trik/init.d/"
