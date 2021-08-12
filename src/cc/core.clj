(ns cc.core
  (:require [cc.db :as db]
            [cc.logic]
            [cc.model :as model]
            [datomic.api :as d]
            [java-time :refer :all]
            [schema.core :as s]
            [clojure.pprint :as pprint]
            ))

(println "Running... " (format "yyyy/MM/dd" (local-date)))
(defn restarta-banco []
  (db/apaga-banco!)
  (def conn (db/abre-conexao!))
  (pprint conn)
  (pprint (db/cria-esquema! conn))
  )

(restarta-banco)



(defn testa-schema []
  (def compra (model/nova-compra "AirMax" "Roupa" 850.50 "Authentic Feet"))
  (s/validate model/Compra compra)
  (def categoria (model/nova-categoria "Roupa"))
  (s/validate model/Categoria categoria))



(def airmax (model/nova-compra "AirMax" "Roupa" 650.50M "Authentic Feet"))
(def mizuno (model/nova-compra "Mizuno" "Roupa" 1000M "Authentic Feet"))
(def caloi10 (model/nova-compra "Caloi 10" "Lazer" 1850.50M "Decathlon"))

(db/adiciona-ou-altera-compras! conn [airmax mizuno caloi10])
(pprint (db/uma-compra (d/db conn) (:compra/id airmax)))

(def produtos (db/todas-as-compras-resumo (d/db conn)))
(pprint produtos)



(let [cliente {:cliente/nome "Guilherme"}]
  (d/transact conn [cliente]))

(pprint (db/todos-os-clientes (d/db conn)))






(db/adiciona-ou-altera-compras! conn [(assoc airmax :compra/estabelecimento "netshoes")])
(pprint (db/uma-compra (d/db conn) (:compra/id airmax)))

(db/adiciona-ou-altera-compras! conn [(assoc airmax :compra/valor 550.20M)])
(pprint (db/uma-compra (d/db conn) (:compra/id airmax)))


(db/adiciona-categorias! conn [(model/nova-categoria "Roupa")])
(pprint (db/todas-as-categorias (d/db conn)))

(defn relatorio
  [cliente]
  (println "------------- Dados do Cliente")
  (pprint (cc.logic/lista-dados-cliente cliente))
  (println)

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
          (db/cliente), [(m/nova-compra "City Tour Comp" "lazer" 1450 "Caloi"),
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
    (println "\n")))

;(relatorio (db/cliente))



