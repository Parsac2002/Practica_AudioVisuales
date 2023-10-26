#!/usr/bin/env bash
/opt/jdk1.8.0_141/bin/java \
-cp .:`echo ./lib/vlcj-3.10.1/*.jar | tr ' ' ':'` Server