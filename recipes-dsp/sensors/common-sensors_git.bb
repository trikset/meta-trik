require video-sensor-ov7670-common.inc
require video-sensor-webcam-common.inc
SUMMARY		 = "Init script  for ov7670 camera "
PR="1"
SRC_URI = "file://media-sensor file://init-ov7670-320x240.sh file://media-sensor-dummy"

do_install() {
	install -d -m 0755 ${D}/etc/trik/
	install -d -m 0755 ${D}/etc/init.d/
	install -m 0755 ${WORKDIR}/init-ov7670-320x240.sh ${D}/etc/trik/
	install -m 0755 ${WORKDIR}/media-sensor ${D}/etc/init.d/
	install -m 0755 ${WORKDIR}/media-sensor-dummy ${D}/etc/init.d/
}

