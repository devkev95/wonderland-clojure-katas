(ns fox-goose-bag-of-corn.puzzle)

(def start-pos [[[:fox :goose :corn :you] [:boat] []]])

(defn you-index [v] (first (keep-indexed
                     (fn [i n] (if (some #(= % :you) n) i)) v)))

(defn river-crossing-plan []
  (loop [start-pos start-pos dir 1]
    (let [v (last start-pos) you-ind (you-index v)]
      (if (empty? (first v))
        start-pos
        (map #(let [res (remove #{})]) (remove #{:boat} (v you-ind))))
      )
    ))
