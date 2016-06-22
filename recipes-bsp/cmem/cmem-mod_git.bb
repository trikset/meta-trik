DESCRIPTION = "Kernel module for contiguous memory allocation from userspace"

include cmem.inc
# This package builds a kernel module, use kernel PR as base and append a local
MACHINE_KERNEL_PR_append = "b"
PR = "${MACHINE_KERNEL_PR}"

inherit module
TOOLCHAIN_PREFIX="arm-oe-linux-gnueabi-"
EXTRA_OEMAKE += '-f lu.mak KERNEL_INSTALL_DIR="${STAGING_KERNEL_DIR}" TOOLCHAIN_PREFIX="${TOOLCHAIN_PREFIX}" EXEC_DIR="${D}/lib/modules/${KERNEL_VERSION}/extra"'
MAKE_TARGETS = "module"

KERNEL_MODULE_AUTOLOAD += "cmemk"
KERNEL_MODULE_PROBECONF +="cmemk"
module_conf_cmemk="options cmemk allowOverlap=1 phys_start=0xc8000000 phys_end=0xc9000000 pools=50x4096,10x131072,5x1440000"
