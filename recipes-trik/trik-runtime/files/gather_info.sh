#!/bin/bash
# Script uses awk

set -euo pipefail

export LC_ALL=C 
export LANG=en_US.UTF-8


archive_path="/home/root/trik/log_info"
remaining_size_limit_k=100


dirs_list=(
	"scripts"
	)

files_list=( 
	"/home/root/trik/core"
	"/home/root/trik/trik.log"
	)

utils_list=(
	"cat /etc/version"
	"dmesg"
	"ifconfig"
	"lsmod"
	"sudo i2cdetect -y 1"
	"sudo i2cdetect -y 2"
	"uname -a"
	)


dirs_dir_name="dirs"
files_dir_name="files"
utils_dir_name="utils"


replace_slashes() {
	echo "${1////\\}"
}


estimate_remaining_size() {
	echo "$(df . | awk '$4 ~ /[0-9]+/ { print $4 }')"
}


generate_unique_name() {
	echo "trik$(cat /sys/class/net/wlan0/address | tail -c 9 | sed 's/://g')"
}


get_creation_time() {
	echo "$(date +%Y%m%d_%H%M%S)"
}


prepare_tmp_dir() {
	tmp_dir_name="$(generate_unique_name)-$(get_creation_time)"
	tmp_dir_path="${archive_path}/${tmp_dir_name}"

	mkdir -p ${tmp_dir_path}/{${dirs_dir_name},${files_dir_name},${utils_dir_name}}
}


gather_dirs() {
	echo "*** DIRS"
	for dir in "${dirs_list[@]}"; do
		cp -rv "$dir" "${tmp_dir_path}/${dirs_dir_name}" || true
	done
}


gather_files() {
	echo "*** FILES"
	for file in "${files_list[@]}"; do
		cp -v "$file" "${tmp_dir_path}/${files_dir_name}" || true
	done
}


redirect_command() {
	$1
} > "${tmp_dir_path}/${utils_dir_name}/$(replace_slashes "$1")"


gather_utils() {
	echo "*** UTILS"
	for util in "${utils_list[@]}"; do
		redirect_command "$util"
		echo "$(replace_slashes "$util")"
	done
}


compress() {
	echo "*** COMPRESSION"
	tar -cvf "${tmp_dir_path}.tar" -C "$archive_path" "$tmp_dir_name" 
	rm -r ${tmp_dir_path}
}


clean_up() {
	rm -f "/home/root/trik/core"
	rm -f "/home/root/trik/trik.log"
}


main() {
	tmp_dir_name=
	tmp_dir_path=
	
	prepare_tmp_dir
	gather_dirs
	gather_files
	gather_utils
	compress
	clean_up
	echo "Info was saved in ${tmp_dir_path}.tar"
}

main
