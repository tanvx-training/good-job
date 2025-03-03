#!/bin/bash

# Script to build and push Docker images for all microservices using Google Jib

# Set variables
REGISTRY=${1:-"docker.io"}  # Default to DockerHub if no registry specified
VERSION=${2:-"1.0-SNAPSHOT"}  # Default to project version if not specified

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Function to build and push a service
build_service() {
    local service=$1
    echo -e "${YELLOW}Building ${service}...${NC}"
    
    # Check if service directory exists
    if [ ! -d "$service" ]; then
        echo -e "${RED}Service directory $service does not exist. Skipping.${NC}"
        return 1
    fi
    
    # Check if pom.xml exists
    if [ ! -f "$service/pom.xml" ]; then
        echo -e "${RED}pom.xml not found in $service. Skipping.${NC}"
        return 1
    fi
    
    # Build with Jib
    echo "Building Docker image for $service using Jib..."
    cd $service
    
    # Build to local Docker daemon
    if [ "$3" == "local" ]; then
        mvn clean compile jib:dockerBuild -Djib.to.image=$REGISTRY/goodjob/$service:$VERSION
    # Push to registry
    else
        mvn clean compile jib:build -Djib.to.image=$REGISTRY/goodjob/$service:$VERSION
    fi
    
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}Successfully built and pushed $service image${NC}"
    else
        echo -e "${RED}Failed to build $service image${NC}"
        cd ..
        return 1
    fi
    
    cd ..
    return 0
}

# Main script

echo -e "${YELLOW}Starting build process for all microservices...${NC}"
echo -e "Registry: ${REGISTRY}"
echo -e "Version: ${VERSION}"
echo -e "Build mode: ${3:-"push to registry"}"
echo ""

# Build infrastructure services first
services=(
    "eureka-server"
    "config-server"
    "api-gateway"
    "authorization-service"
)

# Then build domain services
domain_services=(
    "candidate-service"
    "company-service"
    "job-service"
    "posting-service"
    "skill-service"
    "industry-service"
    "speciality-service"
    "benefit-service"
    "notification-service"
    "mail-service"
)

# Build infrastructure services
echo -e "${YELLOW}Building infrastructure services...${NC}"
for service in "${services[@]}"; do
    build_service $service $REGISTRY $3
done

# Build domain services
echo -e "${YELLOW}Building domain services...${NC}"
for service in "${domain_services[@]}"; do
    build_service $service $REGISTRY $3
done

echo -e "${GREEN}Build process completed!${NC}" 