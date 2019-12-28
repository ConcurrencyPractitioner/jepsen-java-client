package exe;

import java.io.*;
import clojure.lang.ArraySeq;
import user.jepsen.JepsenExecutable;

public class JepsenMain {
    public static void main(String[] args) {
    	JepsenExecutable exec = new JepsenExecutable("Richards-Macbook-Air-2.local", "richardyu", "jinjin123", 10);
        exec.launchTest();
    }
}
