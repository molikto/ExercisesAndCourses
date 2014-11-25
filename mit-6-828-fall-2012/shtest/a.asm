a.out:
(__TEXT,__text) section
_runcmd:
00000001000010e0	pushq	%rbp
00000001000010e1	movq	%rsp, %rbp
00000001000010e4	subq	$0x60, %rsp
00000001000010e8	movq	%rdi, 0xfffffffffffffff8(%rbp)
00000001000010ec	cmpq	$0x0, 0xfffffffffffffff8(%rbp)
00000001000010f4	jne	0x100001104
00000001000010fa	movl	$0x0, %edi
00000001000010ff	callq	0x100001dec ## symbol stub for: _exit
0000000100001104	movq	0xfffffffffffffff8(%rbp), %rax
0000000100001108	movl	(%rax), %ecx
000000010000110a	movl	%ecx, %edx
000000010000110c	subl	$0x7b, %edx
000000010000110f	movl	%ecx, 0xffffffffffffffcc(%rbp)
0000000100001112	movl	%edx, 0xffffffffffffffc8(%rbp)
0000000100001115	ja	0x10000115c
000000010000111b	jmpq	0x100001120
0000000100001120	movl	0xffffffffffffffcc(%rbp), %eax
0000000100001123	subl	$0x20, %eax
0000000100001126	movl	%eax, 0xffffffffffffffc4(%rbp)
0000000100001129	je	0x100001195
000000010000112f	jmpq	0x100001134
0000000100001134	movl	0xffffffffffffffcc(%rbp), %eax
0000000100001137	subl	$0x3c, %eax
000000010000113a	movl	%eax, 0xffffffffffffffc0(%rbp)
000000010000113d	je	0x1000011d9
0000000100001143	jmpq	0x100001148
0000000100001148	movl	0xffffffffffffffcc(%rbp), %eax
000000010000114b	subl	$0x3e, %eax
000000010000114e	movl	%eax, 0xffffffffffffffbc(%rbp)
0000000100001151	je	0x1000011d9
0000000100001157	jmpq	0x100001170
000000010000115c	movl	0xffffffffffffffcc(%rbp), %eax
000000010000115f	subl	$0x7c, %eax
0000000100001162	movl	%eax, 0xffffffffffffffb8(%rbp)
0000000100001165	je	0x10000120e
000000010000116b	jmpq	0x100001170
0000000100001170	leaq	0xd5f(%rip), %rsi ## literal pool for: unknown runcmd

0000000100001177	movq	0xe92(%rip), %rax
000000010000117e	movq	(%rax), %rdi
0000000100001181	movb	$0x0, %al
0000000100001183	callq	0x100001e04 ## symbol stub for: _fprintf
0000000100001188	movl	$0xffffffff, %edi
000000010000118d	movl	%eax, 0xffffffffffffffb4(%rbp)
0000000100001190	callq	0x100001dec ## symbol stub for: _exit
0000000100001195	movq	0xfffffffffffffff8(%rbp), %rax
0000000100001199	movq	%rax, 0xffffffffffffffe0(%rbp)
000000010000119d	movq	0xffffffffffffffe0(%rbp), %rax
00000001000011a1	cmpq	$0x0, 0x8(%rax)
00000001000011a9	jne	0x1000011b9
00000001000011af	movl	$0x0, %edi
00000001000011b4	callq	0x100001dec ## symbol stub for: _exit
00000001000011b9	leaq	0xd26(%rip), %rsi ## literal pool for: exec not implemented

00000001000011c0	movq	0xe49(%rip), %rax
00000001000011c7	movq	(%rax), %rdi
00000001000011ca	movb	$0x0, %al
00000001000011cc	callq	0x100001e04 ## symbol stub for: _fprintf
00000001000011d1	movl	%eax, 0xffffffffffffffb0(%rbp)
00000001000011d4	jmpq	0x100001231
00000001000011d9	leaq	0xd1c(%rip), %rsi ## literal pool for: redir not implemented

00000001000011e0	movq	0xe29(%rip), %rax
00000001000011e7	movq	0xfffffffffffffff8(%rbp), %rcx
00000001000011eb	movq	%rcx, 0xffffffffffffffd0(%rbp)
00000001000011ef	movq	(%rax), %rdi
00000001000011f2	movb	$0x0, %al
00000001000011f4	callq	0x100001e04 ## symbol stub for: _fprintf
00000001000011f9	movq	0xffffffffffffffd0(%rbp), %rcx
00000001000011fd	movq	0x8(%rcx), %rdi
0000000100001201	movl	%eax, 0xffffffffffffffac(%rbp)
0000000100001204	callq	_runcmd
0000000100001209	jmpq	0x100001231
000000010000120e	leaq	0xcfe(%rip), %rsi ## literal pool for: pipe not implemented

