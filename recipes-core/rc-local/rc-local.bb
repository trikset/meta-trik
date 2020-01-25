DESCRIPTION = "rc.local scripts"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=77a4892df63d2a51b015b46a07528112"

SRC_URI = "file://rc.local.etc \
           file://rc.local.init \
           file://LICENSE"

S = "${WORKDIR}"

RDEPENDS_${PN} += "bash"

inherit update-rc.d

INITSCRIPT_NAME = "zrc.local"
INITSCRIPT_PARAMS = "start 99 2 3 4 5 ."

do_install () {
    install -d ${D}/${sysconfdir}/init.d
    install -m 755 ${S}/rc.local.etc ${D}/${sysconfdir}/rc.local
    install -m 755 ${S}/rc.local.init ${D}/${sysconfdir}/init.d/zrc.local 
}
