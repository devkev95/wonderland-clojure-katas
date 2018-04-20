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
  (loop [words word-list word (first word-list) res {}]
    (if (nil? word)
      res
      (let [pairs
            (map (fn [w]
                   (let [n (count w)]
                     (map #([(keyword
                              (apply format "%s_%s"
                                     (s/split w (-> w
                                                    (get %)
                                                    (str)
                                                    (re-pattern))))) w])
                            (take n (iterate inc 0)))))
                 words)]
        ))))

(defn words-graph [word-list])

(defn doublets [word1 word2]
  (let [words-list (filter #())])
  )
