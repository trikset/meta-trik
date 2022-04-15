#!/bin/sh

IMAGE_ON_SD=/usr/share/u-boot-trik/u-boot-gzip.ais
IMAGE_ON_SD_SIZE=$(stat -L -c '%s' $IMAGE_ON_SD)

# Copy expected amount of bytes to RAM
head -c $IMAGE_ON_SD_SIZE /dev/mtd0 > /tmp/u-boot.ais

# Compare them and update if necessary
if cmp -s $IMAGE_ON_SD /tmp/u-boot.ais; then
  echo "U-Boot is OK"
else
  echo "### FLASHING U-BOOT... "
  flashcp -v $IMAGE_ON_SD /dev/mtd0
  flash_erase /dev/mtd1 0 0
  echo DONE
fi

rm -f /tmp/u-boot.ais
