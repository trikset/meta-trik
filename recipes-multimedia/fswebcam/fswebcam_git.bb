SUMMARY  = "fswebcam - Small and simple webcam software for *nix."
HOMEPAGE = "https://github.com/fsphil/fswebcam"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=393a5ca445f6965873eca0259a17f833"
DEPENDS = "gd"
inherit autotools-brokensep

SRCREV= "66debb20a4ae5ca88d435c85f7bd9431e6f0c444"

SRC_URI = "git://github.com/fsphil/fswebcam.git;protocol=https;rev=${SRCREV}"

S="${WORKDIR}/git"

