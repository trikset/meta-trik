DESCRIPTION = "trik-ros"
SECTION = "devel"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://package.xml;;beginline=16;endline=16;md5=87c9cb252374acacb6e366c0616d32a5"

DEPENDS = "roscpp rospy std-msgs nodelet trik-runtime-qt5 freenect-camera"
RDEPENDS_${PN} = "roscpp rospy std-msgs nodelet trik-runtime"
inherit qmake2
SRC_URI = "git://github.com/trikset/${BPN};branch=master;tag=${PV}"
S = "${WORKDIR}/git"

EXTRA_OECMAKE_prepend = "-DQT=${STAGING_INCDIR}/qt5 -DTRIK=${STAGING_INCDIR}/trikRuntime "
#OECMAKE_CXX_FLAGS_prepend = "-x c++ -std=c++11 "
inherit catkin

FILES_${PN} = "${ros_datadir}/* ${ros_libdir}/*"