0000000100001215	movq	0xdf4(%rip), %rax
000000010000121c	movq	0xfffffffffffffff8(%rbp), %rcx
0000000100001220	movq	%rcx, 0xffffffffffffffd8(%rbp)
0000000100001224	movq	(%rax), %rdi
0000000100001227	movb	$0x0, %al
0000000100001229	callq	0x100001e04 ## symbol stub for: _fprintf
000000010000122e	movl	%eax, 0xffffffffffffffa8(%rbp)
0000000100001231	movl	$0x0, %edi
0000000100001236	callq	0x100001dec ## symbol stub for: _exit
000000010000123b	nopl	(%rax,%rax)
_getcmd:
0000000100001240	pushq	%rbp
0000000100001241	movq	%rsp, %rbp
0000000100001244	subq	$0x30, %rsp
0000000100001248	movq	0xdc9(%rip), %rax
000000010000124f	movq	%rdi, 0xfffffffffffffff0(%rbp)
0000000100001253	movl	%esi, 0xffffffffffffffec(%rbp)
0000000100001256	movq	(%rax), %rdi
0000000100001259	callq	0x100001df8 ## symbol stub for: _fileno
000000010000125e	movl	%eax, %edi
0000000100001260	callq	0x100001e0a ## symbol stub for: _isatty
0000000100001265	cmpl	$0x0, %eax
000000010000126a	je	0x10000128b
0000000100001270	leaq	0xcb2(%rip), %rsi ## literal pool for: $ 
0000000100001277	movq	0xda2(%rip), %rax
000000010000127e	movq	(%rax), %rdi
0000000100001281	movb	$0x0, %al
0000000100001283	callq	0x100001e04 ## symbol stub for: _fprintf
0000000100001288	movl	%eax, 0xffffffffffffffe8(%rbp)
000000010000128b	movl	$0x0, %esi
0000000100001290	movabsq	$0xffffffffffffffff, %rcx
000000010000129a	movq	0xfffffffffffffff0(%rbp), %rdi
000000010000129e	movslq	0xffffffffffffffec(%rbp), %rdx
00000001000012a2	callq	0x100001dda ## symbol stub for: ___memset_chk
00000001000012a7	movq	0xd6a(%rip), %rcx
00000001000012ae	movq	0xfffffffffffffff0(%rbp), %rdi
00000001000012b2	movl	0xffffffffffffffec(%rbp), %esi
00000001000012b5	movq	(%rcx), %rdx
00000001000012b8	movq	%rax, 0xffffffffffffffe0(%rbp)
00000001000012bc	callq	0x100001df2 ## symbol stub for: _fgets
00000001000012c1	movq	0xfffffffffffffff0(%rbp), %rcx
00000001000012c5	movsbl	(%rcx), %esi
00000001000012c8	cmpl	$0x0, %esi
00000001000012ce	movq	%rax, 0xffffffffffffffd8(%rbp)
00000001000012d2	jne	0x1000012e4
00000001000012d8	movl	$0xffffffff, 0xfffffffffffffffc(%rbp)
00000001000012df	jmpq	0x1000012eb
00000001000012e4	movl	$0x0, 0xfffffffffffffffc(%rbp)
00000001000012eb	movl	0xfffffffffffffffc(%rbp), %eax
00000001000012ee	addq	$0x30, %rsp
00000001000012f2	popq	%rbp
00000001000012f3	ret
00000001000012f4	nopw	%cs:(%rax,%rax)
_main:
0000000100001300	pushq	%rbp
0000000100001301	movq	%rsp, %rbp
0000000100001304	subq	$0x20, %rsp
0000000100001308	movl	$0x0, 0xfffffffffffffffc(%rbp)
000000010000130f	leaq	_main.buf(%rip), %rdi
0000000100001316	movl	$0x64, %esi
000000010000131b	callq	_getcmd
0000000100001320	cmpl	$0x0, %eax
0000000100001325	jl	0x100001400
000000010000132b	movsbl	_main.buf(%rip), %eax
0000000100001332	cmpl	$0x63, %eax
0000000100001337	jne	0x1000013cb
000000010000133d	movsbl	0xd6d(%rip), %eax
0000000100001344	cmpl	$0x64, %eax
0000000100001349	jne	0x1000013cb
000000010000134f	movsbl	0xd5c(%rip), %eax
0000000100001356	cmpl	$0x20, %eax
000000010000135b	jne	0x1000013cb
0000000100001361	leaq	_main.buf(%rip), %rdi
0000000100001368	callq	0x100001e22 ## symbol stub for: _strlen
000000010000136d	leaq	_main.buf(%rip), %rdi
0000000100001374	movq	%rdi, %rcx
0000000100001377	addq	$0x3, %rcx
000000010000137e	subq	$0x1, %rax
0000000100001384	movb	$0x0, (%rdi,%rax)
0000000100001388	movq	%rcx, %rdi
000000010000138b	callq	0x100001de6 ## symbol stub for: _chdir
0000000100001390	cmpl	$0x0, %eax
0000000100001395	jge	0x1000013c6
000000010000139b	leaq	0xb8a(%rip), %rsi ## literal pool for: cannot cd %s

00000001000013a2	leaq	_main.buf(%rip), %rax
00000001000013a9	addq	$0x3, %rax
00000001000013af	movq	0xc5a(%rip), %rcx
00000001000013b6	movq	(%rcx), %rdi
00000001000013b9	movq	%rax, %rdx
00000001000013bc	movb	$0x0, %al
00000001000013be	callq	0x100001e04 ## symbol stub for: _fprintf
00000001000013c3	movl	%eax, 0xfffffffffffffff0(%rbp)
00000001000013c6	jmpq	0x10000130f
00000001000013cb	callq	_fork1
00000001000013d0	cmpl	$0x0, %eax
00000001000013d5	jne	0x1000013ef
00000001000013db	leaq	_main.buf(%rip), %rdi
00000001000013e2	callq	_parsecmd
00000001000013e7	movq	%rax, %rdi
00000001000013ea	callq	_runcmd
00000001000013ef	leaq	0xfffffffffffffff4(%rbp), %rdi
00000001000013f3	callq	0x100001e28 ## symbol stub for: _wait
00000001000013f8	movl	%eax, 0xffffffffffffffec(%rbp)
00000001000013fb	jmpq	0x10000130f
0000000100001400	movl	$0x0, %edi
0000000100001405	callq	0x100001dec ## symbol stub for: _exit
000000010000140a	nopw	(%rax,%rax)
_fork1:
0000000100001410	pushq	%rbp
0000000100001411	movq	%rsp, %rbp
0000000100001414	subq	$0x10, %rsp
0000000100001418	callq	0x100001dfe ## symbol stub for: _fork
000000010000141d	movl	%eax, 0xfffffffffffffffc(%rbp)
0000000100001420	cmpl	$0xffffffff, 0xfffffffffffffffc(%rbp)
0000000100001427	jne	0x100001439
000000010000142d	leaq	0xb06(%rip), %rdi ## literal pool for: fork
0000000100001434	callq	0x100001e16 ## symbol stub for: _perror
0000000100001439	movl	0xfffffffffffffffc(%rbp), %eax
000000010000143c	addq	$0x10, %rsp
0000000100001440	popq	%rbp
0000000100001441	ret
0000000100001442	nopw	%cs:(%rax,%rax)
_parsecmd:
0000000100001450	pushq	%rbp
0000000100001451	movq	%rsp, %rbp
0000000100001454	subq	$0x30, %rsp
0000000100001458	movq	%rdi, 0xfffffffffffffff8(%rbp)
000000010000145c	movq	0xfffffffffffffff8(%rbp), %rdi
0000000100001460	movq	0xfffffffffffffff8(%rbp), %rax
0000000100001464	movq	%rdi, 0xffffffffffffffe0(%rbp)
0000000100001468	movq	%rax, %rdi
000000010000146b	callq	0x100001e22 ## symbol stub for: _strlen
0000000100001470	leaq	0xfffffffffffffff8(%rbp), %rdi
0000000100001474	movq	0xffffffffffffffe0(%rbp), %rcx
0000000100001478	addq	%rax, %rcx
000000010000147b	movq	%rcx, 0xfffffffffffffff0(%rbp)
000000010000147f	movq	0xfffffffffffffff0(%rbp), %rsi
0000000100001483	callq	_parseline
0000000100001488	leaq	0xfffffffffffffff8(%rbp), %rdi
000000010000148c	leaq	0xaba(%rip), %rdx ## literal pool for: 
0000000100001493	movq	%rax, 0xffffffffffffffe8(%rbp)
0000000100001497	movq	0xfffffffffffffff0(%rbp), %rsi
000000010000149b	callq	_peek
00000001000014a0	movq	0xfffffffffffffff8(%rbp), %rcx
00000001000014a4	cmpq	0xfffffffffffffff0(%rbp), %rcx
00000001000014a8	movl	%eax, 0xffffffffffffffdc(%rbp)
00000001000014ab	je	0x1000014da
00000001000014b1	leaq	0xa96(%rip), %rsi ## literal pool for: leftovers: %s

