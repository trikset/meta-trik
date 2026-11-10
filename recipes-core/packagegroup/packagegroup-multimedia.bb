SUMMARY = "Multimedia libraries package"
DESCRIPTION = "Multimedia libraries and utilities"
LICENSE = "LGPL-2.0-only"
PR = "r4"

inherit packagegroup

# MediaMTX is shipped alongside mjpg-streamer, not instead of it. Moving the
# runtime to RTSP/WebRTC needs major trikRuntime changes, so mjpg-streamer
# stays the default streamer for now. MediaMTX is available to experiment with
# RTSP/WebRTC manually from the console (e.g. publishing with gstreamer).
RDEPENDS:${PN} = "\
  alsa-utils \
  espeak \
  v4l-utils \
  gstreamer1.0-plugins-good \
  gstreamer1.0-plugins-bad \
  gstreamer1.0-plugins-bad-v4l2codecs \
  gstreamer1.0-meta-video \
  gstreamer1.0-plugins-base \
  gstreamer1.0-meta-audio \
  gstreamer1.0-rtsp-server \
  gstreamer1.0-rockchip \
  rockchip-mpp \
  flac \
  mjpg-streamer \
  mediamtx \
 "
#vlc
#  ffmpeg
RRECOMMENDS:${PN} = "\
 "
#pocketsphinx 
#  sox 
#  bigbuckbunny-480p

