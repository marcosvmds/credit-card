(ns cc.db)

(def cliente1 {:dados-cliente     {:nome "x", :cpf "z", :email "z"}
               :cartao-de-credito { :dados-cartao [:numero "x", :cvv "y", :validade "xxxx/yy/zz"]
                                   :compras-realizadas [{:item "x", :categoria "y", :valor 1, :estabelecimento "xyz"}]}})

(def cliente2 {:dados-cliente     {:nome "x", :cpf "z", :email "z"}
               :cartao-de-credito {:dados-cartao [:numero "x", :cvv "y", :validade "xxxx/yy/zz"]
                                   :compras-realizadas [{:item "x", :categoria "y", :valor 1, :estabelecimento "xyz"}]}})

(defn clientes []
  [cliente1, cliente2])

