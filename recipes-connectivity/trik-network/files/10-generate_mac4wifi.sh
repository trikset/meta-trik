#!/bin/bash
set -ueo pipefail
pushd /lib/firmware/ti-connectivity/

nvsAddr=$(calibrator get nvs_mac wl1271-nvs.bin  | grep -o '\([0-9a-f][0-9a-f]:\)\{5\}[0-9a-f][0-9a-f]')
ifconfigAddr=$(ifconfig wlan0  | grep -o '\([0-9A-F][0-9A-F]:\)\{5\}[0-9A-F][0-9A-F]')
echo "nvs wifi addr is $nvsAddr, ifconfig addr is $ifconfigAddr"

if [[ "x$nvsAddr" == "x00:00:00:00:00:00"  &&  "x$ifconfigAddr" == "x00:00:00:00:00:01" ]]; then
  newAddr=$(hexdump -n 5 -e '"02:" 4/1 "%02x:" "%02x"' /dev/urandom)
  echo "Setting new WiFi MAC addr with 'calibrator set nvs_mac wl1271-nvs.bin $newAddr'"
  calibrator set nvs_mac wl1271-nvs.bin "$newAddr"
  rmmod wl12xx || :
  modprobe wl12xx
fi
popd
