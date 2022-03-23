DESCRIPTION = "Software for various robots built using TRIK (http://www.trikset.com/) constructor"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=19cbd64715b51267a47bf3750cc6a8a5"
RDEPENDS_${PN} = "trik-runtime v4l-utils gawk"
BRANCH  = "master"
SRCREV  = "${AUTOREV}"
SRC_URI = "git://github.com/trikset/trik-models.git;protocol=https;branch=${BRANCH}"
PV="0-git${SRCPV}"
S = "${WORKDIR}/git"

inherit user-partion

do_compile(){
	  bbnote "Empty do_compile task"
}
do_install(){
	install -d ${D}${TRIK_USER_PARTION_CREATION_DIR}/trik-examples/
	cp -rvf ${S}/* ${D}${TRIK_USER_PARTION_CREATION_DIR}/trik-examples/
}
FILES_${PN} = "${TRIK_USER_PARTION_CREATION_DIR}"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

INSANE_SKIP_${PN} = "ldflags arch"

