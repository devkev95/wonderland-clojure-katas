(ns doublets.solver
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [clojure.set :as set]))

(def words (-> "words.edn"
               (io/resource)
               (slurp)
               (read-string)))

(defn linked-word [word word-list]
  (some #(when (= (count (set/difference (into #{} (seq %))
                                         (into #{} (seq word)))) 1) %)
        word-list))

(defn doublets [word1 word2]
  (let [n (count word1) v (cons word1 (filter #(= (count %) n)
                              (remove #{word1} words)))]
    (tree-seq #(and (linked-word (% 0) %) (not= (% 0) word2))
              #(cons (linked-word (% 0) (rest %))
                (remove #{(% 0)} (rest %))) v)))
