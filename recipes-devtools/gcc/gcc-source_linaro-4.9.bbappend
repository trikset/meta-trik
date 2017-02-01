do_fix_paths() {
        # Linaro's gcc-4.9 recipe has incorrect line :
        #          sed -i -e 's/hardcode_into_libs=yes/hardcode_into_libs=no/' ${S}/libcc1/configure
        # thus we create this file to prevent build failure until original recipe is fixed
     mkdir -p ${S}/libcc1
     touch ${S}/libcc1/configure
}
addtask do_fix_paths after do_patch before do_preconfigure
