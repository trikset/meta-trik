SUMMARY = "DSP cmemk & syslink dsp driver "
SECTION = "dsp"
PR = "r2"
#LICENSE = "BSD | GPLv2"
LICENSE = "CLOSED" 

inherit module

KERNEL_MODULE_AUTOLOAD += "syslink"
RDEPENDS_${PN} += "cmem-mod"
SRC_URI = "http://downloads.trikset.com/sources/${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "98853aed68db3bcba948ae3282eb35b1"
SRC_URI[sha256sum] = "08575855094bcc71431763601c4718fb831259267d1414a18eb1c69a2aa2c519"

do_compile() {
	bbnote "Empty do_compile task"
} 
do_install(){
	install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/dsp
	install -m 0755 ${S}/syslink.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/dsp/
	# install -m 0755 ${S}/cmemk.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/dsp/
}
