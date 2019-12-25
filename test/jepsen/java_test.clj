(ns jepsen.java-test
  (:require [clojure.test :refer :all]
            [jepsen.java :refer :all]))

(deftest a-test
  (testing "Dummy test"
    (is (= 1 1))))
