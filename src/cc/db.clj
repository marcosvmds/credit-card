(ns cc.db
  (:require [datomic.api :as d]
            [schema.core :as s]
            [cc.model :as model]
            [clojure.walk :as walk])
  [:use java-time :only [local-date]])

(s/set-fn-validation! true)

(def db-uri "datomic:dev://localhost:4334/cc")

(defn abre-conexao! []
  (println "Criando conexão db")
  (d/create-database db-uri)
  (d/connect db-uri))

(defn apaga-banco! []
  (d/delete-database db-uri))


(def schema [{:db/ident       :cliente/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity}
             {:db/ident       :cliente/nome
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "Nome completo do cliente"}
             {:db/ident       :cliente/email
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident       :cliente/cpf
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}


             {:db/ident       :cartao/numero
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity}
             {:db/ident       :cartao/cvv
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident       :cartao/validade
              :db/valueType   :db.type/instant
              :db/cardinality :db.cardinality/one}
             {:db/ident       :cartao/limite
              :db/valueType   :db.type/bigdec
              :db/cardinality :db.cardinality/one}
             {:db/ident       :cartao/cliente
              :db/valueType   :db.type/ref
              :db/cardinality :db.cardinality/one}


             {:db/ident       :compra/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity}
             {:db/ident       :compra/item
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "Nome do item comprado"}
             {:db/ident       :compra/valor
              :db/valueType   :db.type/bigdec
              :db/cardinality :db.cardinality/one}
             {:db/ident       :compra/estabelecimento
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident       :compra/data
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident       :compra/cartao
              :db/valueType   :db.type/ref
              :db/cardinality :db.cardinality/one}
             {:db/ident :compra/categoria
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}



             {:db/ident       :categoria/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity}
             {:db/ident       :categoria/nome
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident       :categoria/compra
              :db/valueType   :db.type/ref
              :db/cardinality :db.cardinality/one}])


(defn dissoc-db-id [entidade]
  (if (map? entidade)
    (dissoc entidade :db/id)
    entidade))

(defn datomic-para-entidade [entidades]
  (walk/prewalk dissoc-db-id entidades))

(defn adiciona-ou-altera-compras!
  ([conn compras]
   (d/transact conn compras))
  )


(s/defn adiciona-categorias! [conn, categorias :- [model/Categoria]]
  (d/transact conn categorias))

(defn cria-esquema! [conn]
  (d/transact conn schema))



(s/defn uma-compra :- (s/maybe model/Compra)
  [db, compra-id :- java.util.UUID]
  (let [resultado (d/pull db '[* {:compra/categoria [*]}] [:compra/id compra-id])
        compra (datomic-para-entidade resultado)]
    (if (:compra/id compra)
      comparator
      nil)))

(defn todos-os-clientes [db]
  (d/q '[:find ?entidade
         :where [?entidade :cliente/nome]] db))


(s/defn todas-as-compras :- [model/Compra]
  [db]
  (datomic-para-entidade
    (d/q '[:find [(pull ?compra [*]) ...]
           :where [?compra :compra/id]]
         db)))

(defn todas-as-compras-resumo
  [db]
  (datomic-para-entidade
    (d/q '[:find [(pull ?compra [:compra/item :compra/valor])...]
           :keys item, preco
           :where [?compra :compra/id]]
         db)))


(s/defn todas-as-categorias :- [model/Categoria] [db]
  (datomic-para-entidade
    (d/q '[:find [(pull ?categoria [*]) ...]
           :where [?categoria :categoria/id]]
         db)))



(defn todos-os-slugs [db]
  (d/q '[:find ?slug
         :where [_ :produto/slug ?slug]] db))

;usando keys pra vir em formato de maps
(defn todos-os-produtos-por-preco [db]
  (d/q '[:find ?nome, ?preco
         :keys produto/nome, produto/preco
         :where [?produto :produto/preco ?preco]
         [produto :produto/nome ?nome]] db))

;outro jeito de trazer os atributos bonitin, usando pull
;(o * traz tudo, se não era pra especificar)
(s/defn todos-as-compras :- [model/Compra]
  [db]
  (datomic-para-entidade
    (d/q '[:find [(pull ?entidade [* {:compra/categoria [*]}]) ...]
           :where [?entidade :compra/item]] db)))

(defn um-cliente [db cliente-id]
  (d/pull db '[*] cliente-id))
















(def cliente1 {:cliente-id        1
               :dados-cliente     {:nome "Marcos", :cpf "44305312320", :email "marcosmazzei@email.com"}
               :cartao-de-credito {:dados-cartao       {:numero "1231231231", :cvv "123", :validade (local-date 2025 10 01), :limite 5000}
                                   :compras-realizadas [
                                                        {:item "Tenis", :categoria "roupa", :valor 399, :estabelecimento "Netshoes" :data (local-date 2021 06 9)}
                                                        {:item "Doritos", :categoria "comida", :valor 8.50, :estabelecimento "Extra" :data (local-date 2021 07 01)}
                                                        {:item "Coca", :categoria "comida", :valor 8.50, :estabelecimento "Extra" :data (local-date 2021 07 01)}
                                                        {:item "Trident", :categoria "comida", :valor 2.50, :estabelecimento "Padaria Copacabana" :data (local-date 2021 06 01)}
                                                        {:item "Adaptador HDMI", :categoria "eletronico", :valor 199, :estabelecimento "Kabum" :data (local-date 2021 06 05)}
                                                        {:item "Roteador", :categoria "eletronico", :valor 1500, :estabelecimento "Kabum" :data (local-date 2021 05 05)}
                                                        {:item "Conta de Luz", :categoria "contas", :valor 518.50, :estabelecimento "ENEL" :data (local-date 2021 07 02)}
                                                        ]}})





(def mock-com-limite {:cliente-id        99
                      :dados-cliente     {:nome "Marcos", :cpf "44305312320", :email "marcosmazzei@email.com"}
                      :cartao-de-credito {:dados-cartao       {:numero "1231231231", :cvv "123", :validade (local-date 2025 10 01), :limite 1}
                                          :compras-realizadas [{:item "item teste", :categoria "categoria teste", :valor 1, :estabelecimento "categoria teste" :data "data teste"}
                                                               ]}})


(def mock-com-limite-sem-compras {:cliente-id        98
                                  :dados-cliente     {:nome "Marcos", :cpf "44305312320", :email "marcosmazzei@email.com"}
                                  :cartao-de-credito {:dados-cartao       {:numero "1231231231", :cvv "123", :validade (local-date 2025 10 01), :limite 1}
                                                      :compras-realizadas []}})


(defn cliente []
  cliente1)

(defn cliente-com-limite []
  mock-com-limite)

(defn cliente-com-limite-sem-compras []
  mock-com-limite-sem-compras)