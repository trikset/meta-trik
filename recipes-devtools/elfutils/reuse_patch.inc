#reuse patches from upstream
FILESEXTRAPATHS_append := ":${THISDIR}/files"
SRC_URI+="file://packed_not_aligned.patch"
