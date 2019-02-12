#reuse patches from upstream
FILESEXTRAPATHS_append := ":${THISDIR}/files"
SRC_URI+="file://0002-include-sys-sysmacros.h-as-needed.patch"
