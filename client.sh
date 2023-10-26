#!/usr/bin/env bash
mv ./class_files/*.class ./
/opt/jdk1.8.0_141/bin/java \
-cp .:`echo ./lib/vlcj-3.10.1/*.jar | tr ' ' ':'` Client
mv *.class ./class_files