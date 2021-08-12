(ns cc.model
  (:require
    [schema.core :as s]
    [java-time :as jt])
  (:use [clojure.pprint]
        [java-time :only [local-date]]))

(defn uuid [] (java.util.UUID/randomUUID))

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


(def Cliente
  "Esquema de um cliente"
  {})

(s/defn novo-cliente :- Cliente
  []
  {})



(def CartaoDeCredito
  "Esquema de um CC"
  {:cartao/numero   s/Str
   :cartao/cvv      s/Str
   :cartao/validade s/Str
   :cartao/limite   PosOuZero
   :cartao/cliente  Cliente})

(s/defn novo-cartao :- CartaoDeCredito
  [numero cvv validade limite cliente]
  {:cartao/numero   numero
   :cartao/cvv      cvv
   :cartao/validade validade
   :cartao/limite   limite
   :cartao/cliente  cliente})


(def Categoria
  "Categoria de um item comprado"
  {:categoria/id   java.util.UUID
   :categoria/nome s/Str})

(defn nova-categoria
  [nome]
  {:categoria/id   (uuid)
   :categoria/nome nome})


(def Compra
  "Esquema de uma compra"
  {:compra/id              java.util.UUID
   :compra/item            s/Str,
   :compra/categoria       s/Str,
   :compra/valor           PosOuZero,
   :compra/estabelecimento s/Str,
   :compra/data            s/Any})

(s/defn nova-compra :- Compra
  [item categoria valor estabelecimento]
  {:compra/id              (uuid)
   :compra/item            item
   :compra/categoria       categoria
   :compra/valor           valor
   :compra/estabelecimento estabelecimento
   :compra/data            "2021 08 11"})

(s/defn nova-compra-antes :- Compra
  [item :- s/Str,
   categoria :- s/Str,
   valor :- s/Num,
   estabelecimento :- s/Str]
  {:item            item,
   :categoria       categoria,
   :valor           valor,
   :estabelecimento estabelecimento,
   :data            (local-date)
   })



;(pprint (s/explain Compra))
;(pprint (nova-compra "Airmax", "roupa", 699, "Authentic feet", "12 25 29"))
;(pprint (s/validate PosOuZero 1 ))