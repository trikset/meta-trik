SUMMARY = "Multimedia libraries package"
DESCRIPTION = "Multimedia libraries and utilities"
LICENSE = "LGPL"
PR = "r3"

inherit packagegroup

RDEPENDS_${PN} = "\
  vlc \
  alsa-utils \
  espeak \
  flite-alsa \
  rhvoice\
  v4l-utils \
  gst-meta-base \
  gst-meta-audio \
  gst-meta-video \
  sox \
  flac \
  mjpg-streamer \
  pocketsphinx \
  pocketsphinx-tools \
  sphinxtrain \
 "

RRECOMMENDS_${PN} = "\
 "

