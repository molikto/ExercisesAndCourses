
MPFR=mpfr-2.4.2
GMP=gmp-4.3.2
MPC=mpc-0.8.1

wget ftp://gcc.gnu.org/pub/gcc/infrastructure/$MPFR.tar.bz2 || exit 1
tar xjf $MPFR.tar.bz2 || exit 1
ln -sf $MPFR mpfr || exit 1

wget ftp://gcc.gnu.org/pub/gcc/infrastructure/$GMP.tar.bz2 || exit 1
tar xjf $GMP.tar.bz2  || exit 1
ln -sf $GMP gmp || exit 1

wget ftp://gcc.gnu.org/pub/gcc/infrastructure/$MPC.tar.gz || exit 1
tar xzf $MPC.tar.gz || exit 1
ln -sf $MPC mpc || exit 1

rm $MPFR.tar.bz2 $GMP.tar.bz2 $MPC.tar.gz || exit 1
