(ns cc.logic
  (:require [cc.db]
            [cc.logic]))

(defn lista-clientes-completo
  [todos-os-clientes]
  (println todos-os-clientes)
  )

(defn lista-dados-clientes
  [todos-os-clientes]
  (->> todos-os-clientes
       (map :dados-cliente)
       println))

(defn lista-dados-cartoes1
  [todos-os-clientes]
  (->> todos-os-clientes
       (map ,,, :cartao-de-credito)
       (select-keys ,,, [:numero :cvv :validade])
       println))


(defn lista-dados-cartoes
  [todos-os-clientes]
  (->> todos-os-clientes
       (map :cartão-de-credito)
       (:dados-cartao)
       println))