00000001000014b8	movq	0xb51(%rip), %rax
00000001000014bf	movq	(%rax), %rdi
00000001000014c2	movq	0xfffffffffffffff8(%rbp), %rdx
00000001000014c6	movb	$0x0, %al
00000001000014c8	callq	0x100001e04 ## symbol stub for: _fprintf
00000001000014cd	movl	$0xffffffff, %edi
00000001000014d2	movl	%eax, 0xffffffffffffffd8(%rbp)
00000001000014d5	callq	0x100001dec ## symbol stub for: _exit
00000001000014da	movq	0xffffffffffffffe8(%rbp), %rax
00000001000014de	addq	$0x30, %rsp
00000001000014e2	popq	%rbp
00000001000014e3	ret
00000001000014e4	nopw	%cs:(%rax,%rax)
_execcmd:
00000001000014f0	pushq	%rbp
00000001000014f1	movq	%rsp, %rbp
00000001000014f4	subq	$0x10, %rsp
00000001000014f8	movabsq	$0x58, %rdi
0000000100001502	callq	0x100001e10 ## symbol stub for: _malloc
0000000100001507	movl	$0x0, %esi
000000010000150c	movabsq	$0x58, %rdx
0000000100001516	movabsq	$0xffffffffffffffff, %rcx
0000000100001520	movq	%rax, 0xfffffffffffffff8(%rbp)
0000000100001524	movq	0xfffffffffffffff8(%rbp), %rax
0000000100001528	movq	%rax, %rdi
000000010000152b	callq	0x100001dda ## symbol stub for: ___memset_chk
0000000100001530	movq	0xfffffffffffffff8(%rbp), %rcx
0000000100001534	movl	$0x20, (%rcx)
000000010000153a	movq	0xfffffffffffffff8(%rbp), %rcx
000000010000153e	movq	%rax, 0xfffffffffffffff0(%rbp)
0000000100001542	movq	%rcx, %rax
0000000100001545	addq	$0x10, %rsp
0000000100001549	popq	%rbp
000000010000154a	ret
000000010000154b	nopl	(%rax,%rax)
_redircmd:
0000000100001550	pushq	%rbp
0000000100001551	movq	%rsp, %rbp
0000000100001554	subq	$0x30, %rsp
0000000100001558	movabsq	$0x20, %rax
0000000100001562	movq	%rdi, 0xfffffffffffffff8(%rbp)
0000000100001566	movq	%rsi, 0xfffffffffffffff0(%rbp)
000000010000156a	movl	%edx, 0xffffffffffffffec(%rbp)
000000010000156d	movq	%rax, %rdi
0000000100001570	callq	0x100001e10 ## symbol stub for: _malloc
0000000100001575	movl	$0x0, %esi
000000010000157a	movabsq	$0x20, %rdx
0000000100001584	movabsq	$0xffffffffffffffff, %rcx
000000010000158e	movq	%rax, 0xffffffffffffffe0(%rbp)
0000000100001592	movq	0xffffffffffffffe0(%rbp), %rax
0000000100001596	movq	%rax, %rdi
0000000100001599	callq	0x100001dda ## symbol stub for: ___memset_chk
000000010000159e	movl	$0x0, %esi
00000001000015a3	movl	$0x1, %r8d
00000001000015a9	movl	$0x601, %r9d
00000001000015af	movl	0xffffffffffffffec(%rbp), %r10d
00000001000015b3	movq	0xffffffffffffffe0(%rbp), %rcx
00000001000015b7	movl	%r10d, (%rcx)
00000001000015ba	movq	0xfffffffffffffff8(%rbp), %rcx
00000001000015be	movq	0xffffffffffffffe0(%rbp), %rdx
00000001000015c2	movq	%rcx, 0x8(%rdx)
00000001000015c6	movq	0xfffffffffffffff0(%rbp), %rcx
00000001000015ca	movq	0xffffffffffffffe0(%rbp), %rdx
00000001000015ce	movq	%rcx, 0x10(%rdx)
00000001000015d2	cmpl	$0x3c, 0xffffffffffffffec(%rbp)
00000001000015d9	sete	%r11b
00000001000015dd	testb	%r11b, %r11b
00000001000015e0	movl	%esi, %r10d
00000001000015e3	cmovel	%r9d, %r10d
00000001000015e7	movq	0xffffffffffffffe0(%rbp), %rcx
00000001000015eb	movl	%r10d, 0x18(%rcx)
00000001000015ef	cmpl	$0x3c, 0xffffffffffffffec(%rbp)
00000001000015f6	sete	%r11b
00000001000015fa	testb	%r11b, %r11b
00000001000015fd	cmovel	%r8d, %esi
0000000100001601	movq	0xffffffffffffffe0(%rbp), %rcx
0000000100001605	movl	%esi, 0x1c(%rcx)
0000000100001608	movq	0xffffffffffffffe0(%rbp), %rcx
000000010000160c	movq	%rax, 0xffffffffffffffd8(%rbp)
0000000100001610	movq	%rcx, %rax
0000000100001613	addq	$0x30, %rsp
0000000100001617	popq	%rbp
0000000100001618	ret
0000000100001619	nopl	(%rax)
_pipecmd:
0000000100001620	pushq	%rbp
0000000100001621	movq	%rsp, %rbp
0000000100001624	subq	$0x20, %rsp
0000000100001628	movabsq	$0x18, %rax
0000000100001632	movq	%rdi, 0xfffffffffffffff8(%rbp)
0000000100001636	movq	%rsi, 0xfffffffffffffff0(%rbp)
000000010000163a	movq	%rax, %rdi
000000010000163d	callq	0x100001e10 ## symbol stub for: _malloc
0000000100001642	movl	$0x0, %esi
0000000100001647	movabsq	$0x18, %rdx
0000000100001651	movabsq	$0xffffffffffffffff, %rcx
000000010000165b	movq	%rax, 0xffffffffffffffe8(%rbp)
000000010000165f	movq	0xffffffffffffffe8(%rbp), %rax
0000000100001663	movq	%rax, %rdi
0000000100001666	callq	0x100001dda ## symbol stub for: ___memset_chk
000000010000166b	movq	0xffffffffffffffe8(%rbp), %rcx
000000010000166f	movl	$0x7c, (%rcx)
0000000100001675	movq	0xfffffffffffffff8(%rbp), %rcx
0000000100001679	movq	0xffffffffffffffe8(%rbp), %rdx
000000010000167d	movq	%rcx, 0x8(%rdx)
0000000100001681	movq	0xfffffffffffffff0(%rbp), %rcx
0000000100001685	movq	0xffffffffffffffe8(%rbp), %rdx
0000000100001689	movq	%rcx, 0x10(%rdx)
000000010000168d	movq	0xffffffffffffffe8(%rbp), %rcx
0000000100001691	movq	%rax, 0xffffffffffffffe0(%rbp)
0000000100001695	movq	%rcx, %rax
0000000100001698	addq	$0x20, %rsp
000000010000169c	popq	%rbp
000000010000169d	ret
000000010000169e	nop
_gettoken:
00000001000016a0	pushq	%rbp
00000001000016a1	movq	%rsp, %rbp
00000001000016a4	subq	$0x50, %rsp
00000001000016a8	movq	%rdi, 0xfffffffffffffff8(%rbp)
00000001000016ac	movq	%rsi, 0xfffffffffffffff0(%rbp)
00000001000016b0	movq	%rdx, 0xffffffffffffffe8(%rbp)
00000001000016b4	movq	%rcx, 0xffffffffffffffe0(%rbp)
00000001000016b8	movq	0xfffffffffffffff8(%rbp), %rcx
00000001000016bc	movq	(%rcx), %rcx
00000001000016bf	movq	%rcx, 0xffffffffffffffd8(%rbp)
00000001000016c3	movb	$0x0, %al
00000001000016c5	movq	0xffffffffffffffd8(%rbp), %rcx
00000001000016c9	cmpq	0xfffffffffffffff0(%rbp), %rcx
00000001000016cd	movb	%al, 0xffffffffffffffd3(%rbp)
00000001000016d0	jae	0x1000016f5
00000001000016d6	leaq	_whitespace(%rip), %rdi
00000001000016dd	movq	0xffffffffffffffd8(%rbp), %rax
00000001000016e1	movsbl	(%rax), %esi
00000001000016e4	callq	0x100001e1c ## symbol stub for: _strchr
00000001000016e9	cmpq	$0x0, %rax
00000001000016ef	setne	%cl
00000001000016f2	movb	%cl, 0xffffffffffffffd3(%rbp)
00000001000016f5	movb	0xffffffffffffffd3(%rbp), %al
00000001000016f8	testb	$0x1, %al
00000001000016fa	jne	0x100001705
0000000100001700	jmpq	0x100001718
0000000100001705	movq	0xffffffffffffffd8(%rbp), %rax
0000000100001709	addq	$0x1, %rax
000000010000170f	movq	%rax, 0xffffffffffffffd8(%rbp)
0000000100001713	jmpq	0x1000016c3
0000000100001718	cmpq	$0x0, 0xffffffffffffffe8(%rbp)
0000000100001720	je	0x100001731
0000000100001726	movq	0xffffffffffffffd8(%rbp), %rax
000000010000172a	movq	0xffffffffffffffe8(%rbp), %rcx
000000010000172e	movq	%rax, (%rcx)
0000000100001731	movq	0xffffffffffffffd8(%rbp), %rax
0000000100001735	movsbl	(%rax), %ecx
0000000100001738	movl	%ecx, 0xffffffffffffffd4(%rbp)
000000010000173b	movq	0xffffffffffffffd8(%rbp), %rax
000000010000173f	movsbl	(%rax), %ecx
0000000100001742	movl	%ecx, %edx
0000000100001744	subl	$0x7b, %edx
0000000100001747	movl	%ecx, 0xffffffffffffffcc(%rbp)
000000010000174a	movl	%edx, 0xffffffffffffffc8(%rbp)
000000010000174d	ja	0x100001790
0000000100001753	jmpq	0x100001758
0000000100001758	movl	0xffffffffffffffcc(%rbp), %eax
000000010000175b	testl	%eax, %eax
000000010000175d	je	0x1000017a4
0000000100001763	jmpq	0x100001768
0000000100001768	movl	0xffffffffffffffcc(%rbp), %eax
000000010000176b	subl	$0x3c, %eax
000000010000176e	movl	%eax, 0xffffffffffffffc4(%rbp)
0000000100001771	je	0x1000017a9
0000000100001777	jmpq	0x10000177c
000000010000177c	movl	0xffffffffffffffcc(%rbp), %eax
000000010000177f	subl	$0x3e, %eax
0000000100001782	movl	%eax, 0xffffffffffffffc0(%rbp)
0000000100001785	je	0x1000017bc
000000010000178b	jmpq	0x1000017cf
0000000100001790	movl	0xffffffffffffffcc(%rbp), %eax
0000000100001793	subl	$0x7c, %eax
0000000100001796	movl	%eax, 0xffffffffffffffbc(%rbp)
0000000100001799	je	0x1000017a9
000000010000179f	jmpq	0x1000017cf
00000001000017a4	jmpq	0x100001857
00000001000017a9	movq	0xffffffffffffffd8(%rbp), %rax
00000001000017ad	addq	$0x1, %rax
00000001000017b3	movq	%rax, 0xffffffffffffffd8(%rbp)
00000001000017b7	jmpq	0x100001857
00000001000017bc	movq	0xffffffffffffffd8(%rbp), %rax
00000001000017c0	addq	$0x1, %rax
00000001000017c6	movq	%rax, 0xffffffffffffffd8(%rbp)
00000001000017ca	jmpq	0x100001857
00000001000017cf	movl	$0x61, 0xffffffffffffffd4(%rbp)
00000001000017d6	movb	$0x0, %al
00000001000017d8	movq	0xffffffffffffffd8(%rbp), %rcx
00000001000017dc	cmpq	0xfffffffffffffff0(%rbp), %rcx
00000001000017e0	movb	%al, 0xffffffffffffffbb(%rbp)
00000001000017e3	jae	0x10000182f
00000001000017e9	leaq	_whitespace(%rip), %rdi
00000001000017f0	movq	0xffffffffffffffd8(%rbp), %rax
00000001000017f4	movsbl	(%rax), %esi
00000001000017f7	callq	0x100001e1c ## symbol stub for: _strchr
00000001000017fc	movb	$0x0, %cl
00000001000017fe	cmpq	$0x0, %rax
0000000100001804	movb	%cl, 0xffffffffffffffbb(%rbp)
0000000100001807	jne	0x10000182f
000000010000180d	leaq	_symbols(%rip), %rdi
0000000100001814	movq	0xffffffffffffffd8(%rbp), %rax
0000000100001818	movsbl	(%rax), %esi
000000010000181b	callq	0x100001e1c ## symbol stub for: _strchr
0000000100001820	cmpq	$0x0, %rax
0000000100001826	setne	%cl
0000000100001829	xorb	$0x1, %cl
000000010000182c	movb	%cl, 0xffffffffffffffbb(%rbp)
000000010000182f	movb	0xffffffffffffffbb(%rbp), %al
0000000100001832	testb	$0x1, %al
0000000100001834	jne	0x10000183f
000000010000183a	jmpq	0x100001852
000000010000183f	movq	0xffffffffffffffd8(%rbp), %rax
0000000100001843	addq	$0x1, %rax
0000000100001849	movq	%rax, 0xffffffffffffffd8(%rbp)
000000010000184d	jmpq	0x1000017d6
0000000100001852	jmpq	0x100001857
0000000100001857	cmpq	$0x0, 0xffffffffffffffe0(%rbp)
000000010000185f	je	0x100001870
0000000100001865	movq	0xffffffffffffffd8(%rbp), %rax
0000000100001869	movq	0xffffffffffffffe0(%rbp), %rcx
000000010000186d	movq	%rax, (%rcx)
0000000100001870	jmpq	0x100001875
0000000100001875	movb	$0x0, %al
0000000100001877	movq	0xffffffffffffffd8(%rbp), %rcx
000000010000187b	cmpq	0xfffffffffffffff0(%rbp), %rcx
000000010000187f	movb	%al, 0xffffffffffffffba(%rbp)
0000000100001882	jae	0x1000018a7
0000000100001888	leaq	_whitespace(%rip), %rdi
000000010000188f	movq	0xffffffffffffffd8(%rbp), %rax
0000000100001893	movsbl	(%rax), %esi
0000000100001896	callq	0x100001e1c ## symbol stub for: _strchr
000000010000189b	cmpq	$0x0, %rax
00000001000018a1	setne	%cl
00000001000018a4	movb	%cl, 0xffffffffffffffba(%rbp)
00000001000018a7	movb	0xffffffffffffffba(%rbp), %al
00000001000018aa	testb	$0x1, %al
00000001000018ac	jne	0x1000018b7
00000001000018b2	jmpq	0x1000018ca
00000001000018b7	movq	0xffffffffffffffd8(%rbp), %rax
00000001000018bb	addq	$0x1, %rax
00000001000018c1	movq	%rax, 0xffffffffffffffd8(%rbp)
00000001000018c5	jmpq	0x100001875
00000001000018ca	movq	0xffffffffffffffd8(%rbp), %rax
00000001000018ce	movq	0xfffffffffffffff8(%rbp), %rcx
00000001000018d2	movq	%rax, (%rcx)
00000001000018d5	movl	0xffffffffffffffd4(%rbp), %eax
00000001000018d8	addq	$0x50, %rsp
00000001000018dc	popq	%rbp
00000001000018dd	ret
00000001000018de	nop
_peek:
00000001000018e0	pushq	%rbp
00000001000018e1	movq	%rsp, %rbp
00000001000018e4	subq	$0x30, %rsp
00000001000018e8	movq	%rdi, 0xfffffffffffffff8(%rbp)
00000001000018ec	movq	%rsi, 0xfffffffffffffff0(%rbp)
00000001000018f0	movq	%rdx, 0xffffffffffffffe8(%rbp)
00000001000018f4	movq	0xfffffffffffffff8(%rbp), %rdx
00000001000018f8	movq	(%rdx), %rdx
00000001000018fb	movq	%rdx, 0xffffffffffffffe0(%rbp)
00000001000018ff	movb	$0x0, %al
0000000100001901	movq	0xffffffffffffffe0(%rbp), %rcx
0000000100001905	cmpq	0xfffffffffffffff0(%rbp), %rcx
0000000100001909	movb	%al, 0xffffffffffffffdf(%rbp)
000000010000190c	jae	0x100001931
0000000100001912	leaq	_whitespace(%rip), %rdi
0000000100001919	movq	0xffffffffffffffe0(%rbp), %rax
000000010000191d	movsbl	(%rax), %esi
0000000100001920	callq	0x100001e1c ## symbol stub for: _strchr
0000000100001925	cmpq	$0x0, %rax
000000010000192b	setne	%cl
000000010000192e	movb	%cl, 0xffffffffffffffdf(%rbp)
0000000100001931	movb	0xffffffffffffffdf(%rbp), %al
0000000100001934	testb	$0x1, %al
0000000100001936	jne	0x100001941
000000010000193c	jmpq	0x100001954
0000000100001941	movq	0xffffffffffffffe0(%rbp), %rax
0000000100001945	addq	$0x1, %rax
000000010000194b	movq	%rax, 0xffffffffffffffe0(%rbp)
000000010000194f	jmpq	0x1000018ff
0000000100001954	movb	$0x0, %al
0000000100001956	movq	0xffffffffffffffe0(%rbp), %rcx
000000010000195a	movq	0xfffffffffffffff8(%rbp), %rdx
000000010000195e	movq	%rcx, (%rdx)
0000000100001961	movq	0xffffffffffffffe0(%rbp), %rcx
0000000100001965	movsbl	(%rcx), %esi
0000000100001968	cmpl	$0x0, %esi
000000010000196e	movb	%al, 0xffffffffffffffde(%rbp)
0000000100001971	je	0x100001993
0000000100001977	movq	0xffffffffffffffe8(%rbp), %rdi
000000010000197b	movq	0xffffffffffffffe0(%rbp), %rax
000000010000197f	movsbl	(%rax), %esi
0000000100001982	callq	0x100001e1c ## symbol stub for: _strchr
0000000100001987	cmpq	$0x0, %rax
000000010000198d	setne	%cl
0000000100001990	movb	%cl, 0xffffffffffffffde(%rbp)
0000000100001993	movb	0xffffffffffffffde(%rbp), %al
0000000100001996	andb	$0x1, %al
0000000100001998	movzbl	%al, %eax
000000010000199b	addq	$0x30, %rsp
000000010000199f	popq	%rbp
00000001000019a0	ret
00000001000019a1	nopw	%cs:(%rax,%rax)
_mkcopy:
00000001000019b0	pushq	%rbp
00000001000019b1	movq	%rsp, %rbp
00000001000019b4	subq	$0x30, %rsp
00000001000019b8	movq	%rdi, 0xfffffffffffffff8(%rbp)
00000001000019bc	movq	%rsi, 0xfffffffffffffff0(%rbp)
00000001000019c0	movq	0xfffffffffffffff0(%rbp), %rsi
00000001000019c4	movq	0xfffffffffffffff8(%rbp), %rdi
00000001000019c8	subq	%rdi, %rsi
00000001000019cb	movl	%esi, %eax
00000001000019cd	movl	%eax, 0xffffffffffffffec(%rbp)
00000001000019d0	movl	0xffffffffffffffec(%rbp), %eax
00000001000019d3	addl	$0x1, %eax
00000001000019d8	movslq	%eax, %rdi
00000001000019db	callq	0x100001e10 ## symbol stub for: _malloc
00000001000019e0	movq	%rax, 0xffffffffffffffe0(%rbp)
00000001000019e4	cmpq	$0x0, 0xffffffffffffffe0(%rbp)
00000001000019ec	setne	%cl
00000001000019ef	xorb	$0x1, %cl
00000001000019f2	andb	$0x1, %cl
00000001000019f5	movzbl	%cl, %edx
00000001000019f8	movslq	%edx, %rax
00000001000019fb	cmpq	$0x0, %rax
0000000100001a01	je	0x100001a26
0000000100001a07	leaq	0x531(%rip), %rdi ## literal pool for: mkcopy
0000000100001a0e	leaq	0x531(%rip), %rsi ## literal pool for: sh.c
0000000100001a15	movl	$0xeb, %edx
0000000100001a1a	leaq	0x52a(%rip), %rcx ## literal pool for: c
0000000100001a21	callq	0x100001dd4 ## symbol stub for: ___assert_rtn
0000000100001a26	jmpq	0x100001a2b
0000000100001a2b	movabsq	$0xffffffffffffffff, %rcx
0000000100001a35	movq	0xffffffffffffffe0(%rbp), %rdi
0000000100001a39	movq	0xfffffffffffffff8(%rbp), %rsi
0000000100001a3d	movslq	0xffffffffffffffec(%rbp), %rdx
0000000100001a41	callq	0x100001de0 ## symbol stub for: ___strncpy_chk
0000000100001a46	movslq	0xffffffffffffffec(%rbp), %rcx
0000000100001a4a	movq	0xffffffffffffffe0(%rbp), %rdx
0000000100001a4e	movb	$0x0, (%rdx,%rcx)
0000000100001a52	movq	0xffffffffffffffe0(%rbp), %rcx
0000000100001a56	movq	%rax, 0xffffffffffffffd8(%rbp)
0000000100001a5a	movq	%rcx, %rax
0000000100001a5d	addq	$0x30, %rsp
0000000100001a61	popq	%rbp
0000000100001a62	ret
0000000100001a63	nopw	%cs:(%rax,%rax)
_parseline:
0000000100001a70	pushq	%rbp
0000000100001a71	movq	%rsp, %rbp
0000000100001a74	subq	$0x20, %rsp
0000000100001a78	movq	%rdi, 0xfffffffffffffff8(%rbp)
0000000100001a7c	movq	%rsi, 0xfffffffffffffff0(%rbp)
0000000100001a80	movq	0xfffffffffffffff8(%rbp), %rdi
0000000100001a84	movq	0xfffffffffffffff0(%rbp), %rsi
0000000100001a88	callq	_parsepipe
0000000100001a8d	movq	%rax, 0xffffffffffffffe8(%rbp)
0000000100001a91	movq	0xffffffffffffffe8(%rbp), %rax
0000000100001a95	addq	$0x20, %rsp
0000000100001a99	popq	%rbp
0000000100001a9a	ret
0000000100001a9b	nopl	(%rax,%rax)
_parsepipe:
0000000100001aa0	pushq	%rbp
0000000100001aa1	movq	%rsp, %rbp
0000000100001aa4	subq	$0x30, %rsp
0000000100001aa8	movq	%rdi, 0xfffffffffffffff8(%rbp)
0000000100001aac	movq	%rsi, 0xfffffffffffffff0(%rbp)
0000000100001ab0	movq	0xfffffffffffffff8(%rbp), %rdi
0000000100001ab4	movq	0xfffffffffffffff0(%rbp), %rsi
0000000100001ab8	callq	_parseexec
0000000100001abd	leaq	0x499(%rip), %rdx ## literal pool for: |
0000000100001ac4	movq	%rax, 0xffffffffffffffe8(%rbp)
0000000100001ac8	movq	0xfffffffffffffff8(%rbp), %rdi
0000000100001acc	movq	0xfffffffffffffff0(%rbp), %rsi
0000000100001ad0	callq	_peek
0000000100001ad5	cmpl	$0x0, %eax
0000000100001ada	je	0x100001b28
0000000100001ae0	movabsq	$0x0, %rax
0000000100001aea	movq	0xfffffffffffffff8(%rbp), %rdi
0000000100001aee	movq	0xfffffffffffffff0(%rbp), %rsi
0000000100001af2	movq	%rax, %rdx
0000000100001af5	movq	%rax, %rcx
0000000100001af8	callq	_gettoken
0000000100001afd	movq	0xffffffffffffffe8(%rbp), %rdi
0000000100001b01	movq	0xfffffffffffffff8(%rbp), %rcx
0000000100001b05	movq	0xfffffffffffffff0(%rbp), %rsi
0000000100001b09	movq	%rdi, 0xffffffffffffffe0(%rbp)
0000000100001b0d	movq	%rcx, %rdi
0000000100001b10	movl	%eax, 0xffffffffffffffdc(%rbp)
0000000100001b13	callq	_parsepipe
0000000100001b18	movq	0xffffffffffffffe0(%rbp), %rdi
0000000100001b1c	movq	%rax, %rsi
0000000100001b1f	callq	_pipecmd
0000000100001b24	movq	%rax, 0xffffffffffffffe8(%rbp)
0000000100001b28	movq	0xffffffffffffffe8(%rbp), %rax
0000000100001b2c	addq	$0x30, %rsp
0000000100001b30	popq	%rbp
0000000100001b31	ret
0000000100001b32	nopw	%cs:(%rax,%rax)
_parseexec:
0000000100001b40	pushq	%rbp
0000000100001b41	movq	%rsp, %rbp
0000000100001b44	subq	$0x40, %rsp
0000000100001b48	movq	%rdi, 0xfffffffffffffff8(%rbp)
0000000100001b4c	movq	%rsi, 0xfffffffffffffff0(%rbp)
0000000100001b50	callq	_execcmd
0000000100001b55	movq	%rax, 0xffffffffffffffc8(%rbp)
0000000100001b59	movq	0xffffffffffffffc8(%rbp), %rax
0000000100001b5d	movq	%rax, 0xffffffffffffffd0(%rbp)
0000000100001b61	movl	$0x0, 0xffffffffffffffd8(%rbp)
0000000100001b68	movq	0xffffffffffffffc8(%rbp), %rdi
0000000100001b6c	movq	0xfffffffffffffff8(%rbp), %rsi
0000000100001b70	movq	0xfffffffffffffff0(%rbp), %rdx
0000000100001b74	callq	_parseredirs
0000000100001b79	movq	%rax, 0xffffffffffffffc8(%rbp)
0000000100001b7d	leaq	0x3d9(%rip), %rdx ## literal pool for: |
0000000100001b84	movq	0xfffffffffffffff8(%rbp), %rdi
0000000100001b88	movq	0xfffffffffffffff0(%rbp), %rsi
0000000100001b8c	callq	_peek
0000000100001b91	cmpl	$0x0, %eax
0000000100001b96	setne	%cl
0000000100001b99	xorb	$0x1, %cl
0000000100001b9c	testb	$0x1, %cl
0000000100001b9f	jne	0x100001baa
0000000100001ba5	jmpq	0x100001c76
0000000100001baa	leaq	0xffffffffffffffe8(%rbp), %rdx
0000000100001bae	leaq	0xffffffffffffffe0(%rbp), %rcx
0000000100001bb2	movq	0xfffffffffffffff8(%rbp), %rdi
0000000100001bb6	movq	0xfffffffffffffff0(%rbp), %rsi
0000000100001bba	callq	_gettoken
0000000100001bbf	movl	%eax, 0xffffffffffffffdc(%rbp)
0000000100001bc2	cmpl	$0x0, %eax
0000000100001bc7	jne	0x100001bd2
0000000100001bcd	jmpq	0x100001c76
0000000100001bd2	cmpl	$0x61, 0xffffffffffffffdc(%rbp)
0000000100001bd9	je	0x100001c04
0000000100001bdf	leaq	0x39a(%rip), %rsi ## literal pool for: syntax error

