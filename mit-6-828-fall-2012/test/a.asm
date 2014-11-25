a.out:
(__TEXT,__text) section
_b:
00001f40	pushl	%ebp
00001f41	movl	%esp, %ebp
00001f43	subl	$0x8, %esp
00001f46	movl	0xc(%ebp), %eax
00001f49	movl	0x8(%ebp), %ecx
00001f4c	movl	%ecx, 0xfffffffffffffffc(%ebp)
00001f4f	movl	%eax, 0xfffffffffffffff8(%ebp)
00001f52	movl	0xfffffffffffffff8(%ebp), %eax
00001f55	addl	$0x8, %esp
00001f58	popl	%ebp
00001f59	ret
00001f5a	nopw	(%eax,%eax)
_main:
00001f60	pushl	%ebp # previous stack base??
00001f61	movl	%esp, %ebp # change the base now to previous stack top?
00001f63	subl	$0x18, %esp # grow the stack top? - grow down 
00001f66	movl	$0x1, %eax # eax is variable a
00001f6b	movl	$0x2, %ecx # what is this??
00001f70	movl	$0x1, 0xfffffffffffffffc(%ebp) # stack variable 1 - a
00001f77	movl	0xfffffffffffffffc(%ebp), %edx # move to edx
00001f7a	addl	$0xffffffff, %edx  # add 1
00001f80	movl	%edx, 0xfffffffffffffffc(%ebp) # move back
00001f83	movl	$0x1, (%esp) # put it in stack top
00001f8a	movl	$0x2, 0x4(%esp) # put it in... so confusing
00001f92	movl	%eax, 0xfffffffffffffff4(%ebp)
00001f95	movl	%ecx, 0xfffffffffffffff0(%ebp)
00001f98	calll	_b
00001f9d	movl	$0x0, %ecx
00001fa2	movl	%eax, 0xfffffffffffffff8(%ebp)
00001fa5	movl	%ecx, %eax
00001fa7	addl	$0x18, %esp
00001faa	popl	%ebp
00001fab	ret
