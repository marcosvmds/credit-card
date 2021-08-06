(ns cc.model
  (:require [cc.db]
            [schema.core :as s]
            [java-time :as jt])
  (:use [clojure.pprint]))



(def PosInt (s/pred pos-int?))

(def PosOuZero (s/pred #(>= % 0)))

(def DadosDoCliente
  "Esquema de dados do cliente"
  {:nome  s/Str,
   :cpf   s/Str,
   :email s/Str})

(def DadosDoCartao
  "Esquema das informacoes de um CC"
  {:numero   s/Str,
   :cvv      s/Str,
   :validade s/Any,
   :limite   PosOuZero})

(def Compra
  "Esquema de uma compra"
  {:item            s/Str,
   :categoria       s/Str,
   :valor           PosOuZero,
   :estabelecimento s/Str,
   :data            s/Any})

(def CartaoDeCredito
  "Esquema de um CC"
  {:dados-cartao       DadosDoCartao
   :compras-realizadas [Compra]
   })

(def Cliente
  "Esquema de um cliente"
  {:cliente-id        PosInt,
   :dados-cliente     DadosDoCliente,
   :cartao-de-credito CartaoDeCredito})

(s/defn novo-cliente :- Cliente
  [id                 :- PosInt,
   dados              :- DadosDoCliente,
   cartao             :- CartaoDeCredito]
  {:cliente-id        id,
   :dados-cliente     dados,
   :cartao-de-credito cartao})

(s/defn nova-compra :- Compra
  [item            :- s/Str,
   categoria       :- s/Str,
   valor           :- s/Num,
   estabelecimento :- s/Str]
  {:item            item,
   :categoria       categoria,
   :valor           valor,
   :estabelecimento estabelecimento,
   :data            (jt/local-date)
   })

(s/defn nova-compra-teste-valor :- Compra
  [valor           :- s/Num]
  {:item            "item teste",
   :categoria       "categoria teste",
   :valor           valor,
   :estabelecimento "estabelecimento teste",
   :data            (jt/local-date)})

;(pprint (s/explain Compra))
(pprint (s/validate Compra {:item "Airmax", :categoria "roupa", :valor 699, :estabelecimento "Authentic feet", :data "12 25 29"}))
;(pprint (nova-compra "Airmax", "roupa", 699, "Authentic feet", "12 25 29"))
;(pprint (s/validate PosOuZero 1 ))