0000000100001be6	movq	0x423(%rip), %rax
0000000100001bed	movq	(%rax), %rdi
0000000100001bf0	movb	$0x0, %al
0000000100001bf2	callq	0x100001e04 ## symbol stub for: _fprintf
0000000100001bf7	movl	$0xffffffff, %edi
0000000100001bfc	movl	%eax, 0xffffffffffffffc4(%rbp)
0000000100001bff	callq	0x100001dec ## symbol stub for: _exit
0000000100001c04	movq	0xffffffffffffffe8(%rbp), %rdi
0000000100001c08	movq	0xffffffffffffffe0(%rbp), %rsi
0000000100001c0c	callq	_mkcopy
0000000100001c11	movslq	0xffffffffffffffd8(%rbp), %rsi
0000000100001c15	movq	0xffffffffffffffd0(%rbp), %rdi
0000000100001c19	movq	%rax, 0x8(%rdi,%rsi,8)
0000000100001c1e	movl	0xffffffffffffffd8(%rbp), %ecx
0000000100001c21	addl	$0x1, %ecx
0000000100001c27	movl	%ecx, 0xffffffffffffffd8(%rbp)
0000000100001c2a	cmpl	$0xa, 0xffffffffffffffd8(%rbp)
0000000100001c31	jl	0x100001c5c
0000000100001c37	leaq	0x350(%rip), %rsi ## literal pool for: too many args

