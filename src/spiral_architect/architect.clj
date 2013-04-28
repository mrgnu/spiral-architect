(ns spiral-architect.architect)


;;; these mapping functions are supposed to take a value in [0; 1] as
;;; input and produce a normalized value as output

;;; no mapping, returns 1
(defn map-1 [k]
  1.)

;;; map to input
(defn map-i [k]
  k)

;;; maps k to sin in range [0; PI]
(defn map-sin [k]
  (Math/sin (* k Math/PI)))

;;; maps k to cos in range [0; PI]
(defn map-cos [k]
  (Math/cos (* k Math/PI)))

;;; maps k to cos in range [PI; 0]
(defn map-cos-inv [k]
  (Math/cos (* (- 1. k) Math/PI)))

;;; maps k to tan in range [-1; 1]
(defn map-tan [k]
  (Math/tan (- (* k 2) 1)))

;;; maps k to a bell-function
(defn map-bell [k]
  (/ (- 1 (Math/cos (* k Math/PI 2))) 2))


;;; interpolate between vectors, factor is expected to be in [0; 1]
(defn mix-vertices [v1 v2 factor]
  (map #(+ (* %1 (- 1. factor)) (* %2 factor)) v1 v2))


;;; creates a spiral of the provided height and radius, consisting of
;;; 'lines' lines distributed on 'loops' loops. polar-map and
;;; radial-map can be used to augment the spiral to create various
;;; shapes.
(defn create-spiral [height radius lines loops polar-map radial-map]
  (let
      [points
       (for [i (range 0 (+ 1 lines))]
             (let [
                   ;; step in [0; 1]
                   k (/ i lines)
                   ;; loop fraction (in [0; 1] for each loop)
                   lf (mod (* k loops) 1)
                   ;; loop fraction in radians
                   a (* lf 2 Math/PI)

                   y (* height (polar-map k))
                   r (* radius (radial-map k))
                   x (* r (Math/cos a))
                   z (* r (Math/sin a))
                   ]
               [x y z]))]
       points))
