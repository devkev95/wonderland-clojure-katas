(ns fox-goose-bag-of-corn.puzzle)

(def start-pos [[[:fox :goose :corn :you] [:boat] []]])

(defn river-crossing-plan []
  (loop [plan start-pos dir 1]
    (let [lstep (last plan) [lbank boat rbank] lstep]
      (if (= (set (ffirst plan)) (set rbank))
        plan
        (let [[selected-bank other-bank bank-pos]
              (if (> dir 0) [lbank rbank 0] [rbank lbank 2])
              options (vec (sort-by count > (map vec (filter
                                     #(and
                                       (not= (set %) #{:fox :goose})
                                       (not= (set %) #{:goose :corn}))
                                     (map #(remove #{:you %1} %2)
                                          [:fox :goose :corn]
                                          (repeat selected-bank))))))]
          (let [res (loop [options options]
                          (let [remain (first options)
                                opt (vec (remove (set opt) selected-bank))
                                poss-res [(apply assoc lstep [bank-pos remain
                                                                   (+ bank-pos
                                                                      dir)
                                                                   (apply conj
                                                                          boat
                                                                          opt)])
                                          (apply assoc lstep [bank-pos
                                                              remain
                                                                   (+ bank-pos
                                                                      (* 2 dir))
                                                                   (apply
                                                                    conj
                                                                    other-bank
                                                                    opt)])]]
                            (if (not-any? (set poss-res) (set plan))
                              poss-res
                              (recur (rest options)))))]
          (recur (apply conj plan res) (- dir))))))))
