#!/bin/bash 
 
# BUILD_ARCH=x86 scripts/build_windows.sh 

if [ "$BUILD_ARCH" == "x86" ] ; then
    BITNESS="--with-target-bits=32"
fi

LLVM="--with-libclang=/cygdrive/c/LLVM/9"


./configure $LLVM $BITNESS

make