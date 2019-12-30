# Change Log
All notable changes to this project will be documented in this file. This change log follows the conventions of [keepachangelog.com](http://keepachangelog.com/).

## 0.1.0 - 2019-12-25

This is the initial release for Jepsen Java Client. The project at the moment acts more as a template Jepsen test rather than a library from which tools can be imported to write a Jepsen test in Java. In a future release, this project will be turned into a more formalized library.

## 1.0.0 - 2019-12-29

This project has been officially transformed into a Java library. (We have migrated from leiningen to gradle). In this release, the user only needs to import certain classes/interfaces to run tests. For further details, see documentation. Here is a sample test for reference:
https://github.com/ConcurrencyPractitioner/jepsen-java-client/blob/master/src/main/java/exe/JepsenMain.java

This should be far easer to maintain and scale depending on the user's needs.

## 1.0.1 - 2019-12-29

Hotfix: Enable fault injection, previously commented it out.

## 1.0.2 - 2019-12-30

Removed a useless test object in the Client interface. It leaks information from internals which is not preferred. This is meant to help streamline some of the methods.
