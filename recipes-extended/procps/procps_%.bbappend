do_install_append () {
    echo -en "# IPv6 disabled\n\
               net.ipv6.conf.all.disable_ipv6 = 1\n\
               net.ipv6.conf.default.disable_ipv6 = 1\n\
               net.ipv6.conf.lo.disable_ipv6 = 1\n" >> ${D}/${sysconfdir}/sysctl.conf
}
