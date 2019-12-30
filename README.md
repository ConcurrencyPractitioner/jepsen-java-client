# Jepsen Java Client

[![Build Status](https://travis-ci.com/ConcurrencyPractitioner/jepsen-java-client.svg?branch=master)](https://travis-ci.com/ConcurrencyPractitioner/jepsen-java-client)
[![License](https://img.shields.io/badge/License-EPL%202.0-blue.svg)](https://opensource.org/licenses/EPL-2.0)

Latest version: v1.0.1, Available here:
https://github.com/ConcurrencyPractitioner/jepsen-java-client/releases

## Design

The goal of this project is to allow the user to write a Jepsen test in a more comfortable language. The current design of the project is simple. Previously, this library was served primarily more as a template Jepsen test than an actual Java library (as in version 0.1). In the current release, this repository has been transformed into an actual library from which you can import tools which allows you to run Jepsen tests. The tutorial will explain how to write one.

## About

This is a Java wrapper for the Jepsen fault tolerance testing framework which is written in Clojure, a LISP language. LISP's following among programmers nowadays is mediocre, so many developers wish to write a Jepsen test in a more friendly language. This library provides them with such a choice, as it implements a Java wrapper on top of Jepsen, which makes fault injection tests far easier to write.

## Running the test

There are a couple of simple steps to run this test.
  1. Currently, there are a number of simple components to this test. Provided in the ```user.jepsen``` package are two
     classes: ```JepsenExecutable``` and the ```Client``` interface. In the latter class, a series of callbacks must be
     implemented by the user before the test is launched.
  2. Construct a ```JepsenExecutable``` instance, and then you can straight away launch the test. This library is relatively
     simple and should not be hard to do.

It should be helpful to know a couple of things when running the test. Jepsen requires that the shell one is attempting to ```ssh``` onto has remote login enabled. Typically, it is turned off by default, and consequently, that means the ```jepsen``` daemon is unable to connect to the node/host provided. 

Tutorial link below:
https://github.com/ConcurrencyPractitioner/jepsen-java-client/blob/master/doc/tutorial.md
