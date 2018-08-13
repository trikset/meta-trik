TRIK_RUNTIME_UPDATE = "12"
PR="r${TRIK_RUNTIME_UPDATE}"
require trik-runtime-qt5.inc
DEPENDS += "nanomsg"
RDEPENDS_${PN} += "nanomsg"
#RDEPENDS_${PN} += "trik-pythonqt"

#Soft dependency
RRECOMMENDS_${PN} += "trik-pythonqt"
