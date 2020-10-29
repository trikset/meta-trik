#!/bin/bash
#set -ueo pipefail
[ -r /etc/default/trik/mems_options.sh ] && source /etc/default/trik/mems_options.sh || :

ACCELEROMETER_PATH=/sys/class/misc/mma845x
GYROSCOPE_PATH=/sys/class/misc/l3g42xxd

# $1: frequency; $2: range; $3: path;
set_params() {
	echo "$1" > "$3/odr_selection" || echo "Error: see above"
	echo "$2" > "$3/fs_selection"  || echo "Error: see above"
}

# $1: state; $2: frequency; $3: range; $4: path;
set_up_accel() {
	if [[ "$1" = true ]]; then
		modprobe mma845x
		set_params "$2" "$3" "$4"
	else
		rmmod mma845x
	fi
}

# $1: state; $2: frequency; $3: range; $4: path;
set_up_gyro() {
	if [[ "$1" = true ]]; then
		modprobe l3g42xxd
		modprobe l3g42xxd_spi
		set_params "$2" "$3" "$4"
	else
		rmmod l3g42xxd_spi
		rmmod l3g42xxd
	fi
}


main() {
	set_up_accel "${accel_state}" "${accel_freq}" "${accel_range}" "${ACCELEROMETER_PATH}"
	set_up_gyro "${gyro_state}" "${gyro_freq}" "${gyro_range}" "${GYROSCOPE_PATH}"
}

main
