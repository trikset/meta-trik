#disable rng-tools, force haveged
PACKAGECONFIG=""
RRECOMMENDS_${PN}-sshd_append_class-target += " haveged"

# should be in BSP somewhere in the machine.conf
PACKAGE_EXCLUDE_append = " rng-tools"
