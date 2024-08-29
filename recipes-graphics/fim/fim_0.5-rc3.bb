SUMMARY = "Framebuffer (scriptable) image viewer"
DESCRIPTION = "FIM (Fbi IMproved) aims to be a highly customizable and scriptable \
               image viewer targeted at users who are comfortable with software \
               like the Vim text editor or the Mutt mail user agent."
SECTION = "utils"
HOMEPAGE = "http://www.autistici.org/dezperado/fim/"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=fa01bff138cc98a62b8840a157951c88"


# flex with provide /usr/include/FlexLexer.h
DEPENDS = "flex-native bison-native flex libexif"
RDEPENDS:${PN} += "bash"

SRC_URI = "${SAVANNAH_NONGNU_MIRROR}/fbi-improved/${BPN}-${PV}.tar.gz \
      file://cross_cc.patch \
      file://comment.patch"

SRC_URI[md5sum] = "f31d4917a23606df03406a017e7ee509"
SRC_URI[sha256sum] = "82816225ae89b246c37a93a95c9c45f88163d56d7d74f681743f19020b525389"

PARALLEL_MAKE = ""

inherit autotools pkgconfig

# Don't use provided regex.c
EXTRA_OECONF = "fim_cv_regex=no fim_cv_regex_broken=no \
    --enable-framebuffer \
    --disable-djvu \
    --disable-ps \
    --disable-xcftopnm \
    --disable-convert \
    --disable-inkscape \
    --disable-xfig \
    --disable-dia \
    --disable-sdl \
    --disable-aa \
    --enable-custom-status-bar \
"

# Note: imlib2 is located in meta-efl layer.
PACKAGECONFIG ?= "jpeg rl hf"

PACKAGECONFIG[png] = "--enable-png,--disable-png,libpng"
PACKAGECONFIG[jpeg] = "--enable-jpeg,--disable-jpeg,jpeg"
PACKAGECONFIG[tiff] = "--enable-tiff,--disable-tiff,tiff"
PACKAGECONFIG[gif] = "--enable-gif,--disable-gif,giflib"
PACKAGECONFIG[pdf] = "--enable-poppler,--disable-poppler,poppler"
PACKAGECONFIG[magick] = "--enable-graphicsmagick,--disable-graphicsmagick,imagemagick"
PACKAGECONFIG[imlib2] = "--enable-imlib2,--disable-imlib2,imlib2"
PACKAGECONFIG[rl] = "--enable-readline,--disable-readline,readline"
PACKAGECONFIG[hf] = "--enable-hardcoded-font,--disable-hardcoded-font"
