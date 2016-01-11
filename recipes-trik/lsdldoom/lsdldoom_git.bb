DECRIPTION = "lsdldoom -- SDL version of doom engine"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=14aa9744482b9e7ee47eef837e04c26e"

PROVIDES += "virtual/doom games/doom"

inherit sdl cmake

SRC_URI = "git://github.com/rlsosborne/doom;rev=master\
            file://no_inline.patch"
SRC_URI[md5sum] = "9558e009ec5079fda3d7eaf626536411"
SRC_URI[sha256sum] = "9004f456b0cbfc65a24c028176298f5fd15d479db422981829b95c014a81d968"

S="${WORKDIR}/git"
FILES_${PN}-dbg += "/usr/games/.debug"
FILES_${PN} += "/usr"


