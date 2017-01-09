SUMMARY = "Provides extra target libs for TRIK toolchain"
LICENSE = "MIT"

PR = "r2"

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
    roslaunch-dev \
    libusb1-dev \
    sip-dev\
"
