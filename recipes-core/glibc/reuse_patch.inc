#reuse patches from upstream
FILESEXTRAPATHS_append := ":${THISDIR}/files"
SRC_URI+="file://do_not_call_io_fwide.patch"
