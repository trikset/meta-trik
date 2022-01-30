#!/bin/bash

[ -r /etc/trik/voltage_options.sh ] && source /etc/trik/voltage_options.sh || :

enable_3v() {
        echo low > /sys/class/gpio/gpio30/direction
        echo 0 > /sys/class/gpio/gpio30/value
}

enable_5v() {
        echo high > /sys/class/gpio/gpio30/direction
        echo 1 > /sys/class/gpio/gpio30/value
}


main() {
        echo 30 > /sys/class/gpio/export

        if [[ "$ENABLE_5V" == 1]]; then
                enable_5v
        else
                enable_3v
        fi
}

main
