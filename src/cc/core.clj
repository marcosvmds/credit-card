(ns cc.core
  (:require [cc.db]
            [cc.logic]))

(println "Running...")

(println "Tudo")
(cc.logic/lista-clientes-completo (cc.db/clientes))
(println "Clientes")
(cc.logic/lista-dados-clientes (cc.db/clientes))
(println "Cartoes")
(cc.logic/lista-dados-cartoes1 (cc.db/clientes))

(println "Cartoes2")
(cc.logic/lista-dados-cartoes (cc.db/clientes))


