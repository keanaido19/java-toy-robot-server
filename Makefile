SERVER_JAR = $(wildcard target/SocketServer*.jar)
REF_SERVER_JAR = $(wildcard libs/reference-server*.jar)

VERSION = 0.0.0
SNAPSHOT_VERSION = $(VERSION)-SNAPSHOT

PATCH_VERSION := $(shell mvn build-helper:parse-version  help:evaluate -Dexpression=parsedVersion.incrementalVersion -q -DforceStdout)
NEXT_PATCH_VERSION := $(shell mvn build-helper:parse-version  help:evaluate -Dexpression=parsedVersion.nextIncrementalVersion -q -DforceStdout)

MINOR_VERSION := $(shell mvn build-helper:parse-version  help:evaluate -Dexpression=parsedVersion.minorVersion -q -DforceStdout)
NEXT_MINOR_VERSION := $(shell mvn build-helper:parse-version  help:evaluate -Dexpression=parsedVersion.nextMinorVersion -q -DforceStdout)

MAJOR_VERSION := $(shell mvn build-helper:parse-version  help:evaluate -Dexpression=parsedVersion.majorVersion -q -DforceStdout)
NEXT_MAJOR_VERSION := $(shell mvn build-helper:parse-version  help:evaluate -Dexpression=parsedVersion.nextMajorVersion -q -DforceStdout)

.PHONY: build clean

clean:
	-@rm -rf test_reference_server.PID test_server.PID

maven_clean:
	mvn clean

maven_compile:
	mvn compile

maven_test:
	mvn test

maven_package:
	mvn package -DskipTests

test_reference_server:
	java -jar $(REF_SERVER_JAR) & echo $$! > test_reference_server.PID
	mvn test
	@kill `cat test_reference_server.PID`

test_server: maven_package
	java -jar $(SERVER_JAR) & echo $$! > test_server.PID
	mvn test
	@kill `cat test_server.PID`

release_patch: build
	$(eval VERSION=$(MAJOR_VERSION).$(MINOR_VERSION).$(NEXT_PATCH_VERSION))
	$(eval SNAPSHOT_VERSION=$(VERSION)-SNAPSHOT)
	mvn -B release:prepare -DskipTests -Darguments=-DskipTests -DreleaseVersion=$(VERSION) -DdevelopmentVersion=$(SNAPSHOT_VERSION)
	mvn release:perform -DskipTests -Darguments=-DskipTests
	mvn release:clean


release_minor: build
	$(eval VERSION=$(MAJOR_VERSION).$(NEXT_MINOR_VERSION).$(PATCH_VERSION))
	$(eval SNAPSHOT_VERSION=$(VERSION)-SNAPSHOT)
	mvn -B release:prepare -DskipTests -Darguments=-DskipTests -DreleaseVersion=$(VERSION) -DdevelopmentVersion=$(SNAPSHOT_VERSION)
	mvn release:perform -DskipTests -Darguments=-DskipTests
	mvn release:clean

release_major: build
	$(eval VERSION=$(NEXT_MAJOR_VERSION).$(MINOR_VERSION).$(PATCH_VERSION))
	$(eval SNAPSHOT_VERSION=$(VERSION)-SNAPSHOT)
	mvn -B release:prepare -DskipTests -Darguments=-DskipTests -DreleaseVersion=$(VERSION) -DdevelopmentVersion=$(SNAPSHOT_VERSION)
	mvn release:perform -DskipTests -Darguments=-DskipTests
	mvn release:clean

build: maven_clean maven_compile test_reference_server test_server clean