#!/bin/bash 
 
# BUILD_ARCH=x86 scripts/build_windows.sh 

if [ "$BUILD_ARCH" == "x86" ] ; then
   BITNESS="--with-target-bits=32"
   #JAVA_PATH=/cygdrive/c/AdoptOpenJDK/x86/jdk-14.0.1.7-hotspot # jdk-8.0.252.09-hotspot
   # boot jdk treba uvijek biti 64bit
   JAVA_PATH=/cygdrive/c/AdoptOpenJDK/jdk-14.0.1.7-hotspot
   LLVM="--with-libclang=/cygdrive/c/LLVM/x86/9"
   CONF=windows-x86-server-release

else
   JAVA_PATH=/cygdrive/c/AdoptOpenJDK/jdk-14.0.1.7-hotspot
   LLVM="--with-libclang=/cygdrive/c/LLVM/9"
   CONF=windows-x86_64-server-release

fi
JAVA_HOME=`cygpath -w $JAVA_PATH`




#rm -rf build

# cygwin make, java/
export PATH=/usr/bin:$JAVA_PATH/bin:$PATH
./configure $LLVM $BITNESS

make CONF=$CONF clean
make CONF=$CONF