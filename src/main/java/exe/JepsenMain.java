package exe;

import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import clojure.lang.ArraySeq;
import user.jepsen.JepsenExecutable;
import user.jepsen.Client;
import user.jepsen.CheckerCallback;
import user.jepsen.JepsenConfig;
import user.jepsen.NoopClient;
import user.jepsen.NoopDatabase;
import user.jepsen.NemesisCallback;

public class JepsenMain {
    public static class NoopChecker implements CheckerCallback {
    	public NoopChecker() {}
	
	public void check(Object test, Object history) {
	    System.out.println("Checking stuff");
	}
    }

    public static class Nemesis implements NemesisCallback {
    	public Nemesis() {}

	public void setup() {
	    System.out.println("Nemesis setup here.");
	}

 	public void invokeOp(String op) {
	    System.out.println("Executing " + op + ".");
	}
	
	public void teardown() {
	    System.out.println("Nemesis was teardown here.");
	}

	public List<String> getPossibleOps() {
	    final List<String> res = new ArrayList<>();
	    res.add("Noop start");
	    res.add("Noop end");
	    return res;	
	}
    }
 
    public static void main(String[] args) {
        JepsenConfig config = (new JepsenConfig()).add(JepsenConfig.NODES, "Bobs-MacBook-Air-2.local")
		.add(JepsenConfig.USERNAME, "bob")
		.add(JepsenConfig.PASSWORD, "bob is king")
		.add(JepsenConfig.NEMESIS, "partition-majorities-ring")
		.add(JepsenConfig.TEST_NAME, "sample_test")
		.add(JepsenConfig.TIME_LIMIT, "12");
	(new JepsenExecutable(config)).setClient(new NoopClient())
		.setDatabase(new NoopDatabase())
 		.addChecker("perf", null)
		.addChecker("noop", new NoopChecker())
		.addNemesis(new Nemesis())
		.launchTest();
    }
}
