PR="r8"
DESCRIPTION = "TRIK core base v2"
IMAGE_FEATURES += "package-management"
IMAGE_INSTALL = "packagegroup-base \
		packagegroup-core-boot \
		${ROOTFS_PKGMANAGE_BOOTSTRAP} \
		${CORE_IMAGE_EXTRA_INSTALL} \
		packagegroup-wifi \
		packagegroup-core-ssh-openssh \
		localedef \
		procps \
		packagegroup-firmware \
		packagegroup-utils \
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
		formfactor \
		coreutils \
		u-boot-trik \
		u-boot-trik-data \
		mini-x-session \
		linux-trik-data \
		trik-uart-ppp \
		trik-mems \
		haveged \
		python3-pip \
		python3-numpy \
		python3-pyserial \
"

IMAGE_LINGUAS = "en-us ru-ru"

LICENSE = "MIT"

inherit core-image

IMAGE_ROOTFS_SIZE = "1280000"

# remove not needed ipkg informations
#ROOTFS_POSTPROCESS_COMMAND += "remove_packaging_data_files ; "


