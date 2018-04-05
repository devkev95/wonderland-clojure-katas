(ns wonderland-number.finder)

(defn wonderland-number []
  (let [n [1 2 3 4 5 6]]
  (loop [i 100000]
    (let [res (map (fn [n] (apply str (sort-by #(Character/digit % 10)
                                               (seq (str (* n i))))))
                   n)]
      (if (apply = res)
        i
        (recur (inc i)))))))

(defn sum-of-cubes []
  (for [x (range 1001)
    :let [y (int (apply + (map #(Math/pow (Character/digit % 10) 3)
                               (seq (str x)))))]
    :when (= x y)]
    x))
