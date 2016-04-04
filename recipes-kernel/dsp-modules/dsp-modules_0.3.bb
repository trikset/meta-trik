SUMMARY = "DSP cmemk & syslink dsp driver "
SECTION = "dsp"
LICENSE = "CLOSED"

inherit module

KERNEL_MODULE_AUTOLOAD += "syslink cmemk"
KERNEL_MODULE_PROBECONF +="cmemk"
module_conf_cmemk="options cmemk allowOverlap=1 phys_start=0xc8000000 phys_end=0xc9000000 pools=50x4096,10x131072,5x1440000"

SRC_URI = "file://syslink.ko file://cmemk.ko"

do_compile() {
	bbnote "Empty do_compile task"
}

do_install(){
	install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/dsp/ 
	install -m 0755 ${WORKDIR}/syslink.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/dsp/
    install -m 0755 ${WORKDIR}/cmemk.ko   ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/dsp/
}
