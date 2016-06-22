SUMMARY = "DSP cmemk & syslink dsp driver "
SECTION = "dsp"
LICENSE = "CLOSED" 

inherit module

KERNEL_MODULE_AUTOLOAD += "syslink"
RDEPENDS_${PN} += "cmem-mod"
SRC_URI = "file://syslink.ko"

do_compile() {
	bbnote "Empty do_compile task"
} 
do_install(){
	install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/dsp
	install -m 0755 ${WORKDIR}/syslink.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/dsp/
}
