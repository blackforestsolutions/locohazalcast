# locohazalcast
This repo describes the hazelcast distributed cache for loco

callable at browser at http://localhost:8080/hazelcast-mancenter

#How to start:

got to the base target folder of this project

execute:

    mvn clean install docker:build
    docker-compose -f src/main/docker/hazelcast.yml up
    docker-compose -f src/main/docker/hazelcast.yml ps
    docker-compose -f src/main/docker/hazelcast.yml down
