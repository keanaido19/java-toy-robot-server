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

PORT_PID := $(shell lsof -t -i:5000)

acceptance_test = mvn test -Dtest="$(1).AcceptanceTests*" -pl ReferenceSocketClient

comma := ,

run_server = @java -jar $(1) $(2) $(3) &
run_server_2x2 = $(call run_server,$(1),-s 2,$(2))
run_server_2x2_obs = $(call run_server_2x2,$(1),-o $(2)$(comma)$(3))

maven_release = mvn build-helper:parse-version -B release:prepare -DskipTests -Darguments=-DskipTests -DreleaseVersion=v$(1) -DdevelopmentVersion=$(2)

all: build maven_package

build: maven_verify maven_compile maven_install test_reference_server test_server

maven_clean:
	mvn clean

maven_install:
	mvn clean install -DskipTests

maven_compile:
	mvn compile

maven_verify:
	mvn verify -DskipTests

maven_test:
	mvn test

maven_package: maven_clean
	mvn package -DskipTests

kill_pid_on_5000:
ifneq ($(strip $(shell lsof -t -i:5000)),)
	$(eval PORT_PID=$(shell lsof -t -i:5000))
	@kill -9 $(PORT_PID)
endif

test_reference_server_world1x1:
	$(MAKE) kill_pid_on_5000
	$(call run_server,$(REF_SERVER_JAR),-v 1)
	$(call acceptance_test,world1x1)
	$(MAKE) kill_pid_on_5000

test_reference_server_world2x2:
	$(MAKE) kill_pid_on_5000
	$(call run_server_2x2,$(REF_SERVER_JAR))
	$(call acceptance_test,world2x2)
	$(MAKE) kill_pid_on_5000

test_reference_server_world2x2_obs_0_1:
	$(MAKE) kill_pid_on_5000
	$(call run_server_2x2_obs,$(REF_SERVER_JAR),0,1)
	$(call acceptance_test,obstacle0_1)
	$(MAKE) kill_pid_on_5000

test_reference_server_world2x2_obs_1_1:
	$(MAKE) kill_pid_on_5000
	$(call run_server_2x2_obs,$(REF_SERVER_JAR),1,1)
	$(call acceptance_test,obstacle1_1)
	$(MAKE) kill_pid_on_5000

test_reference_server_world2x2_obs:
	$(MAKE) test_reference_server_world2x2_obs_0_1
	$(MAKE) test_reference_server_world2x2_obs_1_1

test_reference_server:
	$(MAKE) test_reference_server_world1x1
	$(MAKE) test_reference_server_world2x2
	$(MAKE) test_reference_server_world2x2_obs

test_server_world1x1:
	$(MAKE) kill_pid_on_5000
	mvn exec:java -Dexec.args="-s 1" &
	$(call acceptance_test,world1x1)
	$(MAKE) kill_pid_on_5000

test_server_world2x2:
	$(MAKE) kill_pid_on_5000
	mvn exec:java -Dexec.args="-s 2" &
	$(call acceptance_test,world2x2)
	$(MAKE) kill_pid_on_5000

test_server_world2x2_obs_0_1:
	$(MAKE) kill_pid_on_5000
	mvn exec:java -Dexec.args="-s 2 -o 0,1" &
	$(call acceptance_test,obstacle0_1)
	$(MAKE) kill_pid_on_5000

test_server_world2x2_obs_1_1:
	$(MAKE) kill_pid_on_5000
	mvn exec:java -Dexec.args="-s 2 -o 1,1" &
	$(call acceptance_test,obstacle1_1)
	$(MAKE) kill_pid_on_5000

test_server_world2x2_obs:
	$(MAKE) test_server_world2x2_obs_0_1
	$(MAKE) test_server_world2x2_obs_1_1

test_server:
	mvn test -pl RobotWorldsDomain -pl SocketClient -pl DatabaseConnector -pl ApiServer
	$(MAKE) test_server_world1x1
	$(MAKE) test_server_world2x2
	$(MAKE) test_server_world2x2_obs
	$(call acceptance_test,database)

