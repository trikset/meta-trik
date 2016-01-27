DECRIPTION = "lsdldoom -- SDL version of doom engine"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=14aa9744482b9e7ee47eef837e04c26e"

PROVIDES += "virtual/doom games/doom"

inherit cmake
# prefere sdl2
#inherint sdl

DEPENDS += "virtual/libsdl2 libsdl-mixer libsdl-image"

#FREEDOOM_VERSION = "0.10.1"

SRC_URI = "git://github.com/rlsosborne/doom;rev=master\
            file://no_inline.patch\
            file://pixelformat_bgr565.patch\
            "

#https://github.com/freedoom/freedoom/releases/download/v${FREEDOOM_VERSION}/freedoom-${FREEDOOM_VERSION}.zip"

SRC_URI[md5sum] = "93897b98dd709bd5dac5217f7ca0c6cf"
SRC_URI[sha256sum] = "7d20c4b458b6966a58f8f1d9d67db67999364a66e67616e2c320d01b396338da"

S="${WORKDIR}/git"
FILES_${PN}-dbg += "/usr/games/.debug"
FILES_${PN} += "/usr"

#do_install_append() {
#    install -m 0644 ${WORKDIR}/freedoom-${FREEDOOM_VERSION}/*.wad ${D}${datadir}/games/doom
#}
