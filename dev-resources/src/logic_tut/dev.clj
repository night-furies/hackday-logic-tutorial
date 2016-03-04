(ns ^{:doc
  "Dev module for hacking in the REPL.

  This is kept separate from the project namespaces for organization
  (and cleanliness) purposes."}
  logic-tut.dev
  (:require [clojure.tools.logging :as log]
            [clojure.tools.namespace.repl :as repl]
            [clojure.walk :refer [macroexpand-all]]
            [logic-tut.core :as tut]
            [logic-tut.book :as book]))

(defn refresh
  ([]
    (repl/refresh))
  ([& args]
    (apply #'repl/refresh args)))

;;; Aliases

(def reload #'refresh)
