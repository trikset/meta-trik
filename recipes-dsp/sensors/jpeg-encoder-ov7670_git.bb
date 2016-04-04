require video-sensor-ov7670-common.inc
SUMMARY		 = "TRIK mjpg encoder for ov7670 (arm + dsp binary parts)"
RPROVIDES_${PN} = "mjpg-encoder-ov7670"
PR="r1"
do_install_append() {
   
   echo '$(dirname $0)/jpeg-encoder-ov7670 $*' > ${D}/etc/init.d/mjpg-encoder-ov7670
   chmod a+x ${D}/etc/init.d/mjpg-encoder-ov7670

}
