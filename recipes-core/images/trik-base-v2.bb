PR="r7"
DESCRIPTION = "TRIK core base v2"
IMAGE_FEATURES += "package-management"
IMAGE_INSTALL = "packagegroup-base \
		packagegroup-core-boot \
		${ROOTFS_PKGMANAGE_BOOTSTRAP} \
		${CORE_IMAGE_EXTRA_INSTALL} \
		packagegroup-wifi \
		packagegroup-qte-trik \
		packagegroup-core-ssh-dropbear \
		localedef \
		procps \
		packagegroup-firmware \
		packagegroup-utils \
		packagegroup-multimedia \
		packagegroup-core-tools-debug \
	        packagegroup-core-tools-profile \
		udev-extraconf \
		mspbsl \
		rc-local\
		trik-runtime \
		trik-examples \
		dsp-modules \
		softap-udhcpd-config \
		trik-network \
		fuse \
		eglibc-utils \
		packagegroup-triksensors \
		distro-feed-configs \
		packagegroup-ros-comm \
		roslaunch \
		trik-ros \
		formfactor \
		live555 \
		ffmpeg \
"

IMAGE_LINGUAS = "en-us ru-ru"

LICENSE = "MIT"

inherit core-image

IMAGE_ROOTFS_SIZE = "1280000"

# remove not needed ipkg informations
#ROOTFS_POSTPROCESS_COMMAND += "remove_packaging_data_files ; "


