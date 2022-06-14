default: build

clean:
	mvn clean

compile:
	mvn compile

test:
	mvn test

package:
	    mvn package -DskipTests

test_reference_server:
	java -jar libs/reference-server-0.1.0.jar & echo $$! > $@
	mvn test
	kill `cat test_reference_server` && rm test_reference_server

test_server: package
	java -jar target/SocketServer-1.0-SNAPSHOT-jar-with-dependencies.jar & echo $$! > $@
	mvn test
	kill `cat test_server` && rm test_server

build: clean compile test_reference_server test_server