package exe;

import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import clojure.lang.ArraySeq;
import user.jepsen.JepsenExecutable;
import user.jepsen.Client;
import user.jepsen.CheckerCallback;

public class JepsenMain {
    public static class NoopClient implements Client {
        public NoopClient() {}
 
        public Object setUpDatabase(String node) { System.out.println("Setup DB"); return "NOOP_DB"; }

        public void teardownDatabase(String node) { System.out.println("Torndown DB"); }

        public void teardownClient(Object args) { System.out.println("Torndown client " + args); }

        public Object invokeClient(Object args, String opName, Object inputValue) { System.out.println("Invoked client op " + opName + " with input value "  + inputValue); return true; }

        public Object openClient(String node) { System.out.println("Have opened client"); return "NOOP_CLIENT"; }

        public String getNemesis() { return "partition-random-halves"; }

        public String generateOp() { return "dummyOp"; }

        public Object getValue(String opName) { return (new Random()).nextInt(100); }
    }
     
    public static class NoopChecker implements CheckerCallback {
    	public NoopChecker() {}
	
	public void check(Object test, Object history) {
	    System.out.println("Checking stuff");
	}
    }
 
    public static void main(String[] args) {
    	JepsenExecutable exec = new JepsenExecutable("Richards-Macbook-Air-2.local", "richardyu", "jinjin123", 10, new NoopClient());
        Map<String, CheckerCallback> checkers = new HashMap<>();
	checkers.put("perf", null);
	checkers.put("noop", new NoopChecker());
	exec.addCheckers(checkers);
        exec.launchTest();
    }
}