release_patch: build
	$(eval VERSION=$(MAJOR_VERSION).$(MINOR_VERSION).$(NEXT_PATCH_VERSION))
	$(eval SNAPSHOT_VERSION=$$(VERSION)-SNAPSHOT)
	$(call maven_release,$(VERSION),$(SNAPSHOT_VERSION))
	mvn release:perform -DskipTests -Darguments=-DskipTests
	mvn release:clean
	$(MAKE) docker_release

release_minor: build
	$(eval VERSION=$(MAJOR_VERSION).$(NEXT_MINOR_VERSION).0)
	$(eval SNAPSHOT_VERSION=$$(VERSION)-SNAPSHOT)
	$(call maven_release,$(VERSION),$(SNAPSHOT_VERSION))
	mvn release:perform -DskipTests -Darguments=-DskipTests
	mvn release:clean
	$(MAKE) docker_release

release_major: build
	$(eval VERSION=$(NEXT_MAJOR_VERSION).0.0)
	$(eval SNAPSHOT_VERSION=$$(VERSION)-SNAPSHOT)
	$(call maven_release,$(VERSION),$(SNAPSHOT_VERSION))
	mvn release:perform -DskipTests -Darguments=-DskipTests
	mvn release:clean
	$(MAKE) docker_release

get_major_version = $(shell mvn build-helper:parse-version  help:evaluate -Dexpression=parsedVersion.majorVersion -q -DforceStdout)

get_minor_version = $(shell mvn build-helper:parse-version  help:evaluate -Dexpression=parsedVersion.minorVersion -q -DforceStdout)

get_patch_version = $(shell mvn build-helper:parse-version  help:evaluate -Dexpression=parsedVersion.incrementalVersion -q -DforceStdout)

get_project_version = v$(get_major_version).$(get_minor_version).$(get_patch_version)

get_running_docker_containers = docker container ls -q

docker_arguments = java -jar RobotWorldsServer.jar -p 5050 $(1) $(2)

run_docker_image = docker run -p 5000:5050 robot-worlds-socket-server:latest $(1) & echo "Running docker image..."

kill_docker_containers:
ifneq ($(strip $(shell $(get_running_docker_containers))),)
	docker stop $(shell $(get_running_docker_containers))
endif

test_docker_world1x1:
	$(MAKE) kill_docker_containers
	$(call run_docker_image,$(call docker_arguments))
	$(call acceptance_test,world1x1)
	$(MAKE) kill_docker_containers

test_docker_world2x2:
	$(MAKE) kill_docker_containers
	$(call run_docker_image,$(call docker_arguments,-s 2))
	$(call acceptance_test,world2x2)
	$(MAKE) kill_docker_containers

test_docker_world2x2_obs_0_1:
	$(MAKE) kill_docker_containers
	$(call run_docker_image,$(call docker_arguments,-s 2,-o 0$(comma)1))
	$(call acceptance_test,obstacle0_1)
	$(MAKE) kill_docker_containers

test_docker_world2x2_obs_1_1:
	$(MAKE) kill_docker_containers
	$(call run_docker_image,$(call docker_arguments,-s2,-o 1$(comma)1))
	$(call acceptance_test,obstacle1_1)
	$(MAKE) kill_docker_containers

test_docker_world2x2_obs:
	$(MAKE) test_docker_world2x2_obs_0_1
	$(MAKE) test_docker_world2x2_obs_1_1

test_docker:
	$(MAKE) maven_install
	$(MAKE) kill_pid_on_5000
	$(MAKE) test_docker_world1x1
	$(MAKE) test_docker_world2x2
	$(MAKE) test_docker_world2x2_obs

docker_build: maven_package
	docker compose build

docker_release: docker_build test_docker
	docker login gitlab.wethinkco.de:5050
	docker tag robot-worlds-socket-server:latest gitlab.wethinkco.de:5050/mxomagub021/gerald_lawson:socket-server_$(get_project_version)
	docker tag robot-worlds-api-server:latest gitlab.wethinkco.de:5050/mxomagub021/gerald_lawson:api-server_$(get_project_version)
	docker push gitlab.wethinkco.de:5050/mxomagub021/gerald_lawson:socket-server_$(get_project_version)
	docker push gitlab.wethinkco.de:5050/mxomagub021/gerald_lawson:api-server_$(get_project_version)
