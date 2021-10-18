#!/bin/bash
set -ueo pipefail
[ -r /etc/default/trik/mems_options.sh ] && source /etc/default/trik/mems_options.sh || :

set_up_device() {
	echo "$1" > /sys/bus/iio/devices/"$2"/buffer/enable
	echo "$1" > /sys/bus/iio/devices/"$2"/scan_elements/in_timestamp_en
	echo "$1" > /sys/bus/iio/devices/"$2"/scan_elements/in_accel_x_en
	echo "$1" > /sys/bus/iio/devices/"$2"/scan_elements/in_accel_y_en
	echo "$1" > /sys/bus/iio/devices/"$2"/scan_elements/in_accel_z_en
}

main() {
	set_up_device 1 iio:device1
	set_up_device 1 iio:device0
}

main
