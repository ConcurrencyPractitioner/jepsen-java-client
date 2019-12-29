(ns jepsen.interfaces
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

(def userClient (atom {:client nil}))

(defn java-client "Method which returns client based on protocol"
  [args]
  (reify client/Client
	 (setup! [_ _] )
	 (open! [_ test node] (java-client (-> (:client @userClient) (.openClient test node))))
	 (invoke! [client test op] 
	     (let [result (-> (:client @userClient) (.invokeClient args (:f op) (:value op)))]
	         (if (nil? result)
			(assoc op :type :fail :value 0)
			(assoc op :type :ok :value result) 
		))
	 )
         (teardown! [_ test]
             (-> (:client @userClient) (.teardownClient args))
         )))

(defn db [args] "Helps setup and teardown cluster of database being tested"
  (reify db/DB
         (setup! [_ test node]
                (-> (:client @userClient) (.setUpDatabase test node)))
         (teardown! [_ test node]
                (-> (:client @userClient) (.teardownDatabase test node)))))


(defn clientOp [_ _] 
    (let [op (-> (:client @userClient) (.generateOp))]
        {:type :invoke, :f op, :value (-> (:client @userClient) (.getValue op))})
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
	  ;:nemesis (determineNemesis (-> (:client @userClient) (.getNemesis)) 
          :generator (->> (gen/mix [clientOp]) ; thi`s operation is just as the name suggests, chosen by the client
					       ; we will leave the operation selection to the user
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

(defn main [args]
  (info args)
  (cli/run! (merge (cli/single-test-cmd {:test-fn java-client-test})
                   (cli/serve-cmd)) args)
)

(defn setClient [localClient]
  (swap! userClient assoc :client localClient)
)

(defn -main [& args] "Main method from which test is launched and also place from which Java will call this function." 
  (main args)
)
