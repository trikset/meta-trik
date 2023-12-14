
IMAGE_ROOTFS_ALIGNMENT ?= "4"

XZ_DICTIONARY_SIZE = "128"
XZ_THREADS = "2"
XZ_COMPRESSION_LEVEL ?= "--verbose --no-adjust --memlimit-compress=6GiB --arm --lzma2=mode=normal,dict=${XZ_DICTIONARY_SIZE}MiB,lc=1,lp=2,pb=2,mf=bt4,nice=192,depth=1024"
EXTRA_IMAGECMD:ext4 =+ " -b 4096 -i 4096 "

inherit image_types logging user-partion
# inherit image-mklibs

DEPENDS += "u-boot-trik"
IMAGE_TYPES += "ext4 ext4.xz img img.xz"
IMAGE_TYPEDEP:img = "ext4"
IMAGE_TYPEDEP:img.xz = "img"

du_image_img[depends] += 	"\
			parted-native:do_populate_sysroot \
			e2fsprogs-native:do_populate_sysroot  \
			dosfstools-native:do_populate_sysroot  \
			mtools-native:do_populate_sysroot  \
			"

IMAGE_FSTYPES = "img.xz img"

#IMAGE_TYPES_MASKED = "img"

EXCLUDE_FROM_WORLD = "1"

#inherit image-prelink

IMGDEPLOYDIR ??= "${DEPLOY_DIR_IMAGE}"
TRIKIMG_USER_PARTION = "${IMGDEPLOYDIR}/${IMAGE_NAME}.user-part.vfat"
TRIKIMG_USER_PARTION_LABEL ?= "TRIKUSER"
TRIKIMG_USER_PARTION_SIZE ?= "1024"

TRIKIMG_ROOTFS =  "${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.ext4"
TRIKIMG_FILE ?= "${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.img"

IMAGE_CMD:img () {
 do_bootable_sdimg
}

# temporary add timestamp into conffs
IMAGEDATESTAMP = "${@time.strftime('%Y.%m.%d',time.gmtime())}"

#do_user_partition_img() {
#	rm -rf ${TRIKIMG_USER_PARTION}
#	truncate "-s >${TRIKIMG_USER_PARTION_SIZE}K" ${TRIKIMG_USER_PARTION}
#	echo "${IMAGE_NAME}-${IMAGEDATESTAMP}" > ${WORKDIR}/${TRIK_USER_PARTION_CREATION_DIR}/image-version-info
#	mkdosfs -F 32 -n ${TRIKIMG_USER_PARTION_LABEL} -d ${WORKDIR}/${TRIK_USER_PARTION_CREATION_DIR} ${TRIKIMG_USER_PARTION}
#}

MBR_SIZE ?= "4K"
BLOCK_SIZE ?= "4096"
ALIGNMENT ?= "4K"


file_size() {
 du --summarize  --dereference-args  --apparent-size --block-size=${BLOCK_SIZE} "$1" | cut -f 1
}

align() {
 truncate -s %${ALIGNMENT} "$1"
}

reserve() {
truncate -o ${BLOCK_SIZE} -s "+$1" ${TRIKIMG_FILE}
align ${TRIKIMG_FILE}
file_size ${TRIKIMG_FILE}
}

reserve_for() {
   local size
   size=$(file_size "$1")
   reserve $size
}

insert_at() {
bbnote "Inserting $2 at $1"
dd "if=$2" "of=${TRIKIMG_FILE}" conv=notrunc bs=${BLOCK_SIZE} "seek=$1" status=none
}

do_bootable_sdimg(){
	local UBOOT_AIS="${DEPLOY_DIR_IMAGE}/u-boot.ais"
        local IMAGE="${TRIKIMG_FILE}"
	rm -f ${IMAGE}
	truncate -s ${MBR_SIZE} ${IMAGE}
	AIS_OFFSET=$(file_size ${IMAGE})
	ROOTFS_OFFSET=$(reserve_for ${UBOOT_AIS})
	insert_at ${AIS_OFFSET} ${UBOOT_AIS}
	insert_at ${ROOTFS_OFFSET} ${TRIKIMG_ROOTFS}
	sfdisk  ${IMAGE} << EOD
unit: sectors
label: dos
$((${ROOTFS_OFFSET} * ${BLOCK_SIZE} / 512)),,83
EOD

}

do_bootable_sdimg[depends] += "util-linux-native:do_populate_sysroot \
                               coreutils-native:do_populate_sysroot \
                               u-boot-trik:do_deploy \
                               ${PN}:do_image_ext4"