DECRIPTION = "gzdoom -- OpenGL version of doom engine"
LICENSE = "GPLv2+"
#LIC_FILES_CHKSUM = "file://COPYING;md5=14aa9744482b9e7ee47eef837e04c26e"

PROVIDES += "virtual/doom games/doom"
inherit cmake
# prefere sdl2
#inherint sdl
PV = "2.6+git${SRCPV}"
BBCLASSEXTEND = "native"

DEPENDS += "virtual/libsdl2  fluidsynth"
DEPENDS_trikboard += "virtual/libgl"

EXTRA_OECMAKE += "-DNO_FMOD=ON -DNO_OPENAL=ON"
EXTRA_OECMAKE_trikboard += "-DFORCE_CROSSCOMPILE=ON"

# "-DNO_GENERATOR_EXPRESSIONS=ON"

#FREEDOOM_VERSION = "0.10.1"

SRC_URI = "git://github.com/coelckers/gzdoom;rev=master"

#https://github.com/freedoom/freedoom/releases/download/v${FREEDOOM_VERSION}/freedoom-${FREEDOOM_VERSION}.zip"

#SRC_URI[md5sum] = "93897b98dd709bd5dac5217f7ca0c6cf"
#SRC_URI[sha256sum] = "7d20c4b458b6966a58f8f1d9d67db67999364a66e67616e2c320d01b396338da"

S="${WORKDIR}/git"
#FILES_${PN}-dbg += "/usr/games/.debug"
#FILES_${PN} += "/usr"

#do_install_append() {
#    install -m 0644 ${WORKDIR}/freedoom-${FREEDOOM_VERSION}/*.wad ${D}${datadir}/games/doom
#}
