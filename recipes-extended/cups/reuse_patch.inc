#reuse patches from upstream
FILESEXTRAPATHS_append := ":${THISDIR}/files"
SRC_URI+="file://cups-2.2.4-pam.patch"