0000000100001c3e	movq	0x3cb(%rip), %rax
0000000100001c45	movq	(%rax), %rdi
0000000100001c48	movb	$0x0, %al
0000000100001c4a	callq	0x100001e04 ## symbol stub for: _fprintf
0000000100001c4f	movl	$0xffffffff, %edi
0000000100001c54	movl	%eax, 0xffffffffffffffc0(%rbp)
0000000100001c57	callq	0x100001dec ## symbol stub for: _exit
0000000100001c5c	movq	0xffffffffffffffc8(%rbp), %rdi
0000000100001c60	movq	0xfffffffffffffff8(%rbp), %rsi
0000000100001c64	movq	0xfffffffffffffff0(%rbp), %rdx
0000000100001c68	callq	_parseredirs
0000000100001c6d	movq	%rax, 0xffffffffffffffc8(%rbp)
0000000100001c71	jmpq	0x100001b7d
0000000100001c76	movslq	0xffffffffffffffd8(%rbp), %rax
0000000100001c7a	movq	0xffffffffffffffd0(%rbp), %rcx
0000000100001c7e	movq	$0x0, 0x8(%rcx,%rax,8)
0000000100001c87	movq	0xffffffffffffffc8(%rbp), %rax
0000000100001c8b	addq	$0x40, %rsp
0000000100001c8f	popq	%rbp
0000000100001c90	ret
0000000100001c91	nopw	%cs:(%rax,%rax)
_parseredirs:
0000000100001ca0	pushq	%rbp
0000000100001ca1	movq	%rsp, %rbp
0000000100001ca4	subq	$0x50, %rsp
0000000100001ca8	movq	%rdi, 0xfffffffffffffff8(%rbp)
0000000100001cac	movq	%rsi, 0xfffffffffffffff0(%rbp)
0000000100001cb0	movq	%rdx, 0xffffffffffffffe8(%rbp)
0000000100001cb4	leaq	0x2a4(%rip), %rdx ## literal pool for: <>
0000000100001cbb	movq	0xfffffffffffffff0(%rbp), %rdi
0000000100001cbf	movq	0xffffffffffffffe8(%rbp), %rsi
0000000100001cc3	callq	_peek
0000000100001cc8	cmpl	$0x0, %eax
0000000100001ccd	je	0x100001dc9
0000000100001cd3	movabsq	$0x0, %rax
0000000100001cdd	movq	0xfffffffffffffff0(%rbp), %rdi
0000000100001ce1	movq	0xffffffffffffffe8(%rbp), %rsi
0000000100001ce5	movq	%rax, %rdx
0000000100001ce8	movq	%rax, %rcx
0000000100001ceb	callq	_gettoken
0000000100001cf0	leaq	0xffffffffffffffd8(%rbp), %rdx
0000000100001cf4	leaq	0xffffffffffffffd0(%rbp), %rcx
0000000100001cf8	movl	%eax, 0xffffffffffffffe4(%rbp)
0000000100001cfb	movq	0xfffffffffffffff0(%rbp), %rdi
0000000100001cff	movq	0xffffffffffffffe8(%rbp), %rsi
0000000100001d03	callq	_gettoken
0000000100001d08	cmpl	$0x61, %eax
0000000100001d0d	je	0x100001d38
0000000100001d13	leaq	0x248(%rip), %rsi ## literal pool for: missing file for redirection

