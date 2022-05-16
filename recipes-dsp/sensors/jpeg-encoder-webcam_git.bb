require video-sensor-webcam-common.inc
SUMMARY		 = "TRIK mjpg encoder for webcam (arm + dsp binary parts)"
RPROVIDES_${PN} = "mjpg-encoder-webcam"
PR="r1"
do_install_append() {
   
   echo '$(dirname $0)/jpeg-encoder-webcam $*' > ${D}/etc/init.d/mjpg-encoder-webcam
   chmod a+x ${D}/etc/init.d/mjpg-encoder-webcam

}
