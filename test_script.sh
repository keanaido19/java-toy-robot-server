!#/bin/bash

kill -9 $(lsof -t -i:5000)
mvn -B clean install -DskipTests
mvn exec:java &
mvn test -Dtest="world1x1.AcceptanceTests*" -pl ReferenceSocketClient
kill -9 $(lsof -t -i:5000)