0000000100001d1a	movq	0x2ef(%rip), %rax
0000000100001d21	movq	(%rax), %rdi
0000000100001d24	movb	$0x0, %al
0000000100001d26	callq	0x100001e04 ## symbol stub for: _fprintf
0000000100001d2b	movl	$0xffffffff, %edi
0000000100001d30	movl	%eax, 0xffffffffffffffcc(%rbp)
0000000100001d33	callq	0x100001dec ## symbol stub for: _exit
0000000100001d38	movl	0xffffffffffffffe4(%rbp), %eax
0000000100001d3b	movl	%eax, %ecx
0000000100001d3d	subl	$0x3e, %ecx
0000000100001d40	movl	%eax, 0xffffffffffffffc8(%rbp)
0000000100001d43	movl	%ecx, 0xffffffffffffffc4(%rbp)
0000000100001d46	je	0x100001d97
0000000100001d4c	jmpq	0x100001d51
0000000100001d51	movl	0xffffffffffffffc8(%rbp), %eax
0000000100001d54	subl	$0x3c, %eax
0000000100001d57	movl	%eax, 0xffffffffffffffc0(%rbp)
0000000100001d5a	jne	0x100001dc4
0000000100001d60	jmpq	0x100001d65
0000000100001d65	movq	0xfffffffffffffff8(%rbp), %rdi
0000000100001d69	movq	0xffffffffffffffd8(%rbp), %rax
0000000100001d6d	movq	0xffffffffffffffd0(%rbp), %rsi
0000000100001d71	movq	%rdi, 0xffffffffffffffb8(%rbp)
0000000100001d75	movq	%rax, %rdi
0000000100001d78	callq	_mkcopy
0000000100001d7d	movl	$0x3c, %edx
0000000100001d82	movq	0xffffffffffffffb8(%rbp), %rdi
0000000100001d86	movq	%rax, %rsi
0000000100001d89	callq	_redircmd
0000000100001d8e	movq	%rax, 0xfffffffffffffff8(%rbp)
0000000100001d92	jmpq	0x100001dc4
0000000100001d97	movq	0xfffffffffffffff8(%rbp), %rdi
0000000100001d9b	movq	0xffffffffffffffd8(%rbp), %rax
0000000100001d9f	movq	0xffffffffffffffd0(%rbp), %rsi
0000000100001da3	movq	%rdi, 0xffffffffffffffb0(%rbp)
0000000100001da7	movq	%rax, %rdi
0000000100001daa	callq	_mkcopy
0000000100001daf	movl	$0x3e, %edx
0000000100001db4	movq	0xffffffffffffffb0(%rbp), %rdi
0000000100001db8	movq	%rax, %rsi
0000000100001dbb	callq	_redircmd
0000000100001dc0	movq	%rax, 0xfffffffffffffff8(%rbp)
0000000100001dc4	jmpq	0x100001cb4
0000000100001dc9	movq	0xfffffffffffffff8(%rbp), %rax
0000000100001dcd	addq	$0x50, %rsp
0000000100001dd1	popq	%rbp
0000000100001dd2	ret
