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

VENDOR=" --with-vendor-name=Ziher"
VENDOR+=" --with-vendor-url=https://github.com/hernad/panama-foreign"
VENDOR+=" --with-version-pre=panama"
VENDOR+=" --with-version-string=15"
VENDOR+=" --with-version-build=49"

VENDOR+=" --with-version-feature=15"
VENDOR+=" --with-version-update=0"
VENDOR+=" --with-version-patch=2"


#C:\dev\java\x86>java -version
#openjdk version "15-internal" 2020-09-15

#C:\dev\java>java -version
#openjdk version "14.0.1" 2020-04-14
#OpenJDK Runtime Environment AdoptOpenJDK (build 14.0.1+7)
#OpenJDK 64-Bit Server VM AdoptOpenJDK (build 14.0.1+7, mixed mode, sharing)

#OpenJDK Runtime Environment (build 15-internal+0-adhoc.ernadhusremovic.panama-foreign)
#OpenJDK Server VM (build 15-internal+0-adhoc.ernadhusremovic.panama-foreign, mixed mode, emulated-client)


#  --with-vendor-name      Set vendor name. Among others, used to set the
#                          'java.vendor' and 'java.vm.vendor' system
#                          properties. [not specified]
#  --with-vendor-url       Set the 'java.vendor.url' system property [not
#                          specified]
#  --with-vendor-bug-url   Set the 'java.vendor.url.bug' system property [not
#                          specified]
#  --with-vendor-vm-bug-url
#                          Sets the bug URL which will be displayed when the VM
#                          crashes [not specified]
#  --with-version-string   Set version string [calculated]
#  --with-version-pre      Set the base part of the version 'PRE' field
#                          (pre-release identifier) ['internal']
#  --with-version-opt      Set version 'OPT' field (build metadata)
#                          [<timestamp>.<user>.<dirname>]
#  --with-version-build    Set version 'BUILD' field (build number) [not
#                          specified]
#  --with-version-feature  Set version 'FEATURE' field (first number) [current
#                          source value]
#  --with-version-interim  Set version 'INTERIM' field (second number) [current
#                          source value]
#  --with-version-update   Set version 'UPDATE' field (third number) [current
#                          source value]
#  --with-version-patch    Set version 'PATCH' field (fourth number) [not
#                          specified]
#  --with-version-extra1   Set 1st version extra number [not specified]
#  --with-version-extra2   Set 2nd version extra number [not specified]
#  --with-version-extra3   Set 3rd version extra number [not specified]
#  --with-version-date     Set version date [current source value]
#  --with-vendor-version-string
#                          Set vendor version string [not specified]


#rm -rf build

# cygwin make, java/
export PATH=/usr/bin:$JAVA_PATH/bin:$PATH
./configure $LLVM $BITNESS $VENDOR

make CONF=$CONF clean
make CONF=$CONF
