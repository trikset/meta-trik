SUMMARY  = "This is a small command line utility for grabbing JPEGs form V4L2 devices (e.g. USB webcams)."
HOMEPAGE = "https://github.com/twam/v4l2grab/tree/master"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1a689deca649cde4b83b2327802f9d68"
DEPENDS ="jpeg" 
inherit autotools-brokensep

SRCREV= "15574fc3da42400243971fa3d0f98894b0f9d947"
SRC_URI = "git://github.com/twam/v4l2grab.git;protocol=https;rev=${SRCREV}"

S="${WORKDIR}/git"
