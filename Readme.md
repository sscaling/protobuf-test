Protobuf test
=============

Implementation of [Google docs](https://developers.google.com/protocol-buffers/docs/javatutorial)

To build
--------

Install protobuf compiler

```
brew install protobuf
```

Compile the protobuf file

```
protoc -I=src/main/java/com/shaunscaling/protobuftest/ --java_out=src/main/java/ src/main/java/com/shaunscaling/protobuftest/addressbook.proto
```

Usage
-----

To generate a protobuf file (i.e. 2 people, written to test.pb)

```
 mvn exec:java -Dexec.mainClass="com.shaunscaling.protobuftest.Generate" -Dexec.args="2 test.pb"
```

To read a protobuf file and print it out

```
 mvn exec:java -Dexec.mainClass="com.shaunscaling.protobuftest.Read" -Dexec.args="test.pb"
```
