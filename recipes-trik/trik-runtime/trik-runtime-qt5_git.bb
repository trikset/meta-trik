SRCREV="${AUTOREV}"
TRIK_RUNTIME_UPDATE = "${SRCREV}"
GIT_REVISION = "branch=master"
PV = "git${SRCREV}"
PR = "r20240813"
#PR="r${TRIK_RUNTIME_UPDATE}"
require trik-runtime-qt5.inc
require trik-runtime-install-new.inc
DEPENDS += "rsync-native python3"
RDEPENDS_${PN} += "nanomsg"

#OE_QMAKE_CXXFLAGS_append += " -fno-lto"

#We need to switch precompiled headers off until this issue is fixed in trik-runtime sources
EXTRA_QMAKEVARS_PRE += " -r CONFIG+=noPch CONFIG+=sanitize_address CONFIG+=sanitize_undefined"

# For script gathering logs information
RDEPENDS_${PN} += "bash xz"
#INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
#INHIBIT_PACKAGE_STRIP = "1"
#DEBUG_BUILD = "1"
