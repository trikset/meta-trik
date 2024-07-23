FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

inherit update-rc.d

INITSCRIPT_NAME = "haveged"
INITSCRIPT_PARAMS="start 00 S ."

SRC_URI += "file://haveged"

do_install:append () {
	install -d ${D}${sysconfdir}/init.d
        install -m 0755 -D -t ${D}${sysconfdir}/init.d ${WORKDIR}/haveged
}

