DESCRIPTION = "trik-ros"
SECTION = "devel"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://package.xml;;beginline=16;endline=16;md5=87c9cb252374acacb6e366c0616d32a5"
inherit ros_catkin cmake_qt5

DEPENDS = "trik-runtime-qt5 nodelet sensor-msgs geometry-msgs"
SRC_URI = "git://github.com/trikset/${BPN};branch=master;tag=${PV}"
S = "${WORKDIR}/git"

EXTRA_OECMAKE_prepend = "-DQT=${STAGING_INCDIR}/qt5 -DTRIK=${STAGING_INCDIR}/trikRuntime "

FILES_${PN} = "${ros_datadir}/* ${ros_libdir}/*"
