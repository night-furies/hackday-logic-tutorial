(defproject logic-tut "0.1.0-SNAPSHOT"
  :description "HackDay Project: Logic and Relations Programming with Clojure's core.logic."
  :url "https://github.com/oubiwann/hackday-logic-tutorial"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.logic "0.8.10"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.clojure/tools.namespace "0.2.11"]]
  :repl-options {:init-ns logic-tut.dev
                 :welcome (println "If you don't chew Big Red, then FUCK YOU!")}
  :profiles {
    :dev {:source-paths ["dev-resources/src"]}})
