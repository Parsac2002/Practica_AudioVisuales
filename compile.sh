#!/usr/bin/env bash
/opt/jdk1.8.0_141/bin/javac \
-cp `echo ./lib/vlcj-3.10.1/*.jar | tr ' ' ':'` *.java
mv *.class ./class_files/