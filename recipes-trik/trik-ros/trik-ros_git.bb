DESCRIPTION = "trik-ros"
SECTION = "devel"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://package.xml;;beginline=16;endline=16;md5=87c9cb252374acacb6e366c0616d32a5"

DEPENDS = "roscpp catkin rospy std-msgs nodelet trik-runtime freenect-camera"
RDEPENDS_${PN} = "roscpp rospy std-msgs nodelet trik-runtime"

SRC_URI = "git://github.com/auduchinok/trik-ros;branch=master"
SRCREV = "${AUTOREV}"
PV .= "${SRCPV}"
S = "${WORKDIR}/git"

EXTRA_OECMAKE_prepend = "-DQT=${STAGING_INCDIR}/qtopia -DTRIK=${STAGING_INCDIR}/trikRuntime"
OECMAKE_CXX_FLAGS_prepend = "-std=c++11 "
inherit catkin

FILES_${PN} = "${ros_datadir}/* ${ros_libdir}/*"
