#!/bin/sh -e

IMAGE_ON_SD=/boot/u-boot.ais
IMAGE_ON_SD_SIZE=`stat -L -c '%s' $IMAGE_ON_SD`

# Copy expected amount of bytes to RAM
head -c $IMAGE_ON_SD_SIZE /dev/mtd0 > /tmp/u-boot.ais

# Compare them
if cmp -s $IMAGE_ON_SD /tmp/u-boot.ais; then
  echo "U-Boot is OK"
  rm -f /tmp/u-boot.ais
  exit 0
fi

rm -f /tmp/u-boot.ais

echo "### FLASHING U-BOOT... "
flashcp -v $IMAGE_ON_SD /dev/mtd0
flash_erase /dev/mtd1 0 0
echo DONE
