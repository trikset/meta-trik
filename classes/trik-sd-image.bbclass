
IMAGE_ROOTFS_ALIGNMENT ?= "4096"

XZ_DICTIONARY_SIZE = "128"

XZ_COMPRESSION_LEVEL ?= " --arm --lzma2=mode=normal,dict=${XZ_DICTIONARY_SIZE}M,lc=1,lp=2,pb=2,mf=bt4,nice=192,depth=1024 "
EXTRA_IMAGECMD_ext4 =+ " -E stride=2 -E stripe-width=16 -b 4096 -i 4096 "

inherit image_types logging user-partion

IMAGE_TYPES += "ext4 ext4.xz img img.xz"
IMAGE_TYPEDEP_img = "ext4"
IMAGE_TYPEDEP_img.xz = "img"

IMAGE_DEPENDS_img = 	"\
			parted-native \
			e2fsprogs-native \
			dosfstools-native \
			mtools-native \
			"

IMAGE_FSTYPES = "img.xz img"

#IMAGE_TYPES_MASKED = "img"

EXCLUDE_FROM_WORLD = "1"

inherit image-prelink

TRIKIMG_USER_PARTION = "${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.user-part.vfat"
TRIKIMG_USER_PARTION_LABEL ?= "user-part"
TRIKIMG_USER_PARTION_SIZE ?= "512000" 

TRIKIMG_ROOTFS =  "${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ext4"
TRIKIMG_FILE ?= "${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.img"

IMAGE_CMD_img () {
	create_user_partion
        create_trik_sd_image

}
# temporary add timestamp into conffs 
IMAGEDATESTAMP = "${@time.strftime('%Y.%m.%d',time.gmtime())}"

create_user_partion () {
	echo "We are create_trik_conf_image"
	rm -rf ${TRIKIMG_USER_PARTION}
	truncate "-s >${TRIKIMG_USER_PARTION_SIZE}K" ${TRIKIMG_USER_PARTION}
	mkdosfs -F 32 -n ${TRIKIMG_USER_PARTION_LABEL} ${TRIKIMG_USER_PARTION}

	echo "${IMAGE_NAME}-${IMAGEDATESTAMP}" > ${WORKDIR}/image-version-info

	mcopy -i ${TRIKIMG_USER_PARTION} -v ${WORKDIR}/image-version-info ::/
	mcopy -i ${TRIKIMG_USER_PARTION} -s ${WORKDIR}/${TRIK_USER_PARTION_CREATION_DIR}/* ::/
}

create_trik_sd_image (){
	ROOTFS_SIZE=`du --dereference --apparent-size --block-size=1K --summarize ${TRIKIMG_ROOTFS} | cut -f 1`
	# TODO : check size of images 
	TRIKIMG_USER_PARTION_ALIGMENT=$(expr ${TRIKIMG_USER_PARTION_SIZE} + ${IMAGE_ROOTFS_ALIGNMENT} - 1 )
	TRIKIMG_USER_PARTION_ALIGMENT=$(expr ${TRIKIMG_USER_PARTION_ALIGMENT} - ${IMAGE_ROOTFS_ALIGNMENT} % ${IMAGE_ROOTFS_ALIGNMENT})
	TRIKIMG_SIZE=$(expr ${IMAGE_ROOTFS_ALIGNMENT} + ${TRIKIMG_USER_PARTION_ALIGMENT} + ${ROOTFS_SIZE})

	truncate "-s >${TRIKIMG_SIZE}K" ${TRIKIMG_FILE}  
	parted -s ${TRIKIMG_FILE} -- \
           unit KiB \
           mklabel msdos \
           mkpart primary ext4 $(expr ${TRIKIMG_USER_PARTION_ALIGMENT} \+ ${IMAGE_ROOTFS_ALIGNMENT}) -1s \
           mkpart primary fat32 ${IMAGE_ROOTFS_ALIGNMENT} $(expr ${TRIKIMG_USER_PARTION_ALIGMENT} \+ ${IMAGE_ROOTFS_ALIGNMENT})  \
           print

    dd if=${TRIKIMG_USER_PARTION} \
		of=${TRIKIMG_FILE} conv=fsync bs=$(expr ${IMAGE_ROOTFS_ALIGNMENT} \* 1024) seek=1
    dd if=${TRIKIMG_ROOTFS} \
	of=${TRIKIMG_FILE} conv=fsync bs=$(expr 1024 \* ${TRIKIMG_USER_PARTION_ALIGMENT} + ${IMAGE_ROOTFS_ALIGNMENT} \* 1024) seek=1

}


python do_sdimg() {
		bb.build.exec_func('create_trik_sd_image', d)
}

do_sdimg[depends] += "parted-native:do_populate_sysroot ${PN}:do_rootfs"

#addtask sdimg after do_rootfs
do_user_rootfs[depens] = "${PN}:do_rootfs"

ROOTFS_POSTPROCESS_COMMAND_append = "do_user_rootfs"

TRIK_USER_PARTION_CREATION_DIR ?="/EXT_FAT"

do_user_rootfs () {
	rm -rf ${WORKDIR}/${TRIK_USER_PARTION_CREATION_DIR}
	mkdir -p ${WORKDIR}/${TRIK_USER_PARTION_CREATION_DIR}/
	mv ${IMAGE_ROOTFS}/${TRIK_USER_PARTION_CREATION_DIR}/* ${WORKDIR}/${TRIK_USER_PARTION_CREATION_DIR}/
}
