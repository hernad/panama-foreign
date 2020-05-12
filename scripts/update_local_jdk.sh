#!/bin/bash

#BUILD_ARCH=x64 scripts/update_local_jdk.sh

[ "`uname`" == "Linux" ] && LINUX_OS="1"

function windows_install() {
if [ "$BUILD_ARCH" == "x86" ] ; then
   SRC=/cygdrive/c/dev/panama-foreign/build/windows-x86-server-release/jdk
   DEST=/cygdrive/c/openjdk-panama-foreign/x86/jdk
else
   SRC=/cygdrive/c/dev/panama-foreign/build/windows-x86_64-server-release/jdk
   DEST=/cygdrive/c/openjdk-panama-foreign/x64/jdk
fi
}

function linux_install() {
   SRC=$HOME/panama-foreign/build/linux-x86_64-server-release/jdk
   SRC_CFG=`pwd`/build/linux-x86_64-server-release/support/modules_libs/java.base/jvm.cfg

   DEST=$HOME/openjdk-panama-foreign/jdk
   DEST_CFG=$DEST/lib/jvm.cfg
}

if [ "$LINUX_OS" == "1" ] ; then
  linux_install
else
  windows_install
fi
 
echo " SRC=$SRC"
echo "DEST=$DEST"  
[ -z $SRC ] && echo "SRC nije definisan" && exit 1
[ -z $DEST ] && echo "DEST nije definisana" && echo "RUN: mkdir -p \$HOME/openjdk-panama-foreign" && exit 1


[ ! -d $SRC ] && echo "SRC ne postoji. STOP" && exit 1


rm -rf $DEST

echo " $SRC -> $DEST"
mv $SRC $DEST
cp -av $SRC_CFG $DEST_CFG 


$DEST/bin/java -version
