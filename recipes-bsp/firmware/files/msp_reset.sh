#!/bin/sh
echo Resetting MSP ...
echo 0 > /sys/class/gpio/gpio93/value
usleep 10000
echo 1 > /sys/class/gpio/gpio80/value
usleep 10000
echo 1 > /sys/class/gpio/gpio93/value
usleep 10000
echo 0 > /sys/class/gpio/gpio80/value
echo Done
lsusb
