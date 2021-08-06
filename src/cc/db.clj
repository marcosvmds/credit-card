(ns cc.db
  (:require [java-time :refer :all]))


(def cliente1 {:cliente-id        1
               :dados-cliente     {:nome "Marcos", :cpf "44305312320", :email "marcosmazzei@email.com"}
               :cartao-de-credito {:dados-cartao       {:numero "1231231231", :cvv "123", :validade (local-date 2025 10 01 ), :limite 5000}
                                   :compras-realizadas [
                                                        {:item "Tenis", :categoria "roupa", :valor 399, :estabelecimento "Netshoes" :data (local-date 2021 06 9)}
                                                        {:item "Doritos", :categoria "comida", :valor 8.50, :estabelecimento "Extra" :data (local-date 2021 07 01)}
                                                        {:item "Coca", :categoria "comida", :valor 8.50, :estabelecimento "Extra" :data (local-date 2021 07 01)}
                                                        {:item "Trident", :categoria "comida", :valor 2.50, :estabelecimento "Padaria Copacabana" :data (local-date 2021 06 01)}
                                                        {:item "Adaptador HDMI", :categoria "eletronico", :valor 199, :estabelecimento "Kabum" :data (local-date 2021 06 05)}
                                                        {:item "Roteador", :categoria "eletronico", :valor 1500, :estabelecimento "Kabum" :data (local-date 2021 05 05)}
                                                        {:item "Conta de Luz", :categoria "contas", :valor 518.50, :estabelecimento "ENEL" :data (local-date 2021 07 02)}
                                                        ]}})

(def mock-com-limite {:cliente-id 99
               :dados-cliente     {:nome "Marcos", :cpf "44305312320", :email "marcosmazzei@email.com"}
               :cartao-de-credito {:dados-cartao       {:numero "1231231231", :cvv "123", :validade (local-date 2025 10 01 ), :limite 1}
                                   :compras-realizadas [{:item "item teste", :categoria "categoria teste", :valor 1, :estabelecimento "categoria teste" :data "data teste"}
                                                        ]}})


(def mock-com-limite-sem-compras {:cliente-id 98
                      :dados-cliente     {:nome "Marcos", :cpf "44305312320", :email "marcosmazzei@email.com"}
                      :cartao-de-credito {:dados-cartao       {:numero "1231231231", :cvv "123", :validade (local-date 2025 10 01 ), :limite 1}
                                          :compras-realizadas []}})


(defn cliente []
  cliente1)

(defn cliente-com-limite []
  mock-com-limite)

(defn cliente-com-limite-sem-compras []
  mock-com-limite-sem-compras)