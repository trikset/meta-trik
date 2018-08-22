TRIK_RUNTIME_UPDATE = "13"
PR="r${TRIK_RUNTIME_UPDATE}"
require trik-runtime-qt5.inc
DEPENDS += "nanomsg"
RDEPENDS_${PN} += "nanomsg"


# For script gathering logs information
RDEPENDS_${PN} += "bash xz"
# Commented out, because trik-pythonqt is compiled into trik-runtime as git submodule
#RRECOMMENDS_${PN} += "trik-pythonqt"
