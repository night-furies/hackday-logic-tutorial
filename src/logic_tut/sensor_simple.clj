(ns logic-tut.sensor-simple
  (:require [clojure.core.logic :as logic]
            [clojure.tools.logging :as log]))

(def sat-graph
  [; Satellite missions
   [:id1 :type :mission]
   [:id1 :name "Landsat"]
   [:id1 :satellite-collection :id2]
   ; Collection of stallites
   [:id2 :type :satellite-collection]
   [:id2 :satellite :id3]
   [:id2 :satellite :id4]
   [:id2 :satellite :id5]
   ; The Landsat 4 satellite
   [:id3 :type :satellite]
   [:id3 :name "Landsat 4"]
   [:id3 :sensor :id6]
   ; The Landsat 5 satellite
   [:id4 :type :satellite]
   [:id4 :name "Landsat 5"]
   [:id4 :sensor :id6]
   ; The Landsat 7 satellite
   [:id5 :type :satellite]
   [:id5 :name "Landsat 7"]
   [:id5 :sensor :id7]
   ; The Landsat 4 & 5 sensors
   [:id6 :type :sensor]
   [:id6 :name "TM"]
   ; The Landsat 7 sensors
   [:id7 :type :sensor]
   [:id7 :name "ETM"]])

;; Given the scene id (Mission, sensor, satellite number,
;; wrs-2 -- world-wide reference system, i.e. path/row --, datestamp,
;; ground station and product vision, give me X
;;
;; where 'X' could be anything like:
;; * "what's the pixel size of band 2?"
;; * "what output products are available for this scene id?"
;;
;; In our simple example, we can do something like this:
;; * "what missions support TM?"

(defn make-satellite-relations
  [graph mission-name satellite-name]
  (logic/fresh [sat-mission sat-coll sat]
    ;; contraints on mission
    (logic/membero [sat-mission :name mission-name] graph)
    (logic/membero [sat-mission :type :mission] graph)
    (logic/membero [sat-mission :satellite-collection sat-coll] graph)
    ;; contraints on the collection
    (logic/membero [sat-coll :type :satellite-collection] graph)
    (logic/membero [sat-coll :satellite sat] graph)
    ;; constraints on the satellite
    (logic/membero [sat :type :satellite] graph)
    (logic/membero [sat :name satellite-name] graph)))

(defn satellites-for-mission
  "Find all of the sensors for the given mission."
  [graph mission-name]
  (logic/run* [satellite-name]
    (make-satellite-relations graph mission-name satellite-name)))

(defn make-mission-sensor-relations
  [graph mission-name sensor-name]
  (logic/fresh [sat-mission sat-coll sat sensor]
    ;; contraints on mission
    (logic/membero [sat-mission :name mission-name] graph)
    (logic/membero [sat-mission :type :mission] graph)
    (logic/membero [sat-mission :satellite-collection sat-coll] graph)
    ;; contraints on the collection
    (logic/membero [sat-coll :type :satellite-collection] graph)
    (logic/membero [sat-coll :satellite sat] graph)
    ;; constraints on the satellite
    (logic/membero [sat :type :satellite] graph)
    (logic/membero [sat :sensor sensor] graph)
    ;; constraints on the sensors
    (logic/membero [sensor :type :sensor] graph)
    (logic/membero [sensor :name sensor-name] graph)))

(defn sensors-for-mission
  "Find all of the sensors for the given mission."
  [graph mission-name]
  (logic/run* [sensor-name]
    (make-mission-sensor-relations graph mission-name sensor-name)))

(defn make-satellite-sensor-relations
  [graph mission-name satellite-name sensor-name]
  (logic/fresh [sat-mission sat-coll sat sensor]
    ;; contraints on mission
    (logic/membero [sat-mission :name mission-name] graph)
    (logic/membero [sat-mission :type :mission] graph)
    (logic/membero [sat-mission :satellite-collection sat-coll] graph)
    ;; contraints on the collection
    (logic/membero [sat-coll :type :satellite-collection] graph)
    (logic/membero [sat-coll :satellite sat] graph)
    ;; constraints on the satellite
    (logic/membero [sat :type :satellite] graph)
    (logic/membero [sat :name satellite-name] graph)
    (logic/membero [sat :sensor sensor] graph)
    ;; constraints on the sensors
    (logic/membero [sensor :type :sensor] graph)
    (logic/membero [sensor :name sensor-name] graph)))

(defn sensors-for-satellite
  "Find all of the sensors for the given mission."
  [graph mission-name satellite-name]
  (logic/run* [sensor-name]
    (make-satellite-sensor-relations
      graph mission-name satellite-name sensor-name)))
