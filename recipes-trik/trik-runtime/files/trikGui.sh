#!/bin/sh
. /etc/profile.d/locale.sh
. /etc/profile.d/qws_display.sh
. /etc/profile.d/trik_runtime_path.sh
. /etc/profile.d/python_path.sh
which xset && ( xset s off ; xset s noblank ; xset -dpms ) || echo "Missing xset!"
cd /home/root/trik && exec nice -n -5 ./trikGui 2>&1 1> /dev/null
