
## Parameter List

Below are the list of parameters which should be used to construct the ```JepsenExecutable``` instance:

1. ```nodes```: These should be the nodes that Jepsen would operate on as well as the nodes on which the database cluster will be run.

2. ```username```: For each node, there is an ```ssh``` username. The user will need to change it if the node's username is different from the current default which is ```root```.

3. ```password```: Similar to username, but is the password instead. Default is ```root``` as well.

4. ```time-limit```: Sets the time limit of the test. Should be used. Defines the length of test in seconds.

5. ```client```: Defines the behavior for each component of the Jepsen test. Explanation available in tutorial.

An example instance would be:
``` 
String nodes = "host1:port1, host2:port2";
String username = "root";
String password = "root";
JepsenExecutable exec = new JepsenExecutable(nodes, username, password, 10, new UserDefinedClient()); // 10 second timelimit
```
