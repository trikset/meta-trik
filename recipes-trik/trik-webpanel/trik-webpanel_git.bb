DESCRIPTION = "Webpanel for trik"
HOMEPAGE = "http://trikset.com"
LICENSE = "Apache-2.0"
RDEPENDS_${PN} = "lighttpd"
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
	
	install -m 0644 ${S}/www/configurator.html ${D}/www/pages/
	install -m 0755 ${S}/www/cgi-bin/* ${D}/www/pages/cgi-bin/
	install -m 0644 ${S}/www/js/* ${D}/www/pages/js/
	install -m 0644 ${S}/www/styles/* ${D}/www/pages/styles/
	install -m 0644 ${S}/www/images/* ${D}/www/pages/images/
	install -m 0644 ${S}/www/fonts/* ${D}/www/pages/fonts/
}


FILES_${PN} += "/www/pages"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
