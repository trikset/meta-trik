#!/bin/sh -e
FB=/sys/class/graphics/fb0
case $1 in
0 | 1 )
    if [ -e $FB/id ]
    then
        ID=`cat $FB/id`
        case $ID in
           00858552 ) echo $1 > $FB/idle ;;
           00000000 ) ;;
           *)         echo "UNKNOWN DISPLAY TYPE $ID"
        esac
    else
        echo "$0: missing $FB/idle"

    fi
;;
*) echo "$0: missing IDLE parameter";;
esac
