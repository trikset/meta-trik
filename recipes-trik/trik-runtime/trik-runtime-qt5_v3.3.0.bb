PR="r2"
TRIK_RUNTIME_UPDATE = "2"
require trik-runtime-qt5.inc
#DEPENDS += "trik-pythonqt"
#RDEPENDS_${PN} += "trik-pythonqt"

#Soft dependency
RRECOMMENDS_${PN} += "trik-pythonqt"
