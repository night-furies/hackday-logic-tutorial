(ns logic-tut.farm
   (:refer-clojure :exclude [==])
   (:require [clojure.core.logic :refer :all :as logic]
             [clojure.core.logic.pldb :as pldb]))

(pldb/db-rel plant  p)
(pldb/db-rel animal a)
(pldb/db-rel eats   a m)

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
   [eats 'rodent 'rodent]
   [eats 'porcine 'corn]
   [eats 'porcine 'bovine]
   [eats 'porcine 'farmer]
   [eats 'fly-trap 'insect]))

(defn meat-eater [it is]
  (fresh [prey]
    (animal it)
    (animal prey)
    (eats it prey)
    (== is true)))

(defn plant-eater [it is]
  (fresh [meal]
    (animal it)
    (plant meal)
    (eats it meal)
    (== is true)))

(defn cannibal [it is]
  (conda
   [(eats it it) (animal it) (== is true)]
   [(nafc eats it it) (== is false)]))

(defn carnivore? [critter db]
  (pldb/with-db db
    (run* [answer]
      (fresh [it]
        (conda [(and* [(nafc plant-eater critter answer)
                       (meat-eater critter answer)])]
               [(== answer false)])))))

(defn herbivore? [critter db]
  (pldb/with-db db
    (run* [answer]
      (fresh [it]
        (conda [(and* [(nafc meat-eater critter answer)
                       (plant-eater critter answer)])]
               [(== answer false)])))))

(defn omnivore? [critter db]
  (pldb/with-db db
    (run* [answer]
      (fresh [it]
        (conda [(and* [(meat-eater critter answer)
                       (plant-eater critter answer)])]
               [(== answer false)])))))

(defn cannibal? [critter db]
  (pldb/with-db db
     (run* [answer]
       (fresh [a]
         (== a critter)
         (cannibal a answer)))))

;; What are all the things that a pig will eat?
(pldb/with-db big-red-ranch
  (distinct (run* [eater meal]
    (all
      (== eater 'porcine)
      (conde [(plant meal) (eats eater meal)]
             [(animal meal) (eats eater meal)])))))

(pldb/with-db big-red-ranch
  (run* [q]
    (fresh [a]
      (== q a)
      (eats a a))))

;; Are there any plants that eat animals?
(pldb/with-db big-red-ranch
  (run* [q]
    (fresh [it prey]
      (plant it)
      (animal prey)
      (eats it prey)
      (== q it))))

;; What animals eat plants?
(pldb/with-db big-red-ranch
  (run* [q]
    (fresh [it meal]
      (animal it)
      (plant meal)
      (eats it meal)
      (== q [it meal]))))

;; What eats grass?
(pldb/with-db big-red-ranch
  (run* [it]
    (fresh [meal]
      (eats it meal)
      (== meal 'grass))))

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
;; ...but not horses. Horses rock!
(cannibal? 'porcine big-red-ranch)
(cannibal? 'equine big-red-ranch)

;; ...What aren't cannibals?
(pldb/with-db big-red-ranch
  (distinct
   (run* [answer]
     (animal answer)
     (cannibal answer false))))

;; ...Do any plants eat things and what do they eat??
(pldb/with-db big-red-ranch
  (distinct
   (run* [answer prey]
     (plant answer)
     (eats answer prey))))
