#!/bin/sh -e
/usr/sbin/flashcp -v /usr/share/u-boot-trik/u-boot-gzip.ais /dev/mtd0
/usr/sbin/flash_erase /dev/mtd1 0 0

