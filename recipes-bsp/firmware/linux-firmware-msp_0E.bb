SUMMARY = "TRIK MSP430 firmware"
#PR 	= "r1"
LICENSE = "CLOSED"

COMPATIBLE_MACHINE = "trikboard"

SRC_URI = "file://msp_reset.sh \
           file://msp-firmware-10${PV}.txt \
           file://msp-firmware-28${PV}.txt \
	   file://update_msp_fw.sh \
	"
SRC_URI[md5sum] = "509100434b0d6831bbf434173e136618"
SRC_URI[sha256sum] = "bc97217374d57dca62e47e05f35d234309b1da7f15d910ba52134103405536b1"
RDEPENDS_${PN} = "i2c-tools coreutils"

S = "${WORKDIR}"

do_compile[noexec] = "1"

FILES_${PN} += "${datadir}"

do_install(){
	install -m 0644 -D -t ${D}/etc/trik/msp430/ ${S}/msp-firmware-*${PV}.txt
	install -m 0755 -D -t ${D}/etc/trik/msp430/ ${S}/msp_reset.sh ${S}/update_msp_fw.sh
	mkdir -p ${D}/${datadir}/trik/init.d/ 
	ln -s -t ${D}/${datadir}/trik/init.d/ /etc/trik/msp430/update_msp_fw.sh
}

