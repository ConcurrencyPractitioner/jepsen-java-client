
## Parameter List

Below are the list of parameters which should be used with the ```lein run test``` command:

1. ```nodes```: These should be the nodes that Jepsen would operate on as well as the nodes on which the database cluster will be run.

2. ```username```: (Optional) For each node, there is an ```ssh``` username. The user will need to change it if the node's username is different from the current default which is ```root```.

3. ```password```: (Optional) Similar to username, but is the password instead. Default is ```root``` as well.

4. ```time-limit```: (Optional, but highly advised) Sets the time limit of the test. Should be used. Defines the length of test in seconds.

An example command would be:
``` 
lein run test --nodes Bobs-MacBook-Air-2.local --username bobewell --password birthday --time-limit 10
```
