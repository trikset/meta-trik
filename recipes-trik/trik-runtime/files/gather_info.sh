#!/bin/bash
# Script uses awk

set -euo pipefail

export LC_ALL=C 
export LANG=en_US.UTF-8


archive_path="/var/trik/log"
remaining_size_limit_k=100


tree_elements_list=(
	"/home/root/trik/scripts/"
	"/home/root/trik/trik.log"
	"/var/log/"
	)
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
	mkdir -p ${archive_path}/
	# Remove empty dir that was created in last session
	rmdir ${archive_path}/* || true

	local name=$(generate_unique_name)
	local version=$(cat "/etc/version")
	local next_log_number=$(find "${archive_path}" -maxdepth 1 -name "${name}-${version}-*" | wc -l)
 
	local tmp_dir_name="${name}-${version}-$(printf "%04d" ${next_log_number})"
	local tmp_dir_path="${archive_path}/${tmp_dir_name}"

	mkdir -p "$tmp_dir_path"
	echo "${tmp_dir_path}"	
}


gather_tree() {
	echo "*** FS TREE"
	for tree_element in "${tree_elements_list[@]}"; do
		echo "$tree_element"
		cp -rvL "$tree_element" "${1}/${tree_dir_name}/$(replace_slashes "$tree_element")" || true
	done
}


redirect_command() {
	$1
} > "$2/${utils_dir_name}/$(replace_slashes "$1")"


gather_utils() {
	echo "*** UTILS"
	for util in "${utils_list[@]}"; do
		echo "$util"
		redirect_command "$util" "$1" || echo " FAILED"
	done
}


clean_up() {	
	rm -f "/home/root/trik/trik.log"
}


compress() {
	find ${archive_path} -mindepth 1 -maxdepth 1 -type d | xargs -n 1 -I {} sh -c 'tar czvf {}.tar.gz {} -C ${archive_path} . && rm -r {};'
}


main() {
	if [ "$1" = "--create" ]; then 
		prepare_tmp_dir
	elif [ "$1" = "--collect" ]; then
			mkdir -p $2/{${tree_dir_name},${utils_dir_name}}
			gather_tree "$2"
			gather_utils "$2"
			clean_up
			sync
	elif [ "$1" = "--gc" ]; then
		compress
		sync
	else 
		echo "No such command"
	fi
}


command=${1:-}
option=${2:-}
echo "$option"
main "$command" "$option"
