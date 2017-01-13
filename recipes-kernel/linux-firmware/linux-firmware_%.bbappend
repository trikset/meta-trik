#FILES_${PN}-wl12xx = ""
do_install_append() {
    rm -rf ${D}/lib/firmware/wl12*
    rm -rf ${D}/lib/firmware/TI*
    rm -rf ${D}/lib/firmware/LICENCE.ti-connectivity
    rm -rf ${D}/lib/firmware/ti-connectivity
}

