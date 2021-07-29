(ns cc.logic
  (:require [cc.db]
            [cc.logic]))

(defn lista-tudo
  [dados-do-cliente]
  (->> dados-do-cliente))

(defn lista-dados-cliente
  [dados-do-cliente]
  (->> dados-do-cliente
       (:dados-cliente)))

(defn lista-dados-cartoes-e-compras
  [dados-do-cliente]
  (->> dados-do-cliente
       :cartao-de-credito))

(defn lista-dados-cartoes
  [dados-do-cliente]
  (->> dados-do-cliente
        (lista-dados-cartoes-e-compras)
        :dados-cartao))

(defn lista-compras-realizadas
  [dados-do-cliente]
  (->> dados-do-cliente
       (lista-dados-cartoes-e-compras)
       :compras-realizadas))

(defn calcula-gastos-por-categoria
  [[categoria compra]]
  (let [valor-total (reduce + (map :valor compra))]
    (println "---->" valor-total "gastos com a categoria" categoria)))

(defn lista-compras-por-categoria
  [dados-do-cliente]
  (->> dados-do-cliente
       (lista-compras-realizadas)
       (group-by :categoria)
       (map calcula-gastos-por-categoria)))

(defn verifica-mes
  [compra]
  (as (:data compra) :month-of-year))

(defn calcula-gastos-por-fatura
  [[mes compra]]
  (let [valor-total (reduce + (map :valor compra))]
    (println "----> Valor da fatura do mes" mes "=" valor-total)))

(defn lista-compras-na-mesma-fatura
  [dados-do-cliente]
  (->> dados-do-cliente
       (lista-compras-realizadas)
       (sort-by :data)
       (reverse)
       (group-by verifica-mes)
       (map calcula-gastos-por-fatura)))

(defn buscar-compra-por-valor
  [dados-do-cliente]
  (let [compras (lista-compras-realizadas dados-do-cliente)]
    (println (filter (> (:valor compras) 500)))
    ))
