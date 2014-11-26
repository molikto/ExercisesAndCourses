; tiny.asm for Mac OS X (Mach-O Object File Format)
; nasm -f bin -o tiny tiny.asm

BITS 32
        org   0x1000

        db    0xce, 0xfa, 0xed, 0xfe       ; magic
        dd    7                            ; cputype (CPU_TYPE_X86)
        dd    3                            ; cpusubtype (CPU_SUBTYPE_I386_ALL)
        dd    2                            ; filetype (MH_EXECUTE)
        dd    2                            ; ncmds
        dd    _start - _cmds               ; cmdsize
        dd    0                            ; flags
_cmds:
        dd    1                            ; cmd (LC_SEGMENT)
        dd    44                           ; cmdsize
        db    "__TEXT"                     ; segname
        db    0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ; segname
        dd    0x1000                       ; vmaddr
        dd    0x1000                       ; vmsize
        dd    0                            ; fileoff
        dd    filesize                     ; filesize
        dd    7                            ; maxprot

        dd    5                            ; cmd (LC_UNIXTHREAD)
        dd    80                           ; cmdsize
        dd    1                            ; flvaor (i386_THREAD_STATE)
        dd    16                           ; count (i386_THREAD_STATE_COUNT)
        dd    0, 0, 0, 0, 0, 0, 0, 0       ; state
        dd    0, 0, _start, 0, 0, 0, 0, 0  ; state
_start:
        xor   eax,eax
        inc   eax
  push  byte 42
        sub   esp, 4
        int   0x80                         ; _exit(42)
filesize equ $ - $$
