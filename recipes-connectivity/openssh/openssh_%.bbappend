# By default, 3 keys will be generated, this +RSA. 
# RSA has been generated for a very long time and is outdated anyway, 
# we explicitly specify which keys we need.
do_configure:prepend () {
	sed -i '/^[#[:space:]]*HostKey.*_ecdsa_key\|^[#[:space:]]*HostKey.*_ed25519_key/s/^[#[:space:]]*//' ${WORKDIR}/sshd_config
}
