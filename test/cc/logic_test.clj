(ns cc.logic-test
  (:require [clojure.test :refer :all]
            [cc.logic :refer :all]))

(deftest tem-limite?-test
  (testing "Tem limite quando o valor da compra é menor"
    (is (tem-limite? 1 0)))

  (testing "Não tem limite quando o valor da compra é maior"
    (is (not (tem-limite? 0 1))))

  (testing "Tem limite quando o valor da compra é igual ao limite")
    (is (tem-limite? 1 1))
  )

