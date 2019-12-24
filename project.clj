(defproject jepsen.java "0.1.0-SNAPSHOT"
  :description "A Java wrapper for the Jepsen fault injection test framework."
  :url "https://github.com/ConcurrencyPractitioner/jepsen-java-client"
  :license {:name "EPL-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
		 [jepsen "0.1.13"]]
  :main jepsen.java
  :java-source-paths ["src/"]
  :repl-options {:init-ns jepsen.java})
