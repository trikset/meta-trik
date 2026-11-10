SUMMARY = "Provides extra target libs for TRIK toolchain"
LICENSE = "MIT"

PR = "r3"

PACKAGE_ARCH = "${TUNE_PKGARCH}"

inherit packagegroup

RDEPENDS:${PN} += "\
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
	rockchip-mpp-dev \
	librga-dev \
	libgles2-mesa-dev \
	libgles3-mesa-dev \
	trik-runtime-qt5-dev \
	gcc-sanitizers \
"
# trik-ros-dev

