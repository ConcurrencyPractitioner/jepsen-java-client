# Tutorial

As of the moment, all of the methods in the Client java file found under ```user.jepsen``` package should all be tailored to each individual user's requirements. Below explains the expected behavior of each method.

For an example of a Jepsen test written using this library, see sample:
https://github.com/ConcurrencyPractitioner/jepsen-java-client/blob/master/src/main/java/exe/JepsenMain.java

For a list of ```linux``` parameters which should be run with the test, please look here:
https://github.com/ConcurrencyPractitioner/jepsen-java-client/blob/master/doc/parameters.md

## How to run Jepsen Java Client

Jepsen is split into several components: Client, Database, Checker, and Nemesis. These serve as the few major parts of Jepsen
's framework and each of their roles is described below in detail.

### Client

As with any client-server design, the client is responsible for connecting to the database and is usually responsible for both writes and reads. In Jepsen's version of ```client```, there a few basic functions which it must perform. 

  1. Open: In Jepsen, this part of the client is responsible for constructing a client and then connecting to a cluster. These      operations are performed in the ```openClient``` method provided under ```src/user/jepsen/Client.java```. The user would      in the provided method, construct a client and then open a connection with the cluster being tested. 

  2. Invoke: During the invoke phase, the user can select from a series of methods of their choosing. Whether it is preferred      for it to be randomized or for there to be a specific pattern, it is entirely up to the user. Operations are generated        using the ```generateOp``` and ```grabValue``` methods. The ```invokeClient``` method will then be called, where the          operation that has been chosen by the user will be executed.  
  
  3. Close: This is mostly self-explanatory. Now that the test has been concluded, the user should close the connection and        then remove the client from memory.
  
### Database

This part of Jepsen is more straightforward. There is largely two parts to it all: setup and teardown. As the name suggests, in the former phase, the user would setup a database node and then start running it. Teardown intuitively would be in charge of the database's shutdown and removal. 

This is optional. However, it is highly recommended that the user automate the setup and teardown of database clusters. Doing so would allow the user to run Jepsen tests in a continuous integration environment when there are multiple pull requests (PRs) in progress concurrently. Using a single manually setup database cluster on the other hand would lead to a bottleneck as only one PR at a time technically can be tested. 

### Checker

The role of ```Checker``` is to ensure that the history of the test is correct, or otherwise the test is invalidated.

Jepsen already has a couple of checkers implemented that is available to the user. If you wish to add a checker to your test, and it is one already defined by Jepsen, add it using the ```addCheckers``` method available in ```JepsenExecutable```, and mark the value as null. See example below:

```java

Map<String, CheckerCallback> checkers = new HashMap<>();
checkers.put("perf", null);
  
```

If you wish to define your own checker, you can make your own custom ```Checker``` through the ```CheckerCallback``` interface. Afterwards, just add that callback (it being the value) along with the corresponding checker name (as the key) to the test through the ```addCheckers``` method as well. 

```java
public class NoopChecker implements CheckerCallback {
    public NoopChecker() {}
	  @Override
	  public void check(Object test, Object history) {
	      System.out.println("Checking stuff");
	  }
}

checkers.put("Noop", new NoopChecker());
```

### Nemesis

The user can choose from a variety of failures that Jepsen can run on the test cluster. For documentation about the types available, please see Jepsen's nemesis documentation here: https://github.com/jepsen-io/jepsen/blob/master/doc/tutorial/05-nemesis.md

The current options supported are:
  1. partition-majorities-ring
  2. partition-random-halves
  
The ```getNemesis``` method is called to retrieve the type of failure that the user wishes to stimulate. 

