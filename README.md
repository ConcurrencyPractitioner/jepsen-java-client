# Jepsen Java Client

[![Build Status](https://travis-ci.com/ConcurrencyPractitioner/jepsen-java-client.svg?branch=master)](https://travis-ci.com/ConcurrencyPractitioner/jepsen-java-client)
[![License](https://img.shields.io/badge/License-EPL%202.0-blue.svg)](https://opensource.org/licenses/EPL-2.0)

## Design

The goal of this project is to allow the user to write a Jepsen test in a more comfortable language. The current design of the project is simple. The user should think of this project more as a template Jepsen test rather than a library from which tools are imported. In the future, the Jepsen Java Client community might consider changing this design. Objectively, it is only neccessary to modify the callbacks provided in the Client class to write your own Jepsen test!

## About

This is a Java wrapper for the Jepsen fault tolerance testing framework which is written in Clojure, a LISP language. LISP's following among programmers nowadays is mediocre, so many developers wish to write a Jepsen test in a more friendly language. This library provides them with such a choice, as it implements a Java wrapper on top of Jepsen, which makes fault injection tests far easier to write.

## Running the test

There are a couple of simple steps to run this test:
  1. Implement the client, database, and checker callbacks provided in the Java class. For further info on what each of these      is supposed to do, please look to the tutorial for reference.
  2. Use ```lein run test``` to run the test. There might be other arguments that need to be entered. A comprehensive list is      available in the introduction of the tutorial.

It should be helpful to know a couple of things when running the test. Jepsen requires that the shell one is attempting to ```ssh``` onto has remote login enabled. Typically, it is turned off by default, and consequently, that means the ```jepsen``` daemon is unable to connect to the node/host provided. 

Tutorial link below:
https://github.com/ConcurrencyPractitioner/jepsen-java-client/blob/master/doc/tutorial.md
