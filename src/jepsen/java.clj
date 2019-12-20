(ns jepsen.java
  (:gen-class)
  (:import [source.jepsen Client])
)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn -main [& args] "not much here yet"  ;for now, we just put this here, might break it up if file gets too large
  (println args "Bobewell")
)
