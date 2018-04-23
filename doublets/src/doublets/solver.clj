(ns doublets.solver
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [clojure.set :as set]
            [clojure.string :as s]))

(def words (-> "words.edn"
               (io/resource)
               (slurp)
               (read-string)))

(defn word-buckets [word-list]
  (let [pairs
        (mapcat (fn [w]
               (let [n (count w)]
                 (map #(identity [(keyword
                                   (apply format "%s_%s"
                                          [(subs w 0 %)
                                           (subs w (+ % 1))])) w])
                      (take n (iterate inc 0)))))
                      word-list)]
    (reduce (fn [m pair]
              (let [k (first pair) v (second pair)]
                (assoc m k (cons v (get m k))))) {} pairs)))

(defn words-graph [word-list]
  (let [words-bucket (word-buckets word-list)]
    (filter not-empty (mapcat (fn [[_ v]]
                                (map #(identity [%1 %2]) v (rest v)))
                              words-bucket))))

(defn doublets [word1 word2]
  (if (= (count word1) (count word2))
    (let [n (count (word1))
          words-list (filter #(= n (count %)) words)
          graph (words-graph words-graph)]
      (tree-seq #(not= (last %) word2)
                (fn [doublet]
                  (->> graph
                       (filter (fn [v]
                                 (and (some #{(last doublet)} v)
                                      (not-any? #(some #{%} v)
                                                (drop-last doublet))))))))
    []))
