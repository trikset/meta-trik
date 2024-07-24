FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += " file://0001-psplash-Add-Arago-custom-color.patch"
SPLASH_IMAGES = "file://psplash-trik.png;outsuffix=default"

FILES:${PN} += "/mnt/.psplash"

do_install:append() {
        install -d ${D}/mnt/.psplash/
}
