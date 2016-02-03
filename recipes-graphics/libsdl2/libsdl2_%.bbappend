# ./configure complains unrecognized option for --enable-video-aalib --enable-video-fbcon
EXTRA_OECONF += "--enable-video-directfb --enable-video-dummy"
DEPENDS += "directfb"
BBCLASSEXTEND += "native"
