SUMMARY = "Qt for Embedded TRIK Linux (Qt without X11)"
PR = "r5"
LICENSE = "MIT"

inherit packagegroup

RDEPENDS_${PN} = " \
        icu \
        libqt-embeddedcore4 \
        libqt-embeddedgui4 \
	    libqt-embeddeddbus4 \
        libqt-embeddednetwork4 \
        libqt-embeddedscript4 \
        libqt-embeddedmultimedia4 \
        libqt-embeddedgui4 \
        libqt-embeddedscripttools4 \
	    qt4-embedded-fonts-ttf-dejavu \
        qt4-embedded-plugin-imageformat-jpeg \
        qt4-embedded-plugin-imageformat-tiff \
        qt4-embedded-plugin-sqldriver-sqlite \
	qt4-embedded-plugin-gfxdriver-gfxtransformed \
	sip\
"

