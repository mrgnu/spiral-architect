(ns spiral-architect.shape
  (:use spiral-architect.architect))

(defn make-cylinder [height radius lines loops]
  (create-spiral height radius lines loops map-i map-1))

(defn make-cone [height radius lines loops]
  (create-spiral height radius lines loops map-i map-i))

(defn make-sphere [height radius lines loops]
  (create-spiral height radius lines loops map-cos-inv map-sin))

(defn make-pendant [height radius lines loops]
  (create-spiral height radius lines loops map-tan map-bell))

(defn merge-spirals [s1 s2 factor]
  (map #(mix-vertices %1 %2 factor) s1 s2))

(defn flip-spiral [spiral]
  (map #(vector (first %) (- 0. (second %)) (nth % 2)) spiral))
