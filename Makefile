SERVER_JAR = $(wildcard target/SocketServer*.jar)
REF_SERVER_JAR = $(wildcard libs/reference-server*.jar)

PATCH_VERSION := \$$$${parsedVersion.incrementalVersion}
NEXT_PATCH_VERSION := \$$$${parsedVersion.nextIncrementalVersion}

MINOR_VERSION := \$$$${parsedVersion.minorVersion}
NEXT_MINOR_VERSION := \$$$${parsedVersion.nextMinorVersion}

MAJOR_VERSION := \$$$${parsedVersion.majorVersion}
NEXT_MAJOR_VERSION := \$$$${parsedVersion.nextMajorVersion}

VERSION = $(MAJOR_VERSION).$(MINOR_VERSION).$(PATCH_VERSION)
SNAPSHOT_VERSION = $$(VERSION)-SNAPSHOT

PORT_PID := $(shell lsof -t -i:5000))

.PHONY: build clean

build: maven_clean maven_compile test_reference_server test_server clean

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
ifneq ($(strip $(shell lsof -t -i:5000)),)
	$(eval PORT_PID=$(shell lsof -t -i:5000))
	$(shell kill -9 $(PORT_PID))
endif
	@java -jar $(REF_SERVER_JAR) & echo $$! > test_reference_server.PID
	mvn test
	@kill `cat test_reference_server.PID`

test_server: maven_package
ifneq ($(strip $(shell lsof -t -i:5000)),)
	$(eval PORT_PID=$(shell lsof -t -i:5000))
	$(shell kill -9 $(PORT_PID))
endif
	@java -jar $(SERVER_JAR) & echo $$! > test_server.PID
	mvn test
	@kill `cat test_server.PID`

release_patch: build
	$(eval VERSION=$(MAJOR_VERSION).$(MINOR_VERSION).$(NEXT_PATCH_VERSION))
	$(eval SNAPSHOT_VERSION=$$(VERSION)-SNAPSHOT)
	mvn build-helper:parse-version -B release:prepare -DskipTests -Darguments=-DskipTests -DreleaseVersion=v$(VERSION) -DdevelopmentVersion=$(SNAPSHOT_VERSION)
	mvn release:perform -DskipTests -Darguments=-DskipTests
	mvn release:clean

release_minor: build
	$(eval VERSION=$(MAJOR_VERSION).$(NEXT_MINOR_VERSION).$(PATCH_VERSION))
	$(eval SNAPSHOT_VERSION=$$(VERSION)-SNAPSHOT)
	mvn build-helper:parse-version -B release:prepare -DskipTests -Darguments=-DskipTests -DreleaseVersion=v$(VERSION) -DdevelopmentVersion=$(SNAPSHOT_VERSION)
	mvn release:perform -DskipTests -Darguments=-DskipTests
	mvn release:clean

release_major: build
	$(eval VERSION=$(NEXT_MAJOR_VERSION).$(MINOR_VERSION).$(PATCH_VERSION))
	$(eval SNAPSHOT_VERSION=$$(VERSION)-SNAPSHOT)
	mvn build-helper:parse-version -B release:prepare -DskipTests -Darguments=-DskipTests -DreleaseVersion=v$(VERSION) -DdevelopmentVersion=$(SNAPSHOT_VERSION)
	mvn release:perform -DskipTests -Darguments=-DskipTests
	mvn release:clean
