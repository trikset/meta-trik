PR="r7"
TRIK_RUNTIME_UPDATE = "7"
require trik-runtime-qt5.inc
#DEPENDS += "trik-pythonqt"
#RDEPENDS_${PN} += "trik-pythonqt"

#Soft dependency
RRECOMMENDS_${PN} += "trik-pythonqt"
