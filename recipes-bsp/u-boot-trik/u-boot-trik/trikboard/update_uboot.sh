#!/bin/sh

IMAGE_ON_SD=$(readlink -f /boot/u-boot-gzip.ais)
IMAGE_ON_SD_SIZE=$(stat -L -c '%s' $IMAGE_ON_SD)

# Copy expected amount of bytes to RAM
head -c $IMAGE_ON_SD_SIZE /dev/mtd0 > /tmp/u-boot.ais

# Compare them and update if necessary
if cmp -s $IMAGE_ON_SD /tmp/u-boot.ais; then
  echo "U-Boot is OK"
else
  echo "### FLASHING U-BOOT... "
  # According to changelog, some devices have 128K
  # internal spi flash. Ignore them.
  if grep -q 'mtd0: 00040000' /proc/mtd; then
    flashcp -v $IMAGE_ON_SD /dev/mtd0
    flash_erase /dev/mtd1 0 0
    echo DONE
  else
    echo U-boot partition is too small, skip update
  fi
fi

rm -f /tmp/u-boot.ais
