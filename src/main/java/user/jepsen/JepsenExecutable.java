package user.jepsen;

import clojure.lang.ArraySeq;
import clojure.java.api.Clojure;
import clojure.lang.IFn;
import jepsen.interfaces.JepsenCore;
import clojure.lang.RT;
import java.util.*;
import java.lang.*;
import java.io.*;

public class JepsenExecutable {
    private final long timeLimit;
    private final String nodes;
    private final String username, password;
    public JepsenExecutable(final String nodes, final String username, final String password, final long timeLimit) {
    	this.nodes = nodes;
	this.username = username;
	this.password = password;
	this.timeLimit = timeLimit;
    }
    public void launchTest() {
        try {
	    RT.loadResourceScript("jepsen/interfaces/main.clj", true);
	    String[] args = {"test", "--nodes", nodes, "--username", username, "--password", password, "--time-limit", Long.toString(timeLimit)};
	    RT.var("jepsen.interfaces", "main").invoke(args);
        } catch (IOException exc) { System.out.println("Found exception " + exc); }
    }
}
