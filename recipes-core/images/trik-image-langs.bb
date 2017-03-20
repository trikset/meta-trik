require trik-image.bb

IMAGE_INSTALL += "\
		mono \
		fsharp \
		python-pip \
		python-numpy \
		python-pyqt \
		protobuf \
"

TOOLCHAIN_HOST_TASK += "nativesdk-cmake nativesdk-make nativesdk-protobuf"

# trik-ros roslaunch"

