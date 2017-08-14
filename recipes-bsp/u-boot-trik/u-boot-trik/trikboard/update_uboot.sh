#!/bin/sh -e
LOG=/tmp/uboot-update.log
exec 5>&1 
exec 1>>$LOG
exec 2>&1

reset_fw() {
  RESET_FW=/usr/share/u-boot-trik/reset_uboot.sh 
  test -x $RESET_FW && $RESET_FW || echo "#### FAILED TO RESET SPI FLASH #### "
}


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
59cd1c28bd93134edeb90b0f4249c414)
   echo This is current version, no update required.
;;
#???dac3a861e1bf99ad72d4268ffab6be07 #
*)
       echo "#######WARNING##########"
       echo "#UNKNOWN U-BOOT VERSION#"
       fw_printenv 2>&1
       md5sum /dev/mtd0
;;
esac
exec 1>&5 5>&-
