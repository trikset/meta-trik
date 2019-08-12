#!/bin/sh
iw dev wlp3s0 link | grep "signal" | cut -d " " -f2
