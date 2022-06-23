!#/bin/bash

mvn -B clean install -DskipTests

kill -9 $(lsof -t -i:5000)

mvn exec:java &
mvn test -Dtest="world1x1.AcceptanceTests*" -pl ReferenceSocketClient

kill -9 $(lsof -t -i:5000)

mvn exec:java -Dexec.args="-s 2" &
mvn test -Dtest="world2x2.AcceptanceTests*" -pl ReferenceSocketClient

kill -9 $(lsof -t -i:5000)

mvn exec:java -Dexec.args="-s 2 -o 1,1" &
mvn test -Dtest="obstacle1_1.AcceptanceTests*" -pl ReferenceSocketClient

kill -9 $(lsof -t -i:5000)

mvn exec:java -Dexec.args="-s 2 -o 0,1"  &
mvn test -Dtest="obstacle0_1.AcceptanceTests*" -pl ReferenceSocketClient

kill -9 $(lsof -t -i:5000)
