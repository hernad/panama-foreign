#!/bin/bash

#BUILD_ARCH=x64 scripts/update_local_jdk.sh

if [ "$BUILD_ARCH" == "x86" ] ; then
   SRC=/cygdrive/c/dev/panama-foreign/build/windows-x86-server-release/jdk
   DEST=/cygdrive/c/openjdk-panama-foreign/x86/jdk
else
   SRC=/cygdrive/c/dev/panama-foreign/build/windows-x86_64-server-release/jdk
   DEST=/cygdrive/c/openjdk-panama-foreign/x64/jdk
fi

echo " SRC=$SRC"
echo "DEST=$DEST"  
[ -z $SRC ] && echo "SRC nije definisan" && exit 1
[ -z $DEST ] && echo "DEST nije definisana" && exit 1


[ ! -f $SRC ] && echo "SRC ne postoji, DEST java" && $DEST/bin/java -version && exit 1


rm -rf $DEST

echo " $SRC -> $DEST"
mv $SRC $DEST

$DEST/bin/java -version
