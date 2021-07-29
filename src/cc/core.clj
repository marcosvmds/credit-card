(ns cc.core
  (:require [cc.db]
            [cc.logic]))

(defn printar
  [lista]
  (print (map println lista)))

(println "Running..." (local-date-time))
(println "\n")

(println "------------- Tudo")
(printar (cc.logic/lista-tudo (cc.db/cliente)))
(println "\n")

(println "------------- Dados do Cliente")
(printar (cc.logic/lista-dados-cliente (cc.db/cliente)))
(println "\n")

(println "------------- Cartao e Compras")
(printar (cc.logic/lista-dados-cartoes-e-compras (cc.db/cliente)))
(println "\n")

(println "------------- Dados do Cartao")
(printar (cc.logic/lista-dados-cartoes (cc.db/cliente)))
(println "\n")

(println "------------- Compras realizadas")
(printar (cc.logic/lista-compras-realizadas (cc.db/cliente)))
(println "\n")

(println "------------- Gastos por categoria")
(println (cc.logic/lista-compras-por-categoria (cc.db/cliente)))
(println "\n")

(println "------------- Gastos por fatura")
(println (cc.logic/lista-compras-na-mesma-fatura (cc.db/cliente)))
(println "\n")



