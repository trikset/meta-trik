SUMMARY = "nanomsg is a socket library that provides several common \
communication patterns."
DESCRIPTION = "The nanomsg library is a simple high-performance \
implementation of several scalability protocols. These scalability \
protocols are light-weight messaging protocols which can be used to \
solve a number of very common messaging patterns, such as request/reply, \
publish/subscribe, surveyor/respondent, and so forth. These protocols \
can run over a variety of transports such as TCP, UNIX sockets, and even \
WebSocket."
HOMEPAGE = "http://nanomsg.org/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=587b3fd7fd291e418ff4d2b8f3904755"

SRC_URI = "https://github.com/nanomsg/nanomsg/archive/1.1.4.zip"
SRC_URI[md5sum] = "ff4d79693841e553ccc27d258be538ea"
SRC_URI[sha256sum] = "dcba7622b375ebd87d0d068d89f0907d91982aaf7151cb2890c3ac770a1c6d63"

FILES:${PN}-dev += "${nonarch_libdir}/cmake/${PN}"

inherit cmake pkgconfig
