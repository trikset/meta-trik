do_install_append() {
	C="${D}/${sysconfdir}/mini_x"
	mkdir -p $C
	/bin/echo -e "xset s off\nexec matchbox-window-manager -theme blondie -use_titlebar no -use_cursor no -use_desktop_mode plain -use_lowlight no -use_super_modal no" >> $C/session
	chmod +x $C/session
}
