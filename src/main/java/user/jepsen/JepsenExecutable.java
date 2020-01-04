package user.jepsen;

import clojure.lang.ArraySeq;
import clojure.java.api.Clojure;
import clojure.lang.RT;
import java.util.*;
import java.lang.*;
import java.io.*;

public class JepsenExecutable {
    private final String timeLimit;
    private final String nodes;
    private final String username, password;
    private Client client;
    private Database database;
    private Map<String, CheckerCallback> checkerCallbacks;
    private final String testName;
    private final String nemesis;
 
    public JepsenExecutable(final String nodes, final String username, final String password, final long timeLimit, final Client client) {
    	this.nodes = nodes;
	this.username = username;
	this.password = password;
	this.timeLimit = Long.toString(timeLimit);
	this.client = client;
	this.checkerCallbacks = null;
	this.testName = "test";
	this.nemesis = "partition-random-halves";
	this.database = new NoopDatabase();
    }
    
    public JepsenExecutable(final JepsenConfig config) {
	final Map<String, String> properties = config.getConfig();
	this.nodes = properties.get(JepsenConfig.NODES);
	this.username = properties.get(JepsenConfig.USERNAME);
	this.password = properties.get(JepsenConfig.PASSWORD);
	this.timeLimit = properties.get(JepsenConfig.TIME_LIMIT);
	this.testName = properties.get(JepsenConfig.TEST_NAME);
	this.checkerCallbacks = null;
	this.client = new NoopClient();
	this.database = new NoopDatabase();
	this.nemesis = properties.get(JepsenConfig.NEMESIS);
    }

    public JepsenExecutable setClient(final Client client) {
    	this.client = client;
	return this;
    }

    public JepsenExecutable setDatabase(final Database database) {
    	this.database = database;
	return this;
    }

    public JepsenExecutable addChecker(final String checkerName, final CheckerCallback callback) {
   	if (checkerCallbacks == null)
	    checkerCallbacks = new HashMap<>();
	checkerCallbacks.put(checkerName, callback);
	return this;
    }

    public JepsenExecutable addCheckers(final Map<String, CheckerCallback> callbacks) {
    	if (checkerCallbacks == null)
	    checkerCallbacks = new HashMap<>();
	for (final Map.Entry<String, CheckerCallback> entry : callbacks.entrySet())
	    checkerCallbacks.put(entry.getKey(), entry.getValue());
        return this;
    }

    public void launchTest() {
        try {
	    RT.loadResourceScript("jepsen/interfaces/main.clj", true);
	    String[] args = {"test", "--nodes", nodes, "--username", username, "--password", password, "--time-limit", timeLimit};
	    RT.var("jepsen.interfaces", "setTestName").invoke(testName);
            RT.var("jepsen.interfaces", "setClient").invoke(client);
	    RT.var("jepsen.interfaces", "setDatabase").invoke(database);
	    RT.var("jepsen.interfaces", "setNemesis").invoke(nemesis);
	    RT.var("jepsen.interfaces", "setCheckerCallbacks").invoke(checkerCallbacks);
	    RT.var("jepsen.interfaces", "main").invoke(args);
        } catch (IOException exc) { System.out.println("Found exception " + exc); }
    }
}
