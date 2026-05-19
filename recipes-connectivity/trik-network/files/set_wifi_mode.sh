#!/bin/bash

# Copyright 2014 CyberTech Labs Ltd.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

hostapd_conf=/etc/hostapd.conf
trikrc=/etc/trik/trikrc
interface=wlan0

PREVIOUS_MODE=""
[ -f "$trikrc" ] && source "$trikrc" && PREVIOUS_MODE="${trik_wifi_mode:-}"

start_client() {
    local ifstate=/var/run/ifstate
    local wpa_running=false
    local iface_in_ifstate=false
    pgrep wpa_supplicant > /dev/null 2>&1       && wpa_running=true
    grep -q "^${interface}=" "$ifstate" 2>/dev/null && iface_in_ifstate=true

    # Recovery after interrupted ifup/ifdown: the process state and
    # the ifstate entry may get out of sync — fix the mismatch before retrying.
    if $wpa_running && ! $iface_in_ifstate; then
        echo "Fixing: wpa_supplicant running but $interface absent in ifstate" >&2
        killall -q wpa_supplicant
        sleep 0.3
    elif ! $wpa_running && $iface_in_ifstate; then
        echo "Fixing: stale $interface entry in ifstate" >&2
        sed --in-place "/^${interface}=/d" "$ifstate"
    fi

    ifup "$interface"
}

start_ap() {
    source "$trikrc"
    if [ "${#trik_wifi_ap_passphrase}" -ne "8" ]; then
        generate_ap_passphrase
    fi
    generate_hostapd_conf
    /etc/init.d/hostapd start
    ifconfig "$interface" 192.168.77.1 netmask 255.255.255.0
    udhcpd
}

sigterm_handler() {
    # ignore repeated signals
    trap '' SIGTERM SIGINT
    echo "Signal caught, restoring previous mode: '${PREVIOUS_MODE:-client}'" >&2

    killall -q udhcpd
    /etc/init.d/hostapd stop
    ifdown "$interface"

    local attempts=5
    while pgrep hostapd > /dev/null || pgrep wpa_supplicant > /dev/null; do
        attempts=$(( attempts - 1 ))
        echo "attempts '$attempts'"
        if [ "$attempts" -eq 0 ]; then
            echo "Killing remaining processes..." >&2
            killall -q hostapd     2>/dev/null || true
            killall -q wpa_supplicant 2>/dev/null || true
            break
        fi
        sleep 0.2
    done

    case "${PREVIOUS_MODE:-client}" in
        "client") start_client ;;
        "ap")     start_ap ;;
    esac

    sed --in-place '/^trik_wifi_mode=/d' "$trikrc"
    echo "trik_wifi_mode=${PREVIOUS_MODE:-client}" >> "$trikrc"

    exit 2
}

trap 'sigterm_handler' SIGTERM SIGINT

generate_ap_passphrase() {
    sed --in-place '/^trik_wifi_ap_passphrase=/d' "$trikrc"
    trik_wifi_ap_passphrase=""
    for i in 1 2 3 4 5 6 7 8; do
        digit=$(( RANDOM % 10 ))
        trik_wifi_ap_passphrase="${trik_wifi_ap_passphrase}${digit}"
    done
    echo "trik_wifi_ap_passphrase=$trik_wifi_ap_passphrase" >> "$trikrc"
}

generate_hostapd_conf() {
    echo "interface=$interface
driver=nl80211
ssid=$(cat /etc/hostname)
hw_mode=g
channel=1
macaddr_acl=0
auth_algs=1
ignore_broadcast_ssid=0
wpa=2
wpa_passphrase=$trik_wifi_ap_passphrase
wpa_key_mgmt=WPA-PSK
wpa_pairwise=TKIP
rsn_pairwise=CCMP" > "$hostapd_conf"
}

if [ ! "$1" = "client" ] && [ ! "$1" = "ap" ]; then
    echo "Usage: set_wifi_mode.sh client|ap"
    exit 1
fi

killall -q udhcpd
/etc/init.d/hostapd stop
ifdown "$interface"

attempts=5
while pgrep wpa_supplicant > /dev/null || pgrep hostapd > /dev/null; do
    attempts=$(( attempts - 1 ))
    echo "attempts '$attempts'"
    if [ "$attempts" -eq 0 ]; then
        echo "Killing remaining processes..." >&2
        killall -q hostapd     2>/dev/null || true
        killall -q wpa_supplicant 2>/dev/null || true
        break
    fi
    sleep 0.2
done

if [ ! -f "$trikrc" ]; then
    touch "$trikrc"
fi
sed --in-place '/^trik_wifi_mode=/d' "$trikrc"

case "$1" in
    "client") start_client ;;
    "ap")     start_ap ;;
esac

echo "trik_wifi_mode=$1" >> "$trikrc"
