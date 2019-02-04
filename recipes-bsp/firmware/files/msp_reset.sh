#!/bin/sh -e
echo Resetting MSP ...
# Put MSP to reset
echo 0 > /sys/class/gpio/gpio93/value
usleep 10000
# Drive PUR pin high. Since PUR is wired to USB DP,
# it becomes high too, indicating a newly inserted USB device.
# Linux notices this change on USB and tries to negotiate with
# MSP. Unfortunately, MSP is still in reset state.
# Thus, immediately remove reset to make Linux happy.
echo 1 > /sys/class/gpio/gpio80/value
echo 1 > /sys/class/gpio/gpio93/value
sleep 1
# PUR is now output and does not need any input signals.
echo 0 > /sys/class/gpio/gpio80/value
echo Done
if lsusb | grep -q '2047:0200'
then
    exit 0
else
    echo "MSP reset failed!"
    exit 1
fi
