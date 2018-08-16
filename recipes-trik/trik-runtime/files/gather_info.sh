#!/bin/bash
# Script uses awk

set -euo pipefail

export LC_ALL=C 
export LANG=en_US.UTF-8


archive_path="/var/log/trik"
remaining_size_limit_k=100


trikboard_workdir="/home/root/trik"
trikboard_core_file="${trikboard_workdir}/core"
trikboard_log_file="${trikboard_workdir}/trik.log"
trikboard_scripts_dir="${trikboard_workdir}/scripts/"


utils_list=(
	"cat /etc/version"
	"dmesg"
	"ifconfig"
	"lsmod"
	"i2cdetect -y 1"
	"i2cdetect -y 2"
	"uname -a"
	)


dirs_dir_name="dirs"
files_dir_name="files"
utils_dir_name="utils"


replace_slashes() {
	echo "${1////_}"
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
	echo "DIRS"
	cp -rv -t "${tmp_dir_path}/${dirs_dir_name}/" "$trikboard_scripts_dir" 
}


gather_files() {
	echo "*** FILES"
	cp -v -t "${tmp_dir_path}/${files_dir_name}" "$trikboard_core_file" "$trikboard_log_file" || true
}


redirect_command() {
	$1
} > "${tmp_dir_path}/${utils_dir_name}/$(replace_slashes "$1")"


gather_utils() {
	echo "*** UTILS"
	for util in "${utils_list[@]}"; do
		redirect_command "$util"
		echo "$util"
	done
}


compress() {
	echo "*** COMPRESSION"
	# Tar from BusyBox has a limited set of options, therefore this pipeline is needed
	tar -cvf - -C "$archive_path" "$tmp_dir_name" | gzip > "${tmp_dir_path}.tar.gz"   
	rm -r ${tmp_dir_path}
}


clean_up() {
	rm -f "$trikboard_core_file"	
	rm -f "$trikboard_log_file"
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
	echo "Info was saved in ${tmp_dir_path}.tar.gz"
}

main
