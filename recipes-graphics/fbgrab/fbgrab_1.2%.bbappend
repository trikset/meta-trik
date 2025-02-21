FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

PR="r0.1"
SRC_URI  += "file://0001-bugfix-grab-pictures-with-green-color.patch \
	     file://0002-swap-blue-and-red-component.patch \
             file://0003-compression-bugfix.patch"
