DESCRIPTION = "A PC-side Library designed to encapsulate all low level Bootstrap Loader (BSL) commands and common functions"
HOMEPAGE = "http://github.com/MSP-LaneWestlund/MSPBSL_Library"
SECTION = "libs"
LICENSE = "LGPL-2.1-or-later"
DEPENDS = "boost hidapi"
COMPATIBLE_MACHINE = "trikboard"

S="${WORKDIR}/git"

BRANCH="master"
TAG="${PV}"

SRC_URI ="git://github.com/trikset/trik-mspbsl-library.git;protocol=https;branch=${BRANCH};rev=ee5e5bfe7aa00a8421a4d8203c82adb5445ecbe0"

LIC_FILES_CHKSUM="file://LICENSE.txt;beginline=1;endline=355;md5=fd140d36d9ffcb0548c8c21659083810"

EXTRA_OECMAKE ="-DLIB_INSTALL_DIR=${libdir} -DLIBEXEC_INSTALL_DIR=${libexecdir}"

inherit cmake

B="${S}"


