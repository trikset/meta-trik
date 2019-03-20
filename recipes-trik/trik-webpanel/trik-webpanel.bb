DESCRIPTION = "webpanel for trik"
HOMEPAGE = "http://trikset.com"
LICENSE = "Apache-2.0"
RDEPENDS_${PN} = "trik-runtime v4l-utils gawk"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRCREV = "${AUTOREV}"
PV="0-git${SRCPV}"
S = "${WORKDIR}/git"

SRC_URI = "git://github.com/trikset/trik-webpanel.git;branch=master"

do_compile(){
	bbnote "Empty do_compile task"
}
do_install(){
	install -d ${D}/www/pages/cgi-bin
	install -d ${D}/www/pages/fonts
	install -d ${D}/www/pages/images
	install -d ${D}/www/pages/js
	install -d ${D}/www/pages/styles
	cp -rvf ${S}/www/. ${D}/www/pages/
}

FILES_${PN} = "${TRIK_USER_PARTION_CREATION_DIR}"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

INSANE_SKIP_${PN} += "installed-vs-shipped"
