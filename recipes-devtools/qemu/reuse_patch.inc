#reuse patches from upstream
FILESEXTRAPATHS_append := ":${THISDIR}/files"
SRC_URI+="file://glibc_2.25.patch"
