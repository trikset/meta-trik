SUMMARY		 = "Init script  for ov7670 camera "
SRC_URI = "file://media-sensor file://init-ov7670-320x240.sh file://media-sensor-dummy"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
PR="2"
RDEPENDS_${PN} += "bash"
do_install() {
	install -d -m 0755 ${D}/etc/trik/
	install -d -m 0755 ${D}/etc/init.d/
	install -m 0755 ${WORKDIR}/init-ov7670-320x240.sh ${D}/etc/trik/
	install -m 0755 ${WORKDIR}/media-sensor ${D}/etc/init.d/
	install -m 0755 ${WORKDIR}/media-sensor-dummy ${D}/etc/init.d/
}

