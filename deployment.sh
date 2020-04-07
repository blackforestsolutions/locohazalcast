#!/usr/bin/env bash

DOCKERFILE_PATH=Dockerfile
IMAGE_NAME=locohazalcast
DOCKER_REPO=blackforestsolutions

docker login --username ${USERNAME} --password ${PASSWORD}

docker build --build-arg DOCKER_TAG=$SOURCE_COMMIT -f $DOCKERFILE_PATH -t $IMAGE_NAME .

docker tag $SOURCE_COMMIT $DOCKER_REPO/$IMAGE_NAME:$SOURCE_COMMIT

docker push $DOCKER_REPO/$IMAGE_NAME:$SOURCE_COMMIT