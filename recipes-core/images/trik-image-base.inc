PR="r9"
DESCRIPTION = "TRIK core base v2"
IMAGE_FEATURES += "package-management"
IMAGE_INSTALL = "packagegroup-base \
		packagegroup-core-boot \
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
		mini-x-session \
		linux-trik-data \
		trik-uart-ppp \
		trik-mems \
		python3-pip \
"

BAD_RECOMMENDATIONS += "rng-tools"

IMAGE_LINGUAS = "en-us ru-ru"

LICENSE = "MIT"

inherit core-image

IMAGE_ROOTFS_SIZE = "1280000"
