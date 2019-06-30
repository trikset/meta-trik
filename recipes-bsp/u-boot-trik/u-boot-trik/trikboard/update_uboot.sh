#!/bin/sh -e
#LOG=/tmp/uboot-update.log
exec 5>&1 
#exec 1>>$LOG
exec 2>&1

reset_fw() {
  RESET_FW=/usr/share/u-boot-trik/reset_uboot.sh 
  test -x $RESET_FW && $RESET_FW || echo "#### FAILED TO RESET SPI FLASH #### "
}

update_fw() {
  echo "*** STARTED at $(date -u)"
  case `md5sum /dev/mtd0 | cut -f 1 -d " "` in
  09a1d434dbd7197e7c3af8a7c28ca38b)  
     echo SPI flash is in erased state \(0xFF\). This is a new fresh board, isn\'t it?
     echo Flashing current version of U-Boot...
     reset_fw
   ;;
  0cbe8dd91995547d330018e4ef709923)
     echo This is old "U-Boot 2013.01.01-g273b816-dirty (Apr 11 2014 - 14:19:09)"
     echo Looks like TRIK 2014/2015/2016 with old U-Boot. Must be updated.
     reset_fw
  ;;
  *) 
     CURRENT=/usr/share/u-boot-trik/u-boot-gzip.ais
     SIZE=$(du -s --bytes "$CURRENT" | cut -f 1)
     if [ "$(head -c $SIZE $CURRENT | md5sum | cut -f 1 -d ' ')" \
	     = "$(head -c $SIZE /dev/mtd0 | md5sum | cut -f 1 -d ' ')" ]
     then
        echo "U-Boot is OK"
     else
         echo "#######WARNING##########"
         echo "#UNKNOWN U-BOOT VERSION#"
         md5sum /dev/mtd0
         head -c 65536 /dev/mtd0 | md5sum
         fw_printenv 2>&1       
     fi
  ;;
  esac
}
update_fw
exec 1>&5 5>&-
