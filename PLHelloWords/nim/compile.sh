nim c --noMain --noLinking -d:release --header:fib.h fib.nim
gcc -o m -Inimcache -I/usr/local/Cellar/nim/0.13.0/nim/lib nimcache/*.c maths.c