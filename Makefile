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

build: maven_clean maven_verify maven_compile test_reference_server test_server clean

clean:
	-@rm -rf test_reference_server.PID test_server.PID

maven_clean:
	mvn clean

maven_compile:
	mvn compile

maven_verify:
	mvn verify -DskipTests

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
	$(MAKE) docker_release

release_minor: build
	$(eval VERSION=$(MAJOR_VERSION).$(NEXT_MINOR_VERSION).0)
	$(eval SNAPSHOT_VERSION=$$(VERSION)-SNAPSHOT)
	mvn build-helper:parse-version -B release:prepare -DskipTests -Darguments=-DskipTests -DreleaseVersion=v$(VERSION) -DdevelopmentVersion=$(SNAPSHOT_VERSION)
	mvn release:perform -DskipTests -Darguments=-DskipTests
	mvn release:clean
	$(MAKE) docker_release

release_major: build
	$(eval VERSION=$(NEXT_MAJOR_VERSION).0.0)
	$(eval SNAPSHOT_VERSION=$$(VERSION)-SNAPSHOT)
	mvn build-helper:parse-version -B release:prepare -DskipTests -Darguments=-DskipTests -DreleaseVersion=v$(VERSION) -DdevelopmentVersion=$(SNAPSHOT_VERSION)
	mvn release:perform -DskipTests -Darguments=-DskipTests
	mvn release:clean
	$(MAKE) docker_release

get_major_version = $(shell mvn build-helper:parse-version  help:evaluate -Dexpression=parsedVersion.majorVersion -q -DforceStdout)

get_minor_version = $(shell mvn build-helper:parse-version  help:evaluate -Dexpression=parsedVersion.minorVersion -q -DforceStdout)

get_patch_version = $(shell mvn build-helper:parse-version  help:evaluate -Dexpression=parsedVersion.incrementalVersion -q -DforceStdout)

get_project_version = v$(get_major_version).$(get_minor_version).$(get_patch_version)

docker_release:
	docker build -t robot-worlds-server:$(get_project_version) .
	$(MAKE) kill_docker_containers
ifneq ($(strip $(shell lsof -t -i:5000)),)
	$(eval PORT_PID=$(shell lsof -t -i:5000))
	$(shell kill -9 $(PORT_PID))
endif
	docker run -p 5000:5050 robot-worlds-server:$(get_project_version) & echo "Running docker image..."
	mvn test
	$(MAKE) kill_docker_containers
	docker login gitlab.wethinkco.de:5050
	docker tag robot-worlds-server:$(get_project_version) gitlab.wethinkco.de:5050/mxomagub021/gerald_lawson:$(get_project_version)
	docker push gitlab.wethinkco.de:5050/mxomagub021/gerald_lawson:$(get_project_version)