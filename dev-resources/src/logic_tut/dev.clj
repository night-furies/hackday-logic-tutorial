(ns ^{:doc
  "Dev module for hacking in the REPL.

  This is kept separate from the project namespaces for organization
  (and cleanliness) purposes."}
  logic-tut.dev
  (:require [clojure.tools.logging :as log]
            [clojure.tools.namespace.repl :as repl]
            [clojure.walk :refer [macroexpand-all]]
