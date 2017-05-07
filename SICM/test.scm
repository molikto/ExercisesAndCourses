

(define (mark m str)
	(print-expression (string-append m str))
)

(define (todo str) (mark "todo " str))

(define (exercise str) (mark "exercise " str))

(define (chapter str) (mark "chapter " str))


(define  ((L-free-particle mass) local)
	(let 
		(
			(v (velocity local))
		)
		(* 1/2 mass (dot-product v v))
	)
)

(define q
	(up (literal-function 'x) (literal-function 'y) (literal-function 'z))
)

(print-expression (q 't))

(print-expression ((D q) 't))

(print-expression ((Gamma q) 't))

(print-expression 
	(
		(compose (L-free-particle 'm) (Gamma q)) 't
	)
)

(define (L-action L q t1 t2)
	(definite-integral (compose L (Gamma q)) t1 t2)
)

(define (test-path t)
	(up 
		(+ (* 4 t) 7)
		(+ (* 3 t) 5)
		(+ (* 2 t) 1)
	)
)

(print-expression
	(L-action (L-free-particle 3.0) test-path 0.0 10.0)
)


(todo "1.4")


(define ((make-eta nu t1 t2) t) 
	(* (- t t1) (- t t2) (nu t)))

(define ((varied-free-particle-action mass q nu t1 t2) epsilon)
	(L-action 
		(L-free-particle mass)
		(+ q (* epsilon (make-eta nu t1 t2)))
		t1
		t2
	)
)

(print-expression
	((varied-free-particle-action
	 3.0 test-path (up sin cos square) 0.0 10.0) 0.001)
)


(print-expression
	(minimize
		(varied-free-particle-action 3.0 test-path (up sin cos square) 0.0 10.0)
		-2.0
		1.0
	)
)


(todo "1.5")

(todo "1.6")


(define ((L-equations L) q) 
	(- 
		(D (compose ((partial 2) L) (Gamma q)))
		(compose ((partial 1) L) (Gamma q))
	)
)


(define (test-path-sym t)
	(up 
		(+ (* 'a t) 'a0)
		(+ (* 'b t) 'b0)
		(+ (* 'c t) 'c0)
	)
)

(print-expression
	(((L-equations (L-free-particle 'm)) test-path-sym) 't)
)

(print-expression
	(((L-equations (L-free-particle 'm)) (literal-function 'x)) 't)
)


(define  ((L-harmonic mass k) local)
	(let*
		(
			(v (velocity local))
			(x (coordinate local))
			(kin (* 1/2 mass (dot-product v v)))
		)
		(- kin (* 1/2 k (dot-product x x)))
	)
) ; -1/2 mv^2 - 1/2 k x^2

(define (L-harmonic-proposed-solution t)
	(* 'a (cos (+ (* 'omega t) 'phi)))
)


; harmonic motion L-expression

(print-expression
	(((L-equations (L-harmonic 'm 'k)) L-harmonic-proposed-solution) 't)
) ; a(k-m(w^2))cos(wt+p) => w = sqrt(k / m)

; orbital motion L-expression

(define  ((L-orbital mass mu) local)
	(let*
		(
			(v (velocity local))
			(x (coordinate local))
			(kin (* 1/2 mass (dot-product v v)))
			(grav (/ mu (sqrt (dot-product x x))))
		)
		(+ kin grav)
	)
) ; -1/2 mv^2 + mu / x^2


(define (L-orbital-proposed-solution t)
	(* 'a (up (sin t) (cos t)))
)



(print-expression
	(((L-equations (L-orbital 'm 'u)) L-orbital-proposed-solution) 't)
) ; a^3m = u



(exercise "1.9")


(define ((L-exercise-1-9-a mass) local)
	(let*
		(
			(v (velocity local))
			(q (coordinate local))
			(x (ref q 0))
			(y (ref q 1))
			(qs (dot-product q q))
			(kin (* 1/2 mass (dot-product v v)))
			(pot (+ (/ qs 2) (* x x y) (negate (/ (* y y y) 3))))
		)
		(- kin pot)
	)
)


(print-expression
	(((L-equations (L-exercise-1-9-a 'm)) q) 't)
) ; a^3m = u

(define ((L-planar-pendulum m l g) local)
  (let ((q (coordinate local)) (v (velocity local)))
    (+
     (* 1/2 m (square l) (square v))
     (* m g l (cos q)))
      ))

(print-expression
 (((L-equations (L-planar-pendulum 'm 'l 'g))
   (literal-function 'theta))
  't))

(todo "1.9 c")

(exercise "1.10")


(define ((L-equations3 L) q)
  (+
   (D (D (compose ((partial 3) L) (Gamma q 4))))
   (- (D (compose ((partial 2) L) (Gamma q 4))))
   (compose ((partial 1) L) (Gamma q 4))
   ))


(define ((L-exercise-1-10 m k) local)
  (let ((x (coordinate local))
        (v (velocity local))
        (a (acceleration local)))
    (+
     (* -1 1/2 m x a)
     (* -1 1/2 k (square x)))
    ))

(print-expression 
	(((L-equations3 (L-exercise-1-10 'm 'k)) (literal-function 'q)) 't)
) ; mx'' = -kx


(todo "1.10 c")











