package user.jepsen;

import java.util.List;

public interface NemesisCallback {
    
    public void setup();

    public void invokeOp(String op);

    public void teardown();

    public List<String> getPossibleOps();
}
