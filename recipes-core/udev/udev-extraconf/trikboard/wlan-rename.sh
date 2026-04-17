#!/bin/sh
IFACE=$1

#Check for "usb" in path instead of %E{ID_BUS} — ID_BUS is empty for devices initially recognized as disk
DEVPATH=$(udevadm info /sys/class/net/$IFACE 2>/dev/null | grep "P:" | awk '{print $2}')

if echo "$DEVPATH" | grep -q "/usb" && [ "$IFACE" != "wlan0" ]; then
    if ip link show wlan0 &>/dev/null; then
        ip link set wlan0 down
        ip link set wlan0 name wlan_builtin
    fi
    ip link set "$IFACE" down
    ip link set "$IFACE" name wlan0
fi
