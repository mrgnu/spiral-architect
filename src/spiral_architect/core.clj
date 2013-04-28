(ns spiral-architect.core
  (:use spiral-architect.shape
        quil.core
        [quil.helpers.drawing :only [line-join-points]]))

;;; spiral config
(def radius 100)
(def lines  512)
(def loops   16)

;;; fade config
(def fade-frame-count 60)

(defn get-func [idx]
  (condp = idx
      \1 make-cylinder
      \2 make-cone
      \3 make-sphere
      \4 make-pendant
      nil))

(def cur-idx \1)
(defn load-spiral []
  (let [func (get-func cur-idx)]
    (if func
      (func radius radius lines loops))))
(def spiral (load-spiral))
(def from-spiral nil)

(defn start-fade [to]
  (def fade-start-frame (frame-count))
  (def from-spiral spiral)
  (def to-spiral to))

(defn draw-spiral [points]
  (dorun (map #(apply line %) (line-join-points points))))

(defn draw []
  (hint :enable-depth-test)

  (if (key-pressed?)
    (let [idx (raw-key)]
      (cond 
       ;; flip
       (and (= idx \f) (nil? from-spiral)) (start-fade (flip-spiral spiral))
       ;; morph
       (and (not= cur-idx idx) (get-func idx))
       (do
         (def cur-idx idx)
         (start-fade (load-spiral))))))

  ;; morph between previous and current shape
  (if from-spiral
    (let [factor (/ (- (frame-count) fade-start-frame) fade-frame-count)]
      (if (>= factor 1.)
        (do
          (def spiral to-spiral)
          (def from-spiral nil)
          (def to-spiral nil))
        (def spiral (merge-spirals from-spiral to-spiral factor)))))    

  (background 192)
  (translate (/ (width) 2) (/ (height) 2))

  ;; rotate viewport
  (rotate-x (* (frame-count) 0.005))
  (rotate-z (* (frame-count) 0.008))

  ;; roate around y-axis of spiral
  (rotate-y (* (frame-count) 0.1))
  (draw-spiral spiral)
  )

(defn setup []
  (smooth)
  (frame-rate 60)
  (background 255)
  (stroke 00))

(defsketch spiral-sphere
  :title "spiral sphere"
  :setup setup
  :draw draw
  :size [512 512]
  :renderer :opengl)

(defn -main []
  (println "sorcerers of madness selling me their time"))
