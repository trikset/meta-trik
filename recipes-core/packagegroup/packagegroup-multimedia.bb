SUMMARY = "Multimedia libraries package"
DESCRIPTION = "Multimedia libraries and utilities"
LICENSE = "LGPLv2"
PR = "r4"

inherit packagegroup

RDEPENDS_${PN} = "\
  alsa-utils \
  espeak \
  v4l-utils \
  gstreamer1.0-plugins-bad \
  gstreamer1.0-plugins-good \
  gstreamer1.0-meta-video \
  gstreamer1.0-plugins-base \
  gstreamer1.0-meta-audio \
  gstreamer1.0-rtsp-server \
  sox \
  flac \
  mjpg-streamer \
  ffmpeg \
  live555 \
  lsdldoom \
  bigbuckbunny-480p \
 "
#vlc
RRECOMMENDS_${PN} = "\
 "
#pocketsphinx 

