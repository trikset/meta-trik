setenv set_bootargs 'setenv bootargs mem=128M@0xC0000000 mem=64M@0xCC000000 console=ttyS2,115200n8 rw noinitrd rootwait root=${rootdev} vt.global_cursor_default=0 consoleblank=0 ${extrabootargs}'
run bootcmd
