PACKAGECONFIG_FONTS = "fontconfig freetype"
#PACKAGECONFIG[dyngl] = "-opengl dynamic,,,"
PACKAGECONFIG_GL = "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'gles2', 'no-opengl', d)}"
PACKAGECONFIG_FB = "directfb linuxfb"
PACKAGECONFIG_DISTRO = "icu"
#QT_CONFIG_FLAGS += " -reduce-relocations " commented out until QTBUG-36129 is fixed
QT_CONFIG_FLAGS += "-force-asserts -qreal float "
