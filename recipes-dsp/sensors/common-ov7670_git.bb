require video-sensor-ov7670-common.inc
SUMMARY		 = "Init script  for ov7670 camera "

SRC_URI = "file://media-sensor file://init-ov7670-320x240.sh"

do_install() {
	install -d -m 0755 ${D}/etc/trik/
	install -d -m 0755 ${D}/etc/init.d/
	install -m 0755 ${S}/init-ov7670-320x240.sh ${D}/etc/trik/
	install -m 0755 ${S}/media-sensor ${D}/etc/init.d/
	install -m 0755 ${S}/media-sensor-dummy ${D}/etc/init.d/
}

