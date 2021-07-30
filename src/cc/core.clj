(ns cc.core
  (:require [cc.db]
            [cc.logic]
            [clojure.pprint :as printar]))
(use 'java-time)



(println "Running..." (local-date-time))
(println "\n")

(println "------------- Dados do Cliente")
(printar/pprint (cc.logic/lista-dados-cliente (cc.db/cliente)))
(println "\n")

;(println "------------- Cartao e Compras")
;(printar/pprint (cc.logic/lista-dados-cartoes-e-compras (cc.db/cliente)))
;(println "\n")

(print "------- Dados do Cartao")
(printar/pprint (cc.logic/lista-dados-cartoes (cc.db/cliente)))
(println "\n")

(print "------- Compras realizadas")
(printar/print-table (cc.logic/lista-compras-realizadas (cc.db/cliente)))
(println "\n")

(print "------- Gastos por categoria")
(printar/print-table (cc.logic/lista-compras-por-categoria (cc.db/cliente)))
(println "\n")

(print "------- Gastos por fatura")
(printar/print-table (cc.logic/lista-compras-na-mesma-fatura (cc.db/cliente)))
(println "\n")



