#!/bin/bash

# Vehicle Manufacturing Application Startup Script
# This script starts all components of the application

echo "================================================"
echo "Vehicle Manufacturing Application Startup"
echo "================================================"
echo ""

# Function to check if a command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Check prerequisites
echo "Checking prerequisites..."
if ! command_exists java; then
    echo "❌ Java is not installed. Please install Java 17 or higher."
    exit 1
fi

if ! command_exists mvn; then
    echo "❌ Maven is not installed. Please install Maven 3.9 or higher."
    exit 1
fi

if ! command_exists node; then
    echo "❌ Node.js is not installed. Please install Node.js 20 or higher."
    exit 1
fi

if ! command_exists docker; then
    echo "❌ Docker is not installed. Please install Docker."
    exit 1
fi

echo "✅ All prerequisites are met!"
echo ""

# Choose startup method
echo "Select startup method:"
echo "1. Docker Compose (Recommended - starts all services in containers)"
echo "2. Local Development (starts services on your machine)"
read -p "Enter your choice (1 or 2): " choice

case $choice in
    1)
        echo ""
        echo "Starting with Docker Compose..."
        echo "This will build and start all microservices, Kafka, and Zookeeper"
        echo ""
        docker-compose up --build
        ;;
    2)
        echo ""
        echo "Starting Local Development Environment..."
        echo ""
        
        # Start Kafka and Zookeeper in Docker
        echo "1. Starting Kafka and Zookeeper..."
        docker-compose up -d zookeeper kafka
        sleep 10
        
        # Start Service Registry
        echo "2. Starting Service Registry..."
        cd backend/service-registry
        mvn spring-boot:run > ../../logs/service-registry.log 2>&1 &
        cd ../..
        sleep 20
        
        # Start Config Server
        echo "3. Starting Config Server..."
        cd backend/config-server
        mvn spring-boot:run > ../../logs/config-server.log 2>&1 &
        cd ../..
        sleep 15
        
        # Start API Gateway
        echo "4. Starting API Gateway..."
        cd backend/api-gateway
        mvn spring-boot:run > ../../logs/api-gateway.log 2>&1 &
        cd ../..
        sleep 15
        
        # Start Vehicle Service
        echo "5. Starting Vehicle Service..."
        cd backend/vehicle-service
        mvn spring-boot:run > ../../logs/vehicle-service.log 2>&1 &
        cd ../..
        sleep 10
        
        # Start Manufacturing Service
        echo "6. Starting Manufacturing Service..."
        cd backend/manufacturing-service
        mvn spring-boot:run > ../../logs/manufacturing-service.log 2>&1 &
        cd ../..
        sleep 10
        
        # Start Inventory Service
        echo "7. Starting Inventory Service..."
        cd backend/inventory-service
        mvn spring-boot:run > ../../logs/inventory-service.log 2>&1 &
        cd ../..
        sleep 10
        
        # Start Order Service
        echo "8. Starting Order Service..."
        cd backend/order-service
        mvn spring-boot:run > ../../logs/order-service.log 2>&1 &
        cd ../..
        sleep 10
        
        echo ""
        echo "✅ All backend services are starting..."
        echo ""
        echo "To start frontend applications, run:"
        echo "  Angular: cd angular-admin && npm install && npm start"
        echo "  React:   cd react-customer-portal && npm install && npm start"
        echo "  Vue:     cd vue-manufacturing-dashboard && npm install && npm run dev"
        echo ""
        echo "Service URLs:"
        echo "  - Service Registry: http://localhost:8761"
        echo "  - API Gateway:      http://localhost:8080"
        echo "  - Angular Admin:    http://localhost:4200"
        echo "  - React Portal:     http://localhost:3000"
        echo "  - Vue Dashboard:    http://localhost:5173"
        ;;
    *)
        echo "Invalid choice. Exiting."
        exit 1
        ;;
esac
