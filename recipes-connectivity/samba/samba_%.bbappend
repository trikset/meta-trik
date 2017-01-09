FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
#RRECOMMENDS_winbind += "libnss-winbind"

inherit user-partion

do_install_append() {
	echo -e "\n[user-part]" >> ${D}${sysconfdir}/samba/smb.conf
	echo -e "\tpath = ${TRIK_USER_PARTION_CREATION_DIR}" >> ${D}${sysconfdir}/samba/smb.conf
	echo -e "\tpublic = yes" >> ${D}${sysconfdir}/samba/smb.conf
	echo -e "\twritable = yes" >> ${D}${sysconfdir}/samba/smb.conf
	echo -e "\tcomment = trik user partion" >> ${D}${sysconfdir}/samba/smb.conf
	echo -e "\tprintable = no" >> ${D}${sysconfdir}/samba/smb.conf
	echo -e "\tguest ok = yes" >> ${D}${sysconfdir}/samba/smb.conf
}

pkg_postinst_libnss-winbind () {
	sed -e '/^hosts:/s/\s*\<wins\>//' \
		-e 's/\(^hosts:.*\)\(\<files\>\)\(.*\)\(\<dns\>\)\(.*\)/\1\2\3 wins \4\5/' \
		-i $D${sysconfdir}/nsswitch.conf
}

pkg_prerm_libnss-winbind () {
	 sed -e '/^hosts:/s/\s*\<wins\>//' \
		-i $D${sysconfdir}/nsswitch.conf 
}



