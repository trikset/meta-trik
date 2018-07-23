SUMMARY = "Provides extra target libs for TRIK toolchain"
LICENSE = "MIT"

PR = "r3"

inherit packagegroup

RDEPENDS_${PN} += "\
	linux-libc-headers-dev \
	kernel-dev \
	alsa-dev \
	libnl-dev \
	v4l-utils-dev \
	libv4l-dev \
	boost-dev \
	hidapi-dev \
	libusb1-dev \
	protobuf-dev  \
	roslaunch-dev \
	trik-runtime-qt5-dev \
	trik-libcodecengine-client-staticdev \
"
