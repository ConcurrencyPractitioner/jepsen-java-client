(ns jepsen.java
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clojure.core.reducers :as r]
            [clojure.tools.logging :refer :all]
            [knossos.model :as model]
            [knossos.op :as op]
            [jepsen.db :as db]
            [jepsen.cli :as cli]
            [jepsen.checker :as checker]
            [jepsen.client :as client]
            [jepsen.control :as control]
            [jepsen.generator :as gen]
            [jepsen.nemesis :as nemesis]
            [jepsen.tests :as tests]
            [jepsen.os.centos :as centos]
            [jepsen.control.util :as control-util]
            [jepsen.client :as client])
  (:import [user.jepsen Client])
)

(defn java-client "Method which returns client based on protocol"
  [args]
  (reify client/Client
         (open! [_ test node] (java-client (Client/openClient test node)))
         (invoke! [client test op] 
	     (let [result (Client/invoke args)]
	         (if result
			(assoc op :type :ok)
			(assoc op :type :fail) 
		))
	 )
         (teardown! [_ test]
             (Client/teardownClient args)
         )))

(defn db [args] "Helps setup and teardown cluster of database being tested"
  (reify db/DB
         (setup! [_ test node]
                (Client/setUpDatabase test node))
         (teardown! [_ test node]
                (Client/teardownDatabase test node))))


(defn java-client-test [opts] "Test to be run"
  (merge tests/noop-test
         opts
         {:name "shiva"
          :client (java-client nil)
          :db (db nil)
	  ;:nemesis (nemesis/partition-majorities-ring)
          ;:generator (->> (gen/mix ) ;ignore this comment, too lazy to delete :P disabled scan because of strange behavior
          ;                (gen/stagger 1)
          ;                (gen/nemesis (gen/seq (cycle [(gen/sleep 30)
          ;                                             {:type :info, :f :start}
          ;                                             (gen/sleep 30)
          ;                                             {:type :info, :f :stop}])))
          ;                (gen/time-limit (:time-limit opts)))
          :checker (checker/compose {; add own latency checker here
                                      :perf (checker/perf)
                                    })})
)

(defn -main [& args] "Main method from which test is launched" 
  (cli/run! (merge (cli/single-test-cmd {:test-fn java-client-test})
                   (cli/serve-cmd))
             args)  
)
