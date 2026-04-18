SUMMARY = "Realtek rtl8188fu USB WiFi driver"
DESCRIPTION = "Out-of-tree kernel module for rtl8188fu"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

inherit module

SRC_URI = "git://github.com/RinCat/RTL88x2BU-Linux-Driver.git;protocol=https;branch=master \
	   file://rtl8822bu/support-trik-platform-rtl8822bu.patch \
	   "
SRCREV = "825556e195ecde9ce8f5f4cbad9953f398c8598e"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0644 ${S}/*.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
}
