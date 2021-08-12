(ns cc.logic
  (:require [cc.db]
            [cc.model :as m]
            [schema.core :as s]
            [clojure.pprint :as pprint])
  [:use java-time :only [local-date as]])

(s/set-fn-validation! true)

(s/defn lista-dados-cliente
  [cliente :- m/Cliente]
  (->> cliente
       (:dados-cliente)))

(s/defn lista-dados-cartao
  [cliente :- m/Cliente]
  (->> cliente
       (:cartao-de-credito)
       :dados-cartao))

(s/defn busca-limite
  [cliente :- m/Cliente]
  (->> cliente
       (lista-dados-cartao)
       :limite))

(s/defn tem-limite? :- s/Bool
  [limite :- m/PosOuZero, valor-da-compra]
  (>= limite valor-da-compra))

(s/defn lista-compras-realizadas
  [cliente :- m/Cliente]
  (->> cliente
       (:cartao-de-credito)
       (:compras-realizadas)
       (sort-by :data)
       reverse))

(s/defn calcula-gastos-por-categoria
  [[categoria, compras-da-categoria]]
  (let [valor-total (reduce + (map :valor compras-da-categoria))]
    {:categoria categoria :valor-total valor-total}))

(s/defn lista-compras-por-categoria
  [cliente :- m/Cliente]
  (->> cliente
       (lista-compras-realizadas)
       (group-by :categoria)
       (map calcula-gastos-por-categoria)))

(s/defn verifica-mes
  [compra :- m/Compra]
  (as (:data compra) :month-of-year))

(s/defn calcula-gastos-por-fatura :- clojure.lang.PersistentArrayMap
  [[mes, compra]]
  (let [valor-total (reduce + (map :valor compra))]
    {:Mes mes :Valor-da-fatura valor-total}))

(s/defn lista-compras-na-mesma-fatura
  [cliente :- m/Cliente]
  (->> cliente
       (lista-compras-realizadas)
       (sort-by :data)
       (reverse)
       (group-by verifica-mes)
       (map calcula-gastos-por-fatura)
       ))

(s/defn registra-compra :- m/Cliente
  [cliente :- m/Cliente, limite :- m/PosOuZero, compra :- m/Compra]
  (let [novo-limite (- limite (:valor compra))]
    (println "Compra realizada:" compra)
    (println "Novo limite:" novo-limite)
    (let [cliente-up-compras
          (update-in cliente [:cartao-de-credito :compras-realizadas] conj compra)]
      (update-in cliente-up-compras [:cartao-de-credito :dados-cartao :limite]
                 - (:valor compra)))))

(s/defn realizar-compra
  [cliente :- m/Cliente, compra :- m/Compra]
  (let [limite (busca-limite cliente)]
    (if (tem-limite? limite (:valor compra))
      (registra-compra cliente limite compra)
      (do (println "Item:" (:item compra) "| Valor de:"(:valor compra))
          (println "Compra não autorizada")))))

(s/defn realizar-varias-compras
  [cliente :- m/Cliente, compras :- [m/Compra]]
  (reduce realizar-compra cliente compras))

(println (lista-compras-por-categoria (cc.db/cliente)))

(->> (cc.db/cliente)
     (lista-compras-realizadas)
     (group-by :categoria)
     println)

 (calcula-gastos-por-categoria [1 [{:valor 1}, {:valor 1}], 2 [{:valor 55}, {:valor 1}]])
