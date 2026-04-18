SUMMARY = "Realtek rtw88xx USB WiFi driver"
DESCRIPTION = "Out-of-tree kernel module for rtw88xx"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

inherit module

# rtw88 are mainline Linux modules for all wi-fi 5 chips
# disable rtl8822b chip as it is non-functional 
# may work on Linux 5+, worth trying after upgrade
SRC_URI = "git://github.com/lwfinger/rtw88.git;protocol=https;branch=master \
           file://rtw88/support-linux-4.14-rtw88.patch \
           file://rtw88/disable-rtl8822b-rtw88.patch \
           "
SRCREV = "d2258b4de21aeabf7ef85ec0cada1f3cff9bcbe0"
S = "${WORKDIR}/git"

# Only USB support is needed
# These CONFIGs may be set in the defconfig, override them for the driver
EXTRA_OEMAKE += "CONFIG_PCI=n"

# here just empty
EXTRA_OEMAKE += "CONFIG_MMC="

FILES:${PN} += "/lib/firmware/rtw88/*"

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0644 ${S}/*.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
        
    install -d ${D}${nonarch_base_libdir}/firmware/rtw88
    install -m 0644 ${S}/firmware/*.bin ${D}${nonarch_base_libdir}/firmware/rtw88/
}
