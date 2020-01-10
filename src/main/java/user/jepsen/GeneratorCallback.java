package user.jepsen;

import java.util.Map;

public interface GeneratorCallback {
    /**
     * Only a couple types of ops are accepted.
     */ 
    public Operation generateOp(Object generator, Object test, Object process);
}

