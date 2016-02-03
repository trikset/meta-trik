SUMMARY = "Utils"
DESCRIPTION = "The minimal set of packages required to manupulate usb, i2c,input devices,ext2FS"
LICENSE = "LGPL"
PR = "r0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup


MACHINE_ESSENTIAL_EXTRA_RDEPENDS ?= ""
MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS ?= ""

RDEPENDS_${PN} = "\
  iptables \
  mtd-utils \
  evtest \
  i2c-tools \
  usbutils \
  e2fsprogs \
  mc \
  lrzsz \
  ntp \
  strace \
  ltrace \
  cronie \
  tzdata \
  rsync \
  fbgrab \
  fbshot \
  u-boot-trik-fw-utils \
  curl \
  psplash \
  picocom \
  usb-modeswitch \
  usb-modeswitch-data \
  rp-pppoe \
  samba \
  winbind \
  bash-completion \
  openvpn \
  can-utils \
  iproute2 \
  iftop \
  iotop \
  dstat \
  smem \
  fbgrab \
  fim \
  ${MACHINE_ESSENTIAL_EXTRA_RDEPENDS} \
 "

RRECOMMENDS_${PN} = "\
  ${MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS} \
 "

