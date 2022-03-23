DECRIPTION = "lsdldoom -- SDL version of doom engine"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=14aa9744482b9e7ee47eef837e04c26e"

PROVIDES += "virtual/doom games/doom"

inherit cmake
#inherit sdl

DEPENDS += "virtual/libsdl2"
#libsdl-mixer libsdl-image


SRC_URI = "git://github.com/rlsosborne/doom;protocol=https;rev=master\
            file://no_inline.patch\
            file://pixelformat_bgr565.patch\
            "
FREEDOOM_VERSION = "0.11"
#https://github.com/freedoom/freedoom/releases/download/v0.11/freedoom-0.11.zip
SRC_URI += "https://github.com/freedoom/freedoom/releases/download/v${FREEDOOM_VERSION}/freedoom-${FREEDOOM_VERSION}.zip"

SRC_URI[md5sum] = "83835f823e5139824bd3e37b369caba5"
SRC_URI[sha256sum] = "00efcb9975308d36d63143a25066ccfca14c9648906418a037bc2a2420f86181"


S="${WORKDIR}/git"
#FILES_${PN}-dbg += "/usr/games/.debug"
FILES_${PN} += "/usr"

do_install_append() {
    install -m 0644 ${WORKDIR}/freedoom-${FREEDOOM_VERSION}/*.wad ${D}${datadir}/games/doom
}
