#!/bin/sh -e
FB=/sys/class/graphics/fb0

if [ -e $FB/id ]
then
    ID=`cat $FB/id` 
    case $ID in
    00858552 ) echo 1 > $FB/idle ;;
    00000000 ) ;;
    *)         echo "UNKNOWN DISPLAY TYPE $ID"
    esac

fi
