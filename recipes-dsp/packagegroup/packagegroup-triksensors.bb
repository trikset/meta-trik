SUMMARY="TRIK Sensors "
DESCRIPTION = "The minimal set of packages reguired for TRIK video sensors"
LICENSE = "Apache-2.0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup


MACHINE_ESSENTIAL_EXTRA_RDEPENDS ?= ""
MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS ?= ""

RDEPENDS_${PN} = "\
	edge-line-sensor-ov7670 \
	edge-line-sensor-webcam \
	line-sensor-ov7670 \
	line-sensor-webcam \
	mxn-sensor-ov7670 \
	mxn-sensor-webcam \
	object-sensor-ov7670 \
	object-sensor-webcam \
	jpeg-encoder-ov7670 \
	jpeg-encoder-webcam \
	motion-sensor-ov7670 \
	motion-sensor-webcam \
	${MACHINE_ESSENTIAL_EXTRA_RDEPENDS} \
	"
RRECOMMENDS_${PN} = "\
	${MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS} \
	"

