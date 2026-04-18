SUMMARY = "Realtek rtl8188eu, rtl8188eu, rtl8188etv USB WiFi driver"
DESCRIPTION = "Out-of-tree kernel module for rtl8188eu, rtl8188eu, rtl8188etv"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

inherit module

SRC_URI = "git://github.com/aircrack-ng/rtl8188eus.git;protocol=https;branch=v5.3.9 \
	   file://rtl8188eu/support-trik-platform-rtl8188eu.patch \
	   "
SRCREV = "af3bf004458f76b7aec33e9ba552cd382ed1f5c3"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0644 ${S}/*.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
}
