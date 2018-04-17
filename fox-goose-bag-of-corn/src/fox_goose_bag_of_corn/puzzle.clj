(ns fox-goose-bag-of-corn.puzzle)

(def start-pos [[[:fox :goose :corn :you] [:boat] []]])

(defn river-crossing-plan []
  (loop [plan start-pos dir 1]
    (let [lstep (last plan) [lbank boat rbank] lstep]
      (if (= (set (ffirst plan)) (set rbank))
        plan
        (let [[selected-bank other-bank bank-pos]
              (if (> dir 0) [lbank rbank 0] [rbank lbank 2])
              r (vec (first (filter #(and
                            (not= (set %) #{:fox :goose})
                            (not= (set %) #{:goose :corn}))
                          (map #(remove #{:you %1} %2)
                               [:fox :goose :corn]
                               (repeat selected-bank)))))
              o (vec (remove (set r) selected-bank))]
          (recur (conj plan
                       (apply assoc lstep [bank-pos r
                                           (+ bank-pos dir)
                                           (apply conj boat o)])
                       (apply assoc lstep [bank-pos r
                                           (+ bank-pos (* 2 dir))
                                           (apply conj other-bank o)]))
                 (- dir)))))))
