SUMMARY = "Multimedia libraries package"
DESCRIPTION = "Multimedia libraries and utilities"
LICENSE = "LGPLv2"
PR = "r4"

inherit packagegroup

RDEPENDS:${PN} = "\
  alsa-utils \
  espeak \
  v4l-utils \
  gstreamer1.0-plugins-good \
  gstreamer1.0-meta-video \
  gstreamer1.0-plugins-base \
  gstreamer1.0-meta-audio \
  gstreamer1.0-rtsp-server \
  flac \
  mjpg-streamer \
  live555 \
 "
#vlc
#  ffmpeg
RRECOMMENDS:${PN} = "\
 "
#pocketsphinx 
#  sox 
#  bigbuckbunny-480p

