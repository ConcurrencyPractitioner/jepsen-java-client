
## Jepsen Test Construction

It is recommended that the user uses a ```JepsenConfig``` to construct a ```JepsenExecutable``` instance:

1. ```JepsenConfig.NODES```: These should be the nodes that Jepsen would operate on as well as the nodes on which the database cluster will be run. (Default is ```localhost:9092```)

2. ```JepsenConfig.USERNAME```: For each node, there is an ```ssh``` username. The user will need to change it if the node's username is different from the current default which is ```root```.

3. ```JepsenConfig.PASSWORD```: Similar to username, but is the password instead. Default is ```root``` as well.

4. ```JepsenConfig.TIME_LIMIT```: Sets the time limit of the test. Should be used. Defines the length of test in seconds. (Default is 10 seconds)

An example configuration of a Jepsen test would be:

```java

JepsenConfig config = (new JepsenConfig()).add(JepsenConfig.NODES, "Bobs-MacBook-Air-2.local")
		.add(JepsenConfig.USERNAME, "bob")
		.add(JepsenConfig.PASSWORD, "bob is king")
		.add(JepsenConfig.NEMESIS, "partition-majorities-ring")
		.add(JepsenConfig.TEST_NAME, "sample_test")
		.add(JepsenConfig.TIME_LIMIT, "13")
		.add(JepsenConfig.CLIENT_OP_WAIT_TIME, "1")
		.add(JepsenConfig.NEMESIS_OP_WAIT_TIME, "3");
 
JepsenExecutable exec = new JepsenExectuable(config);
```
After the executable has been constructed, the user can add checkers which could verify the history of the operations performed. There are multiple checkers defined already by Jepsen which the user can add (already mentioned in the tutorial, "perf"). Multiple checkers can be added to the test.
