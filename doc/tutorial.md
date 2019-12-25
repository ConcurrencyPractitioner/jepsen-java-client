# Tutorial

## How to run Jepsen Java Client

Jepsen is split into several components: Client, Database, Checker, and Nemesis. These serve as the few major parts of Jepsen
's framework and each of their roles is described below in detail.

- Note: Do not change any of the method names or the number of input arguments it takes in as it will break the test -

### Client

As with any client-server design, the client is responsible for connecting to the database and is usually responsible for both writes and reads. In Jepsen's version of ```client```, there a few basic functions which it must perform. 

  1. Open: In Jepsen, this part of the client is responsible for constructing a client and then connecting to a cluster. These      operations are performed in the ```openClient``` method provided under ```src/user/jepsen/Client.java```. The user would      in the provided method, construct a client and then open a connection with the cluster being tested. Below is an example 
     with the Shiva database used by Transwarp.
     
  ```
  
  ```

  2. Invoke: During the invoke phase, the user can choose a series of methods from which they are randomly chosen and then the      client will perform the selected operation.
  
  3. Close: 
