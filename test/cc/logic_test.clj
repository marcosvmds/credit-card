(ns cc.logic-test
  (:require [clojure.test :refer :all]
            [cc.logic :refer :all]
            [cc.model :as m]
            [cc.db :as db]
            [schema.core :as s]))

(deftest realizar-compra-test
  (testing "Realiza compra quando preço for menor que o limite, retornando um cliente novo"
    (let [cliente (db/cliente-com-limite)
          compra (m/nova-compra-teste-valor 0)]
      (is (s/validate m/Cliente (realizar-compra cliente compra)))))

  (testing "Realiza compra quando preço for igual ao limite, retornando um novo cliente"
    (let [cliente (db/cliente-com-limite)
          compra (m/nova-compra-teste-valor 1)]
      (is (s/validate m/Cliente (realizar-compra cliente compra)))))

  (testing "Não realiza compra se preço for maior que o limite, retornando nil."
    (let [cliente (db/cliente-com-limite)
          compra (m/nova-compra-teste-valor 2)]
      (is (nil? (realizar-compra cliente compra)))))

  (testing "Ao realizar uma compra, devolve um cliente com limite corretamente menor"
    (let [cliente (db/cliente-com-limite)
          compra (m/nova-compra-teste-valor 0.5)]
      (is (= (:limite (lista-dados-cartao (realizar-compra cliente compra))) 0.5)))))

(deftest lista-compras-realizadas-test
  (testing "Recebe um cliente e retorna um vetor com suas compras realizadas"
    (is (= (lista-compras-realizadas (db/cliente-com-limite))
           [{:item "item teste", :categoria "categoria teste", :valor 1, :estabelecimento "categoria teste", :data "data teste"}])))

  (testing "Ao receber um cliente sem compras realizadas, retorna uma sequencia vazia"
    (is (= (lista-compras-realizadas (db/cliente-com-limite-sem-compras)) ()))))

(deftest calcula-gastos-por-categoria-test
  (testing "Ao receber um mapa de Categoria [Vetor de compras], retorna a soma dos valores das compras"
    (let [categoria-com-duas-compras [1 [{:valor 1}, {:valor 1}]]]
      (is (= (calcula-gastos-por-categoria categoria-com-duas-compras)
              {:categoria 1 :valor-total 2})))))

