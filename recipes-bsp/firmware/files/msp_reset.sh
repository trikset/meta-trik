#!/bin/sh -e
echo Resetting MSP ...
echo 0 > /sys/class/gpio/gpio93/value
usleep 10000
echo 1 > /sys/class/gpio/gpio80/value
usleep 10000
echo 1 > /sys/class/gpio/gpio93/value
usleep 10000
echo 0 > /sys/class/gpio/gpio80/value
sleep 3
echo Done
if lsusb | grep -q '2047:0200'
then
    exit 0
else
    echo "MSP reset failed!"
    exit 1
fi
