#!/usr/bin/env bash

DOCKERFILE_PATH=Dockerfile
IMAGE_NAME=locohazalcast
DOCKER_REPO=blackforestsolutions
DOCKER_TAG=0.0.1

docker login

docker build --build-arg DOCKER_TAG=0.0.1 -f $DOCKERFILE_PATH -t $IMAGE_NAME .

docker tag $SOURCE_COMMIT $DOCKER_REPO/$IMAGE_NAME:0.0.1

docker push $DOCKER_REPO/$IMAGE_NAME:0.0.1