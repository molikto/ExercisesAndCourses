
export "CC=clang"
# stop clang from turning warnings into erros, or use -Wno-unused-value
export "CFLAGS=-g -O2 -Wno-error"

//cd binutils-2.23.2
//./configure --target=i386-jos-elf --disable-nls --prefix=/usr/local/jos
//make -j4
//sudo make install
//cd ..
//mkdir gcc-build
//cd gcc-build
//../gcc-4.6.4/configure --target=i386-jos-elf  --prefix=/usr/local/jos --disable-nls --without-headers --with-newlib --disable-threads --disable-shared --disable-libmudflap --disable-libssp --disable-libquadmath --enable-languages=c
//make -j4
//sudo make install
//cd ..
//mkdir gdb-build
//cd gdb-build
//
//../gdb-6.8/configure --target=i386-jos-elf --program-prefix=i386-jos-elf- \
//--disable-werror --prefix=/usr/local/jos
//make -j4
//sudo make install
cd qemu
export "CC=clang -F/Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX10.7.sdk/System/Library/Frameworks/"
# it is annoying that qemu's Makefile can't find required frameworks.
export "PATH=$PATH:/Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX10.7.sdk/System/Library/Frameworks/"

./configure --disable-kvm --disable-sdl --prefix=/usr/local/jos --target-list="i386-softmmu" --enable-tcg-interpreter
make -j4
sudo make install
