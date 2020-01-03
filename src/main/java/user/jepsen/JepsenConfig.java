package user.jepsen;

import java.util.HashMap;
import java.util.Map;

public class JepsenConfig {
    public static final String TEST_NAME = "test.name";
    public static final String TIME_LIMIT = "time.limit";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String NODES = "nodes";

    private final Map<String, String> properties;

    public JepsenConfig() {
	properties = new HashMap<>();
    }

    public JepsenConfig add(String property, String value) {
        properties.put(property, value);
	return this;
    }

    public Map<String, String> getConfig() {
        return properties;
    }
}
