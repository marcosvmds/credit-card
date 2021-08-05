(ns cc.core
  (:require [cc.db]
            [cc.logic]
            [cc.model :as m]
            [java-time :refer :all]
            [clojure.pprint :as pprint]))

(println "Running... " (format "yyyy/MM/dd" (local-date)))

(defn relatorio
  [cliente]
  (println "------------- Dados do Cliente")
  (pprint (cc.logic/lista-dados-cliente cliente))

  (println "------- Dados do Cartao")
  (pprint (cc.logic/lista-dados-cartao cliente))
  (println "\n")

  (print "------- Compras realizadas")
  (pprint/print-table (cc.logic/lista-compras-realizadas cliente))
  (println "\n")

  (print "------- Gastos por categoria")
  (pprint/print-table (cc.logic/lista-compras-por-categoria cliente))
  (println "\n")

  (println "------- Realizando compras")
  (let [cliente-pos-compras (cc.logic/realizar-varias-compras
          (cc.db/cliente), [(m/nova-compra "Bike" "lazer" 1450 "Caloi"),
                            (m/nova-compra "Ingresso" "lazer" 200 "Hopi Hari"),
                            (m/nova-compra "Kinder ovo" "comida" 500 "Guanabara")])]
    (println)

    (print "------- Pós compras realizadas")
    (pprint/print-table (cc.logic/lista-compras-realizadas cliente-pos-compras))
    (println "\n")

    (print "------- Gastos por categoria pós compras")
    (pprint/print-table (cc.logic/lista-compras-por-categoria cliente-pos-compras))
    (println "\n")

    (print "------- Gastos por fatura")
    (pprint/print-table (cc.logic/lista-compras-na-mesma-fatura cliente-pos-compras))
    (println "\n")
    ))

(relatorio (cc.db/cliente))



