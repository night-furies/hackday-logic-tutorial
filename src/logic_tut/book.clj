(ns logic-tut.book
  (:require [clojure.core.logic :as logic]
            [clojure.tools.logging :as log]))

(def movie-graph
  [;; The "NewMarket Films" studio
   [:a1 :type :FilmStudio]
   [:a1 :name "Newmarket Films"]
   [:a1 :filmsCollection :a2]

   ;; Collection of films made by Newmarket Films
   [:a2 :type :FilmCollection]
   [:a2 :film :a3]
   [:a2 :film :a6]

   ;; The movie "Memento"
   [:a3 :type :Film]
   [:a3 :name "Memento"]
   [:a3 :cast :a4]

   ;; Connects the film to its cast (actors/director/producer etc.)
   [:a4 :type :FilmCast]
   [:a4 :director :a5]

   ;; The director of "Memento"
   [:a5 :type :Person]
   [:a5 :name "Christopher Nolan"]

   ;; The movie "The Usual Suspects"
   [:a6 :type :Film]
   [:a6 :filmName "The Usual Suspects"]
   [:a6 :cast :a7]

   ;; Connects the film to its cast (actors/director/producer etc.)
   [:a7 :type :FilmCast]
   [:a7 :director :a8]

   ;; The director of "The Usual Suspects"
   [:a8 :type :Person]
   [:a8 :name "Bryan Singer"]])

(defn make-relations
  [graph studio-name director-name]
  (logic/fresh [studio film-coll film cast director]
    ;; Relate the original studio-name to a film collection - from the
    ;; core.logic docs:
    ;;
    ;; "When you unify two lvars, the operation constrains each lvar
    ;;  to have the same set of possible values. A mental shortcut is
    ;;  to consider unification to be the intersection of the two sets
    ;;  lvar values."
    ;;
    ;; This means that below, we're saying:
    ;;  *
    (logic/membero [studio :name studio-name] graph)
    (logic/membero [studio :type :FilmStudio] graph)
    (logic/membero [studio :filmsCollection film-coll] graph)

    ;; Relate any film collections to their individual films
    (logic/membero [film-coll :type :FilmCollection] graph)
    (logic/membero [film-coll :film film] graph)

    ;; Then from film to cast members
    (logic/membero [film :type :Film] graph)
    (logic/membero [film :cast cast] graph)

    ;; Grounding to cast members of type :director
    (logic/membero [cast :type :FilmCast] graph)
    (logic/membero [cast :director director] graph)

    ;; Finally, attach to the director-name
    (logic/membero [director :type :Person] graph)
    (logic/membero [director :name director-name] graph)))

(defn directors-at
  "Find all of the directors that have directed at a given studio."
  [graph studio-name]
  (logic/run* [director-name]
    (make-relations graph studio-name director-name)))

