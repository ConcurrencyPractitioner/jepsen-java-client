package exe;

import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import clojure.lang.ArraySeq;
import user.jepsen.JepsenExecutable;
import user.jepsen.Client;
import user.jepsen.CheckerCallback;
import user.jepsen.JepsenConfig;
import user.jepsen.NoopClient;

public class JepsenMain {
    public static class NoopChecker implements CheckerCallback {
    	public NoopChecker() {}
	
	public void check(Object test, Object history) {
	    System.out.println("Checking stuff");
	}
    }
 
    public static void main(String[] args) {
        JepsenConfig config = (new JepsenConfig()).add(JepsenConfig.NODES, "Bobs-MacBook-Air-2.local")
		.add(JepsenConfig.USERNAME, "bob")
		.add(JepsenConfig.PASSWORD, "bob is king")
		.add(JepsenConfig.TEST_NAME, "sample_test")
		.add(JepsenConfig.TIME_LIMIT, "10");
	(new JepsenExecutable(config)).setClient(new NoopClient()).addChecker("perf", null)
		.addChecker("noop", new NoopChecker())
		.launchTest();
    }
}
