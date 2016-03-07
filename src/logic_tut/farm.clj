(ns logic-tut.farm
   (:refer-clojure :exclude [==])
   (:require [clojure.core.logic :refer :all :as logic]
             [clojure.core.logic.pldb :as pldb]))

(pldb/db-rel plant  p)
(pldb/db-rel animal a)
(pldb/db-rel eats   a m) ; animal / meal

(def big-red-ranch
  (pldb/db
   [plant 'oat]
   [plant 'grass]
   [plant 'potatoe]
   [plant 'corn]
   [plant 'fly-trap]
   [animal 'bovine]
   [animal 'equine]
   [animal 'feline]
   [animal 'rodent]
   [animal 'canine]
   [animal 'porcine]
   [animal 'farmer]
   [animal 'insect]
   [eats 'porcine 'porcine]
   [eats 'farmer 'bovine]
   [eats 'farmer 'porcine]
   [eats 'farmer 'corn]
   [eats 'farmer 'potatoe]
   [eats 'canine 'feline]
   [eats 'bovine 'grass]
   [eats 'equine 'oat]
   [eats 'equine 'grass]
   [eats 'feline 'rodent]
   [eats 'rodent 'corn]
   [eats 'rodent 'potatoe]
   [eats 'porcine 'corn]
   [eats 'porcine 'bovine]
   [eats 'porcine 'farmer]
   [eats 'fly-trap 'insect]))


;; What are all the things that a pig will eat?
(pldb/with-db big-red-ranch
  (distinct (run* [eater meal]
    (all
      (== eater 'porcine)
      (conde [(plant meal) (eats eater meal)]
             [(animal meal) (eats eater meal)])))))

(defn meat-eater [it]
  (fresh [prey]
    (animal it)
    (animal prey)
    (eats it prey)))

(defn plant-eater [it]
  (fresh [meal]
    (animal it)
    (plant meal)
    (eats it meal)))

(defn cannibal [it is]
  (conda
   [(eats it it) (animal it) (== is true)]
   [(nafc eats it it) (== is false)]))

(defn carnivore? [critter db]
  (pldb/with-db db
    (run* [answer]
      (fresh [it]
        (conda [(and* [(nafc plant-eater critter)
                       (meat-eater critter)
                       (== answer true)])]
               [(== answer false)])))))

(defn herbivore? [critter db]
  (pldb/with-db db
    (run* [answer]
      (fresh [it]
        (conda [(and* [(nafc meat-eater critter)
                       (plant-eater critter)
                       (== answer true)])]
               [(== answer false)])))))

(defn omnivore? [critter db]
  (pldb/with-db db
    (run* [answer]
      (fresh [it]
        (conda [(and* [(meat-eater critter)
                       (plant-eater critter)
                       (== answer true)])]
               [(== answer false)])))))

(defn cannibal? [critter db]
  (pldb/with-db db
     (run* [answer]
       (fresh [a]
         (== a critter)
         (cannibal a answer)))))

;; pigs will eat anything.
(carnivore? 'porcine big-red-ranch)
(herbivore? 'porcine big-red-ranch)
(omnivore?  'porcine big-red-ranch)

;; cats (on this farm) only eat mice.
(carnivore? 'feline big-red-ranch)
(herbivore? 'feline big-red-ranch)
(omnivore?  'feline big-red-ranch)

;; horses only eat plants.
(carnivore? 'equine big-red-ranch)
(herbivore? 'equine big-red-ranch)
(omnivore?  'equine big-red-ranch)

;; oh, yeah. pigs will eat ANYTHING.
(cannibal? 'porcine big-red-ranch)
(cannibal? 'equine big-red-ranch)
