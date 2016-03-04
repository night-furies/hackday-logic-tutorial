(ns logic-tut.sensor
  (:require [clojure.core.logic :as logic]))

(def sensor-graph
  [; Graph of sensor hierarchies for Landsat and MODIS
   [:a1 :type :BaseSensor]
   [:a1 :mission :b1]
      
   [:b1 :type :Mission ]
   [:b1 :mission-name "landsat" ]
   [:b1 :satellite :c1 ]
   [:b1 :satellite :c2 ]
   [:b1 :satellite :c3 ]
   [:b1 :satellite :c4 ]
   
   [:c1 :type :Satellite ]
   [:c1 :id 4 ]
   [:c1 :sensor :d1 ]
   
   [:c2 :type :Satellite ]
   [:c2 :id 5 ]
   [:c1 :sensor :d1 ]
   
   [:c3 :type :Satellite ]
   [:c3 :id 7 ]
   [:c1 :sensor :d2 ]
   
   [:c4 :type :Satellite ]
   [:c4 :id 8 ]
   [:c1 :sensor :d3 ]
   [:c1 :sensor :d4 ]
   
   [:d1 :type :Sensor ]
   [:d1 :id "tm" ]
   [:d1 :name "Thematic Mapper" ]
   
   [:d2 :type :Sensor ]
   [:d2 :id "etm" ]
   [:d2 :name "Enhanced Thematic Mapper Plus" ]
   
   [:d3 :type :Sensor ]
   [:d3 :id "oli" ]
   [:d3 :name "Operational Land Imager" ]
   
   [:d4 :type :Sensor ]
   [:d4 :id "tirs" ]
   [:d4 :name "Thermal Infrared Sensor" ]
   ])
  
  (defn find-by-id
   [node-type]
   (logic/run* [q]
    (logic/membero [q :type node-type] sensor-graph)))
 
    
   
    
