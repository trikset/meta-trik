SUMMARY = "Multimedia libraries package"
DESCRIPTION = "Multimedia libraries and utilities"
LICENSE = "LGPLv2"
PR = "r3"

inherit packagegroup

RDEPENDS_${PN} = "\
  alsa-utils \
  espeak \
  v4l-utils \
  gst-meta-base \
  gst-meta-audio \
  gst-meta-video \
  gst-rtsp \
  sox \
  flac \
  mjpg-streamer \
  pocketsphinx \
 "

RRECOMMENDS_${PN} = "\
 "

