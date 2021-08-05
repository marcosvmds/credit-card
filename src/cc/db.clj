(ns cc.db
  (:require [java-time]))


(def cliente1 {:cliente-id        1
               :dados-cliente     {:nome "Marcos", :cpf "44305312320", :email "marcosmazzei@email.com"}
               :cartao-de-credito {:dados-cartao       {:numero "1231231231", :cvv "123", :validade (java-time/local-date 2025 10 01 ), :limite 5000}
                                   :compras-realizadas [
                                                        {:item "Tenis", :categoria "roupa", :valor 399, :estabelecimento "Netshoes" :data (java-time/local-date 2021 06 9)}
                                                        {:item "Doritos", :categoria "comida", :valor 8.50, :estabelecimento "Extra" :data (java-time/local-date 2021 07 01)}
                                                        {:item "Coca", :categoria "comida", :valor 8.50, :estabelecimento "Extra" :data (java-time/local-date 2021 07 01)}
                                                        {:item "Trident", :categoria "comida", :valor 2.50, :estabelecimento "Padaria Copacabana" :data (java-time/local-date 2021 06 01)}
                                                        {:item "Adaptador HDMI", :categoria "eletronico", :valor 199, :estabelecimento "Kabum" :data (java-time/local-date 2021 06 05)}
                                                        {:item "Roteador", :categoria "eletronico", :valor 1500, :estabelecimento "Kabum" :data (java-time/local-date 2021 05 05)}
                                                        {:item "Conta de Luz", :categoria "contas", :valor 518.50, :estabelecimento "ENEL" :data (java-time/local-date 2021 07 02)}
                                                        ]}})
(def cliente2 {:dados-cliente     {:nome "Marcos", :cpf "44305312320", :email "marcosmazzei@email.com"}
        :cartao-de-credito {:dados-cartao {:numero "1231231231", :cvv "123", :validade (java-time/local-date 2025 10 01 ), :limite 5000}
                            :compras-realizadas [ ]}})

(defn cliente []
  cliente1)

