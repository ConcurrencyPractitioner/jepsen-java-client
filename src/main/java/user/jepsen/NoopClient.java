package user.jepsen;

import java.util.Random;

public class NoopClient implements Client {
    public NoopClient() {}

    public Object setUpDatabase(String node) {
	System.out.println("Setup DB"); 
	return "NOOP_DB";
    }

    public void teardownDatabase(String node) { 
	System.out.println("Torndown DB"); 
    }

    public void teardownClient(Object args) { 
	System.out.println("Torndown client " + args); 
    }

    public Object invokeClient(Object args, String opName, Object inputValue) { 
	System.out.println("Invoked client op " + opName + " with input value "  + inputValue); 
	return true; 
    }

    public Object openClient(String node) { 
        System.out.println("Have opened client"); 
	return "NOOP_CLIENT"; 
    }

    public String getNemesis() { 
	return "partition-random-halves"; 
    }

    public String generateOp() { 
	return "dummyOp"; 
    }

    public Object getValue(String opName) { 
	return (new Random()).nextInt(100); 
    }
}
