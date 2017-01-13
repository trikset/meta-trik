DESCRIPTION = "Firmware files for use with Trik Linux kernel"
SECTION = "kernel"

COMPATIBLE_MACHINES = "trikboard"
LICENSE = "Proprietary"

LIC_FILES_CHKSUM = "file://LICENCE.ti-connectivity;md5=186e7a43cf6c274283ad81272ca218ea"
RPROVIDES_${PN}="linux-firmware-wl12xx"
PREFERRED_PROVIDER_linux-firmware-wl12xx="linux-firmware-trik-wl1271"
SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/trikset/trik-firmware.git"

S = "${WORKDIR}/git"


inherit allarch update-alternatives

do_compile() {
        :
}
do_install() {
        install -d  ${D}/lib/firmware/
        cp -r * ${D}/lib/firmware/

       # fixup wl12xx location, after 2.6.37 the kernel searches a different location for it
        ( cd ${D}/lib/firmware ; ln -sf ti-connectivity/* . )
}

FILES_${PN} += "/lib/firmware/*"


