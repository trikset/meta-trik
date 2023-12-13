
IMAGE_ROOTFS_ALIGNMENT ?= "4"

XZ_DICTIONARY_SIZE = "128"
XZ_THREADS = "2"
XZ_COMPRESSION_LEVEL ?= "--verbose --no-adjust --memlimit-compress=6GiB --arm --lzma2=mode=normal,dict=${XZ_DICTIONARY_SIZE}MiB,lc=1,lp=2,pb=2,mf=bt4,nice=192,depth=1024"
EXTRA_IMAGECMD:ext4 =+ " -E stride=2 -E stripe-width=16 -b 4096 -i 4096 "

inherit image_types logging user-partion
#TODO image-mklibs

DEPENDS += "u-boot-trik"
IMAGE_TYPES += "ext4"

IMAGE_FSTYPES = "ext4"

#IMAGE_TYPES_MASKED = "img"

EXCLUDE_FROM_WORLD = "1"

#TODO inherit image-prelink

IMGDEPLOYDIR ??= "${DEPLOY_DIR_IMAGE}"
TRIKIMG_USER_PARTION = "${IMGDEPLOYDIR}/${IMAGE_NAME}.user-part.vfat"
TRIKIMG_USER_PARTION_LABEL ?= "TRIKUSER"
TRIKIMG_USER_PARTION_SIZE ?= "1024"

TRIKIMG_ROOTFS =  "${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.ext4"
TRIKIMG_FILE ?= "${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.img"

# temporary add timestamp into conffs
IMAGEDATESTAMP = "${@time.strftime('%Y.%m.%d',time.gmtime())}"

#do_user_partition_img() {
#	rm -rf ${TRIKIMG_USER_PARTION}
#	truncate "-s >${TRIKIMG_USER_PARTION_SIZE}K" ${TRIKIMG_USER_PARTION}
#	echo "${IMAGE_NAME}-${IMAGEDATESTAMP}" > ${WORKDIR}/${TRIK_USER_PARTION_CREATION_DIR}/image-version-info
#	mkdosfs -F 32 -n ${TRIKIMG_USER_PARTION_LABEL} -d ${WORKDIR}/${TRIK_USER_PARTION_CREATION_DIR} ${TRIKIMG_USER_PARTION}
# }

MBR_SIZE ?= "4K"
BLOCK_SIZE ?= "4096"
ALIGNMENT ?= "4"
DIRECT_BLOCKS ?= "12"
SECTOR_SIZE ?= "512"
BLOCKS_PER_INDIRECT_BLOCK = "1024"
# ${BLOCK_SIZE} / 4

def write_indirect_block(device, indirect_block, blocks):
    import sys
    import tempfile
    import struct
    import subprocess

    block_size = int(d.getVar("BLOCK_SIZE"))
    blocks_per_indirect_block = int(d.getVar("BLOCKS_PER_INDIRECT_BLOCK"))

    print("writing indirect block ", indirect_block)
    dev = open(device, "wb")
    dev.seek(indirect_block * block_size)
    for block in blocks:
        bin_block = struct.pack("<I", int(block))
        dev.write(bin_block)
    zero = struct.pack("<I", 0)
    for x in range(len(blocks), blocks_per_indirect_block):
        dev.write(zero)
    dev.close()

def insert_file_ext4(outFile, inFile, sizeKb, offsetKb):
    import sys
    import tempfile
    import struct
    import subprocess

    block_size = int(d.getVar("BLOCK_SIZE"))
    direct_blocks = int(d.getVar("DIRECT_BLOCKS"))
    sector_size = int(d.getVar("SECTOR_SIZE"))
    blocks_per_indirect_block = int(d.getVar("BLOCKS_PER_INDIRECT_BLOCK"))

    size = int(size) * 1024 # Size in bytes
    offset = int(offset) * 1024 # Offset from the start of the outFile in bytes

    if size > 0xFFFFFFFF:
        print("Unable to allocate files over 4GB.")
        return

    ind_blocks = (size_blocks - direct_blocks) / blocks_per_indirect_block
    if (size_blocks - direct_blocks) % blocks_per_indirect_block != 0:
        ind_blocks += 1
    has_dind_block = ind_blocks > 1
    total_blocks = size_blocks + ind_blocks
    if has_dind_block:
        total_blocks += 1

    offset_block = offset / block_size
    print("Finding ", total_blocks, " free blocks from block ", offset_block)
    process = subprocess.Popen(["debugfs", outFile, "-R", "ffb %d %d" % (total_blocks, offset_block)], stdout=subprocess.PIPE)
    output = process.stdout
    blocks = output.readline().split(" ")[3:]
    output.close()
    blocks = filter(lambda x: len(x.strip(" \n")) > 0, blocks)
    if len(blocks) != total_blocks:
        print("Not enough free blocks found for the inFile.")
        return

    if ind_blocks > 0:
        write_indirect_block(outFile, int(blocks[direct_blocks]), blocks[direct_blocks + 1 : direct_blocks + 1 + blocks_per_indirect_block])

    if has_dind_block:
        dind_block_index = direct_blocks + 1 + blocks_per_indirect_block
        dind_block = blocks[dind_block_index]
        ind_block_indices = [dind_block_index+1+(i*(blocks_per_indirect_block+1)) for i in range(ind_blocks-1)]
        write_indirect_block(outFile, int(dind_block), [blocks[i] for i in ind_block_indices])
        for i in ind_block_indices:
            write_indirect_block(outFile, int(blocks[i]), blocks[i+1:i+1+blocks_per_indirect_block])

    script = tempfile.NamedTemporaryFile(mode = "w", delete = False)
    for block in blocks:
        script.write("setb %s\n" % (block,))

    for i in range(direct_blocks):
        script.write("sif %s block[%d] %s\n" % (inFile, i, blocks[i]))

    if size_blocks > direct_blocks:
        script.write("sif %s block[IND] %s\n" % (inFile, blocks[direct_blocks]))

    if has_dind_block:
        script.write("sif %s block[DIND] %s\n" % (inFile, dind_block))

    script.write("sif %s size %d\n" % (inFile, size))
    script.close()

    print("Modifying file")
    subprocess.call(["debugfs", "-w", outFile, "-f", script.name])
    script.unlink(script.name)

create_img() {
 rm -f ${TRIKIMG_FILE}
 cp ${TRIKIMG_ROOTFS} ${TRIKIMG_FILE}
}

python do_bootable_sdimg() {
 bb.build.exec_func("create_img", d)
 bb.build.exec_func("insert_uboot", d)
}

ERROR_QA:remove = "version-going-backwards"

python insert_uboot() {
    import os

    insert_file_ext4d.getVar("TRIKIMG_FILE"), d.getVar("DEPLOY_DIR_IMAGE") + "/u-boot.ais", os.stat(d.getVar("DEPLOY_DIR_IMAGE") + "/u-boot.ais").file_stats.st_size * 1024, 4)
}



addtask bootable_sdimg after do_image_ext4 u-boot-trik:do_deploy coreutils-native:do_populate_sysroot util-linux-native:do_populate_sysroot before do_build