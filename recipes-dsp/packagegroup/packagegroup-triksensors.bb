SUMMARY="TRIK Sensors "
DESCRIPTION = "The minimal set of packages reguired for TRIK video sensors"
LICENSE = "Apache-2.0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup


MACHINE_ESSENTIAL_EXTRA_RDEPENDS ?= ""
MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS ?= ""

RDEPENDS:${PN} = "\
	edge-line-sensor-ov7670 \
	line-sensor-ov7670 \
	line-sensor-webcam \
	mxn-sensor-ov7670 \
	object-sensor-ov7670 \
	object-sensor-webcam \
	jpeg-encoder-ov7670\
	motion-sensor-ov7670 \
	${MACHINE_ESSENTIAL_EXTRA_RDEPENDS} \
	"
RRECOMMENDS:${PN} = "\
	${MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS} \
	"

