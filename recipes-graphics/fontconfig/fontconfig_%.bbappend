# tune font config
do_install_append() {
	#remove all default links
	find ${D}${sysconfdir}/fonts/conf.d/ -type l -exec rm {} +
	for c in 10-hinting-full 10-sub-pixel-rgb 11-lcdfilter-default 30-metric-aliases 70-yes-bitmaps
	do 
		ln -s -t ${D}${sysconfdir}/fonts/conf.d/ ${datadir}/fontconfig/conf.avail/${c}.conf
	done
}
