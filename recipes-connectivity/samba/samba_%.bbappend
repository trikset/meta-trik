FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
#RRECOMMENDS_winbind += "libnss-winbind"
SRC_URI += "file://trik-smb.conf"
#inherit user-partion
install_append () {
	install -m 0644 -D ${S}/trik-smb.conf ${D}${sysconfdir}/samba/smb.conf

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



