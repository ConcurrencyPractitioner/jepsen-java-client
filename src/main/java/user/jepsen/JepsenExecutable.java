package user.jepsen;

import clojure.lang.ArraySeq;
import clojure.java.api.Clojure;
import clojure.lang.IFn;
import jepsen.interfaces.JepsenCore;

public class JepsenExecutable {
    private final JepsenCore jepsenCore = (JepsenCore) Clojure.var("jepsen.interfaces", "JepsenTest").invoke();
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
	jepsenCore.execTest(ArraySeq.create("test", "--nodes", nodes, "--username", username, "--password", password, "--time-limit", timeLimit).toString());
    }
}
