(ns logic-tut.sensor)

(def sensor-graph
  [; Graph of sensor hierarchies for Landsat and MODIS
   [:a1 :]
   
   [:b1 :mission-name "modis" ]
   
   [:b2 :mission-name "landsat" ]
   [:landsat :pixel-size 30 ]
   
   ; how do we dynamically alter attributes at runtime?
   [:landsat :path "nodata" ]
   [:landsat :row "nodata" ]
   [:landsat :year "nodata" ]
   [:landsat :doy "nodata" ]
   [:landsat :station "nodata" ]
   [:landsat :version "nodata" ]
   
   [:landsat-4 ]
   
   [:landsat-5 ]
   
   [:landsat-7 ]
   
   [:landsat-8 ]
   
   [:modis-aqua ]
   
   [:modis-terra-09A1 ]
   [:modis-terra-09GA ]
   [:modis-terra-09GQ ]
   [:modis-terra-09Q1 ]
   [:modis-terra-13A1 ]
   [:modis-terra-13A2 ]
   [:modis-terra-13A3 ]
   [:modis-terra-13Q1 ]
   
   [:modis-aqua-09A1 ]
   [:modis-aqua-09GA ]
   [:modis-aqua-09GQ ]
   [:modis-aqua-09Q1 ]
   [:modis-aqua-13A1 ]
   [:modis-aqua-13A2 ]
   [:modis-aqua-13A3 ]
   [:modis-aqua-13Q1 ]]