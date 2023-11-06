#disable rng-tools, force haveged
PACKAGECONFIG=""
RRECOMMENDS:${PN}-sshd:append:class-target += " haveged"

# should be in BSP somewhere in the machine.conf
PACKAGE_EXCLUDE:append = " rng-tools"
