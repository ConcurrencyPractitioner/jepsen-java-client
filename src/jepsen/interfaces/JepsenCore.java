package jepsen.interfaces;

import clojure.lang.ArraySeq;

public interface JepsenCore {
    void execTest(ArraySeq args);
}
