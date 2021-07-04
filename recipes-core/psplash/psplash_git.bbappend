FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += " file://0001-psplash-Add-Arago-custom-color.patch \
             file://0002-mount-bug-fixed.patch;patchdir=.."
SPLASH_IMAGES = "file://psplash-trik.png;outsuffix=default"

FILES_${PN} += "/mnt/.psplash"

do_install_append() {
        install -d ${D}/mnt/.psplash/
}
