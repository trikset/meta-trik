#!/bin/sh
. /etc/profile.d/locale.sh
. /etc/profile.d/qws_display.sh
which xsetroot >/dev/null &&  xsetroot -solid blue || echo "Missing xsetroot!"
which xset >/dev/null && ( xset s off ; xset s noblank ; xset -dpms ) || echo "Missing xset!"
. /etc/profile.d/trik_runtime_path.sh
. /etc/profile.d/python_path.sh
/etc/trik/display_settings.sh 1
core_dump=$(/etc/trik/log_manager.sh --create)
cd /home/root/trik && nice -n -5 ./trikGui -c "configs/kernel-$(uname -r | cut -f -2 -d .)" -d "${core_dump}" 1>/var/log/trikGui.log 2>&1 || /etc/trik/log_manager.sh --collect ${core_dump}
/etc/trik/display_settings.sh 0
which xsetroot >/dev/null &&  xsetroot -solid red || echo "Missing xsetroot!"
