(ns metop-lab3.quad-approx)

(def str-format "|\t%d\t|\t%.3f\t|\t%.3f\t|\t%.3f\t|\t%.3f\t|\t%.3f\t|\t%.3f\t|\n")

(def f (fn [x] (+ (* x x) (* -3 x) (* x (Math/log x)))))

(def index (fn
             ([storage value] (index storage value 0))
             ([storage value index] (if (== (get storage index) value) index (recur storage value (inc index))))
             ))

(declare find-extremum-step2 find-extremum-step6 find-extremum-step7)

(def find-extremum (fn [a b eps]
                     (println "|\t№\t|\tx1\t\t|\tx2\t\t|\tx3\t\t|\tf1\t\t|\tf2\t\t|\tf3\t\t|")
                     (find-extremum-step2 (+ a (/ (- b a) 4)) (/ (- b a) 4) eps eps)
                     ))
(def find-extremum-step2 (fn [x1 dx eps1 eps2] (
                                                 let [x2 (+ x1 dx) f1 (f x1) f2 (f x2) x3 (if (> f1 f2) (+ x1 (* 2 dx)) (- x1 dx))
                                                      f3 (f x3)]
                                                 (printf str-format 2 x1 x2 x3 f1 f2 f3)
                                                 (find-extremum-step6 x1 x2 x3 f1 f2 f3 eps1 eps2)
                                                 )))
(def find-extremum-step6 (fn [x1 x2 x3 f1 f2 f3 eps1 eps2] (
                                                             let [f-min (min f1 f2 f3) x-min (get [x1 x2 x3] (index [f1 f2 f3] f-min))
                                                                  div (+ (* f1 (- x2 x3)) (* f2 (- x3 x1)) (* f3 (- x1 x2)))]
                                                             (printf str-format 6 x1 x2 x3 f1 f2 f3)
                                                             (if (== div 0) (find-extremum-step2 x-min (- x2 x1) eps1 eps2) (find-extremum-step7 x1 x2 x3 f1 f2 f3 x-min f-min div eps1 eps2))
                                                             )))
(def find-extremum-step7 (fn [x1 x2 x3 f1 f2 f3 x-min f-min div eps1 eps2] (
                                                                             let [x- (/ (+
                                                                                          (* f1 (- (* x2 x2) (* x3 x3)))
                                                                                          (* f2 (- (* x3 x3) (* x1 x1)))
                                                                                          (* f3 (- (* x1 x1) (* x2 x2)))
                                                                                          ) (* 2 div))]
                                                                             (if (and (< (abs (/ (- f-min (f x-)) (f x-))) eps1) (< (abs (/ (- x-min x-) x-)) eps2))
                                                                               [x- (f x-)] (if (and (<= x1 x-) (<= x- x3))
                                                                                             (find-extremum-step6 (- (min x- x-min) (- x2 x1)) (min x- x-min) (+ (min x- x-min) (- x2 x1)) f1 f2 f3 eps1 eps2)
                                                                                             (find-extremum-step2 x- (- x2 x1) eps1 eps2)))
                                                                             )))