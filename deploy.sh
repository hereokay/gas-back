#!/bin/bash

# Gradle 빌드
echo "Building with Gradle..."
./gradlew build

# Docker 이미지 빌드
echo "Building Docker image..."
docker build -t hereokay/myapp .

# Docker 이미지 푸시
echo "Pushing image to Docker Hub..."
docker push hereokay/myapp

echo "Deployment completed successfully."

