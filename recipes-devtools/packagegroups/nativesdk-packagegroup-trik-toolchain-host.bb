SUMMARY = "Provides extra host tools for TRIK toolchain"
LICENSE = "MIT"

PR = "r1"

inherit packagegroup

RDEPENDS:${PN} += "\
	nativesdk-cmake \
	nativesdk-make \
	nativesdk-protobuf \
"
#nativesdk-catkin
