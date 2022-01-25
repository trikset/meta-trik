#!/bin/bash

echo 30 > /sys/class/gpio/export
echo low > /sys/class/gpio/gpio30/direction
echo 0 > /sys/class/gpio/gpio30/value
