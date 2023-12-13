
IMAGE_ROOTFS_ALIGNMENT ?= "4"

XZ_DICTIONARY_SIZE = "128"
XZ_THREADS = "2"
XZ_COMPRESSION_LEVEL ?= "--verbose --no-adjust --memlimit-compress=6GiB --arm --lzma2=mode=normal,dict=${XZ_DICTIONARY_SIZE}MiB,lc=1,lp=2,pb=2,mf=bt4,nice=192,depth=1024"
EXTRA_IMAGECMD:ext4 =+ " -E stride=2 -E stripe-width=16 -b 4096 -i 4096 "

inherit image_types logging user-partion
#TODO image-mklibs

DEPENDS += "u-boot-trik"
IMAGE_TYPES += "ext4 img"
IMAGE_TYPEDEP:img = "ext4"

IMAGE_FSTYPES = "img"

#IMAGE_TYPES_MASKED = "img"

EXCLUDE_FROM_WORLD = "1"

#TODO inherit image-prelink

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

    BLOCK_SIZE = int(${BLOCK_SIZE})
    DIRECT_BLOCKS = int(${DIRECT_BLOCKS})
    SECTOR_SIZE = int(${SECTOR_SIZE})
    BLOCKS_PER_INDIRECT_BLOCK = int(${BLOCKS_PER_INDIRECT_BLOCK})

    print "writing indirect block ", indirect_block
    dev = open(device, "wb")
    dev.seek(indirect_block * BLOCK_SIZE)
    # Write blocks
    for block in blocks:
        bin_block = struct.pack("<I", int(block))
        dev.write(bin_block)
    zero = struct.pack("<I", 0)
    # Zero out the rest of the block
    for x in range(len(blocks), BLOCKS_PER_INDIRECT_BLOCK):
        dev.write(zero)
    dev.close()

def insert_file_ext4(outFile, inFile, sizeKb, offsetKb):
    import sys
    import tempfile
    import struct
    import subprocess

    BLOCK_SIZE = int(${BLOCK_SIZE})
    DIRECT_BLOCKS = int(${DIRECT_BLOCKS})
    SECTOR_SIZE = int(${SECTOR_SIZE})
    BLOCKS_PER_INDIRECT_BLOCK = int(${BLOCKS_PER_INDIRECT_BLOCK})

    size = int(size) * 1024 # Size in KB
    offset = int(offset) * 1024 # Offset from the start of the outFile in KB

    if size > 0xFFFFFFFF:
        # Supporting this requires two things: triple indirect block support, and proper handling of size_high when changing the inode
        print "Unable to allocate files over 4GB."
        return

    # Because size is specified in MB, it should always be exactly divisable by BLOCK_SIZE.
    size_blocks = size / BLOCK_SIZE
    # We need 1 indirect block for each 1024 blocks over 12 blocks.
    ind_blocks = (size_blocks - DIRECT_BLOCKS) / BLOCKS_PER_INDIRECT_BLOCK
    if (size_blocks - DIRECT_BLOCKS) % BLOCKS_PER_INDIRECT_BLOCK != 0:
        ind_blocks += 1
    # We need a double indirect block if we have more than one indirect block
    has_dind_block = ind_blocks > 1
    total_blocks = size_blocks + ind_blocks
    if has_dind_block:
        total_blocks += 1

    # Find free blocks we can use at the offset
    offset_block = offset / BLOCK_SIZE
    print "Finding ", total_blocks, " free blocks from block ", offset_block
    process = subprocess.Popen(["debugfs", outFile, "-R", "ffb %d %d" % (total_blocks, offset_block)], stdout=subprocess.PIPE)
    output = process.stdout
    # The first three entries after splitting are "Free", "blocks", "found:", so we skip those.
    blocks = output.readline().split(" ")[3:]
    output.close()
    # The last entry may contain a line-break. Removing it this way to be safe.
    blocks = filter(lambda x: len(x.strip(" \n")) > 0, blocks)
    if len(blocks) != total_blocks:
        print "Not enough free blocks found for the inFile."
        return

    # The direct blocks in the inode are blocks 0-11
    # Write the first indirect block, listing the blocks for file blocks 12-1035 (inclusive)
    if ind_blocks > 0:
        write_indirect_block(outFile, int(blocks[DIRECT_BLOCKS]), blocks[DIRECT_BLOCKS + 1 : DIRECT_BLOCKS + 1 + BLOCKS_PER_INDIRECT_BLOCK])

    if has_dind_block:
        dind_block_index = DIRECT_BLOCKS + 1 + BLOCKS_PER_INDIRECT_BLOCK
        dind_block = blocks[dind_block_index]
        ind_block_indices = [dind_block_index+1+(i*(BLOCKS_PER_INDIRECT_BLOCK+1)) for i in range(ind_blocks-1)]
        # Write the double indirect block, listing the blocks for the remaining indirect block
        write_indirect_block(outFile, int(dind_block), [blocks[i] for i in ind_block_indices])
        # Write the remaining indirect blocks, listing the relevant file blocks
        for i in ind_block_indices:
            write_indirect_block(outFile, int(blocks[i]), blocks[i+1:i+1+BLOCKS_PER_INDIRECT_BLOCK])

    # Time to generate a script for debugfs
    script = tempfile.NamedTemporaryFile(mode = "w", delete = False)
    # Mark all the blocks as in-use
    for block in blocks:
        script.write("setb %s\n" % (block,))

    # Change direct blocks in the inode
    for i in range(DIRECT_BLOCKS):
        script.write("sif %s block[%d] %s\n" % (inFile, i, blocks[i]))

    # Change indirect block in the inode
    if size_blocks > DIRECT_BLOCKS:
        script.write("sif %s block[IND] %s\n" % (inFile, blocks[DIRECT_BLOCKS]))

    # Change double indirect block in the inode
    if has_dind_block:
        script.write("sif %s block[DIND] %s\n" % (inFile, dind_block))

    # Set total number of blocks in the inode (this value seems to actually be sectors
    script.write("sif %s blocks %d\n" % (inFile, total_blocks * (BLOCK_SIZE / SECTOR_SIZE)))
    # Set file size in the inode
    # TODO: Need support of size_high for large files
    script.write("sif %s size %d\n" % (inFile, size))
    script.close()

    # execute the script
    print "Modifying file"
    subprocess.call(["debugfs", "-w", outFile, "-f", script.name])
    script.unlink(script.name)


python insert_uboot():
    import os

    insert_file_ext4("${IMAGE}", "${DEPLOY_DIR_IMAGE}/u-boot.ais", os.stat("${DEPLOY_DIR_IMAGE}/u-boot.ais").file_stats.st_size * 1024, 4)

insert_uboot[depends] += "util-linux-native:do_populate_sysroot \
                               coreutils-native:do_populate_sysroot \
                               u-boot-trik:do_deploy \
                               ${PN}:do_image_ext4"
addtask insert_uboot