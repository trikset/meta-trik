#!/bin/bash
set -ueo pipefail
[ -r /etc/default/trik/mems_options.sh ] && source /etc/default/trik/mems_options.sh || :

set_up_accel() {
	echo 1 > /sys/bus/iio/devices/iio\:device0/buffer/enable
	echo 1 > /sys/bus/iio/devices/iio\:device0/scan_elements/in_timestamp_en
	echo 1 > /sys/bus/iio/devices/iio\:device0/scan_elements/in_accel_x_en
	echo 1 > /sys/bus/iio/devices/iio\:device0/scan_elements/in_accel_y_en
	echo 1 > /sys/bus/iio/devices/iio\:device0/scan_elements/in_accel_z_en
}

set_up_gyro() {
	echo 1 > /sys/bus/iio/devices/iio\:device1/buffer/enable
	echo 1 > /sys/bus/iio/devices/iio\:device1/scan_elements/in_timestamp_en
	echo 1 > /sys/bus/iio/devices/iio\:device1/scan_elements/in_anglvel_x_en
	echo 1 > /sys/bus/iio/devices/iio\:device1/scan_elements/in_anglvel_y_en
	echo 1 > /sys/bus/iio/devices/iio\:device1/scan_elements/in_anglvel_z_en
}

main() {
	set_up_accel
	set_up_gyro
}

main
