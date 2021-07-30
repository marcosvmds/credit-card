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

(println "------------- Dados do Cartao")
(printar/pprint (cc.logic/lista-dados-cartoes (cc.db/cliente)))
(println "\n")

(println "------------- Compras realizadas")
(printar/pprint (cc.logic/lista-compras-realizadas (cc.db/cliente)))
(println "\n")

(println "------------- Gastos por categoria")
(printar/pprint (cc.logic/lista-compras-por-categoria (cc.db/cliente)))
(println "\n")

(println "------------- Gastos por fatura")
(printar/pprint (cc.logic/lista-compras-na-mesma-fatura (cc.db/cliente)))
(println "\n")



