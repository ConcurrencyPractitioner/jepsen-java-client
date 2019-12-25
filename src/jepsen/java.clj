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
	 (setup! [_ _] )
	 (open! [_ test node] (java-client (Client/openClient test node)))
	 (invoke! [client test op] 
	     (let [result (Client/invokeClient args (:f op) (:value op))]
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


(defn clientOp [_ _] 
    (let [op (Client/generateOp)]
        {:type :invoke, :f op, :value (Client/getValue op)})
)

(defn determineNemesis [nemesisName] 
      (case nemesisName
        "partition-majorities-ring" (nemesis/partition-majorities-ring)
        "partition-random-halves"  (nemesis/partition-random-halves)
      ))

(defn java-client-test [opts] "Test to be run"
  (merge tests/noop-test
         opts
         {:name "shiva"
          :client (java-client nil)
          :db (db nil)
	  :nemesis (determineNemesis (Client/getNemesis)) 
          :generator (->> (gen/mix [clientOp]) ; this operation is just as the name suggests, a dummy, it doesn't do anything
					      ; we will leave the operation randomization to the user
                          (gen/stagger 1)
                          (gen/nemesis (gen/seq (cycle [(gen/sleep 30)
                                                        {:type :info, :f :start}
                                                        (gen/sleep 30)
                                                        {:type :info, :f :stop}])))
                          (gen/time-limit (:time-limit opts)))
          :checker (checker/compose {; add own latency checker here
                                      :perf (checker/perf)
                                    })})
)

(defn -main [& args] "Main method from which test is launched" 
  (cli/run! (merge (cli/single-test-cmd {:test-fn java-client-test})
                   (cli/serve-cmd))
             args)  
)
