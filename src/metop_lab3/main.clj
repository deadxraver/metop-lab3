(ns metop-lab3.main
  (:require [metop-lab3.quad-approx :as quad-approx]))

(def str-format "x* = %.3f; f* = %.3f\n")

(def eps 0.00001)
(def a 1.0)
(def b 2.0)

(println "Метод квадратичной аппроксимации:")
(def res (quad-approx/find-extremum a b eps))
(printf str-format (get res 0) (get res 1))
