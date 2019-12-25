package user.jepsen;

import java.util.*;
import java.io.*;
import java.lang.*;

public class Client {
    public static Object setUpDatabase(Object test, String node) { System.out.println("Setup DB"); return null; }
    public static void teardownDatabase(Object test, String node) { System.out.println("Torndown DB"); }
    public static void teardownClient(Object args) { System.out.println("Torndown client"); }
    public static boolean invokeClient(Object args, String opName, Object inputValue) { System.out.println("Invoked client op " + opName + " with input value "  + inputValue); return true; }
    public static void openClient(Object test, String node) { System.out.println("Have opened client"); }
    public static String getNemesis() { return "partition-random-halves"; }
    public static String generateOp() { return "dummyOp"; }
    public static Object getValue(String opName) { return (new Random()).nextInt(100); } 
}
