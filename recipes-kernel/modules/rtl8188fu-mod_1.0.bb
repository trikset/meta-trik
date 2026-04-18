SUMMARY = "Realtek rtl8188fu USB WiFi driver"
DESCRIPTION = "Out-of-tree kernel module for rtl8188fu"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

inherit module

SRC_URI = "git://github.com/kelebek333/rtl8188fu.git;protocol=https;branch=master \
	   file://rtl8188fu/support-trik-platform-rtl8188fu.patch \
	   "
SRCREV = "7ce43037212aab03a5cfe441992eee04de7f858d"

S = "${WORKDIR}/git"

FILES:${PN} += "/lib/firmware/rtlwifi/*"

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0644 ${S}/*.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    
    install -d ${D}${nonarch_base_libdir}/firmware/rtlwifi
    install -m 0644 ${S}/firmware/*.bin ${D}${nonarch_base_libdir}/firmware/rtlwifi/
}
