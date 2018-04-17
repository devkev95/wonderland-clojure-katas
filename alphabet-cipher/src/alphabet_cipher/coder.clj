(ns alphabet-cipher.coder
  (:require [clojure.string :as str]))

(defn char-idx [c]
  (- (int c) 97))
(defn encode [keyword message]
  (apply str (map #(char (+ (mod (+ (char-idx %1) (char-idx %2)) 26)
                            97)) message (cycle keyword))))

(defn decode [keyword message]
  (apply str (map #(char (+ (mod (- (char-idx %1) (char-idx %2)) 26)
                            97)) message (cycle keyword))))

(defn decipher [cipher message]
  (let [x (decode message cipher)]
    (loop [i 1]
      (let [res (decode (subs x 0 i) cipher)]
        (if (= res message)
          (subs x 0 i)
          (recur (inc i)))))))
