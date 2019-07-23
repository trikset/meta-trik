#!/bin/bash

source /etc/default/trik/smth.sh

ACCELEROMETER_PATH=/sys/class/misc/mma845x
GYROSCOPE_PATH=/sys/class/misc/l3g42xxd

# $1: frequency; $2: range; $3: path;
set_parameters() {
	echo $1 > $3/odr_selection
	echo $2 > $3/fs_selection
}

# $1: state; $2: frequency; $3: range; $4: path;
set_up_accelerometer() {
	if [[ "$1" = true ]]; then
		modprobe mma845x
		set_parameters $2 $3 $4
	else
		rmmod mma845x
	fi
}

# $1: state; $2: frequency; $3: range; $4: path;
set_up_gyroscope() {
	if [[ "$1" = true ]]; then
		modprobe l3g42xxd
		modprobe l3g42xxd_spi
		set_parameters $2 $3 $4
	else
		rmmod l3g42xxd_spi
		rmmod l3g42xxd
	fi
}


main() {
	set_up_accelerometer "${accelerometer_state}" "${accelerometer_frequency}" "${accelerometer_range}" "${ACCELEROMETER_PATH}"
	set_up_gyroscope "${gyroscope_state}" "${gyroscope_frequency}" "${gyroscope_range}" "${GYROSCOPE_PATH}"
}

main