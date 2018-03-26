#!/bin/sh
. /etc/profile.d/locale.sh
. /etc/profile.d/qws_display.sh
which xsetroot >/dev/null &&  xsetroot -solid blue || echo "Missing xsetroot!"
which xset >/dev/null && ( xset s off ; xset s noblank ; xset -dpms ) || echo "Missing xset!"
. /etc/profile.d/trik_runtime_path.sh
. /etc/profile.d/python_path.sh
/etc/trik/display_settings.sh 1
cd /home/root/trik && exec nice -n -5 ./trikGui 2>&1 1> /dev/null
/etc/trik/display_settings.sh 0
which xsetroot >/dev/null &&  xsetroot -solid red || echo "Missing xsetroot!"
