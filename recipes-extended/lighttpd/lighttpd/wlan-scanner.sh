#! /bin/sh

# iwlist is not available in the image (only iw is installed), so use iw.
iw dev wlan0 scan 2>/dev/null | awk '
function esc(s) {
	gsub(/&/, "\\&amp;", s)
	gsub(/</, "\\&lt;", s)
	gsub(/>/, "\\&gt;", s)
	gsub(/"/, "\\&quot;", s)
	return s
}
function emit() {
	if (ssid == "")
		return
	if (!encrypted && privacy)
		return
	if (encrypted) {
		printf "<li><a class=\"encrypted\" href=\"javascript:void(0);\">%s", esc(ssid)
		printf "<img class=\"lock\" width=\"20px\" src=\"/images/lock.png\"/>"
		printf "</a></li>\n"
	} else {
		printf "<li><a href=\"javascript:void(0);\">%s</a></li>\n", esc(ssid)
	}
}
/^BSS / {
	emit()
	ssid = ""
	encrypted = 0
	privacy = 0
	next
}
/^[[:space:]]*SSID:/ {
	sub(/^[[:space:]]*SSID:[[:space:]]*/, "")
	ssid = $0
	next
}
/^[[:space:]]*RSN:/ || /^[[:space:]]*WPA:/ {
	encrypted = 1
	next
}
/^[[:space:]]*capability:/ && /Privacy/ {
	privacy = 1
	next
}
END {
	emit()
}
'
