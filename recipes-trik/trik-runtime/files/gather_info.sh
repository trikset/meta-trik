#!/bin/bash
# Script uses awk

set -euo pipefail

export LC_ALL=C 
export LANG=en_US.UTF-8


archive_path="/var/trik/log"
remaining_size_limit_k=100


utils_list=(
	"cat /etc/version"
	"dmesg"
	"i2cdetect -y 1"
	"i2cdetect -y 2"
	"ifconfig -a"
	"lsmod"
	"lsof"
	"ps ax"
	"uname -a"
	)


tree_elements_list=(
	"/home/root/trik/core"
	"/home/root/trik/scripts/"
	"/home/root/trik/trik.log"
	"/var/log/"
)


tree_dir_name="tree"
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


prepare_tmp_dir() {
	local name=$(generate_unique_name)
	local version=$(cat "/etc/version")
	local next_log_number=$(find "${archive_path}" -maxdepth 1 -name "${name}-${version}-*" | wc -l)
 
	tmp_dir_name="${name}-${version}-$(printf "%04d" ${next_log_number})"
	tmp_dir_path="${archive_path}/${tmp_dir_name}"

	mkdir -p ${tmp_dir_path}/{${tree_dir_name},${utils_dir_name}}
}


gather_tree() {
	echo "*** FS TREE"
	for tree_element in "${tree_elements_list[@]}"; do
		echo "$tree_element"
		ln -s "$tree_element" "${tmp_dir_path}/${tree_dir_name}/$(replace_slashes "$tree_element")"
	done
}


redirect_command() {
	$1
} > "${tmp_dir_path}/${utils_dir_name}/$(replace_slashes "$1")"


gather_utils() {
	echo "*** UTILS"
	for util in "${utils_list[@]}"; do
		echo "$util"
		redirect_command "$util" || echo " FAILED"
	done
}


compress() {
	echo "*** COMPRESSION"
	# Tar from BusyBox has a limited set of options, therefore this pipeline is needed
	tar -cvhf - -C "$archive_path" "$tmp_dir_name" | gzip > "${tmp_dir_path}.tar.gz" || true   
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
	gather_tree
	gather_utils
	compress
	clean_up
	echo -e "Info was saved into:\n${tmp_dir_path}.tar.gz"
}

main
