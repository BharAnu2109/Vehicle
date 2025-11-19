# Vehicle Manufacturing and Automobile Application 🚗

A comprehensive, production-ready microservices-based vehicle manufacturing and automobile management system built with modern technologies.

## 🌟 Overview

This application demonstrates a complete enterprise-grade architecture for vehicle manufacturing operations, featuring:
- **7 Spring Boot Microservices** (Java 17)
- **3 Frontend Applications** (Angular 18, React, Vue.js 3)
- **Event-Driven Architecture** (Apache Kafka)
- **Service Discovery & Load Balancing** (Eureka)
- **API Gateway** (Spring Cloud Gateway)
- **Containerization** (Docker & Docker Compose)
- **Cloud Deployment** (Azure with Kubernetes)

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    Frontend Applications                     │
│  ┌──────────────┐  ┌──────────────┐  ┌─────────────────┐  │
│  │ Angular 18   │  │   React 18   │  │   Vue.js 3      │  │
│  │ Admin Portal │  │ Customer App │  │ Manufacturing   │  │
│  └──────┬───────┘  └──────┬───────┘  └────────┬────────┘  │
└─────────┼──────────────────┼───────────────────┼───────────┘
          │                  │                   │
          └──────────────────┼───────────────────┘
                             │
                   ┌─────────▼──────────┐
                   │   API Gateway      │
                   │   (Port 8080)      │
                   └─────────┬──────────┘
                             │
          ┌──────────────────┼───────────────────┐
          │                  │                   │
  ┌───────▼────────┐  ┌─────▼──────┐  ┌────────▼─────────┐
  │ Vehicle Service│  │Manufacturing│  │ Inventory Service│
  │  (Port 8081)   │  │   Service   │  │  (Port 8083)     │
  └────────┬───────┘  │ (Port 8082) │  └──────────────────┘
           │          └─────┬───────┘
           │                │          ┌───────────────────┐
           └────────────────┼──────────┤  Order Service    │
                            │          │  (Port 8084)      │
                   ┌────────▼────────┐ └───────────────────┘
                   │  Apache Kafka   │
                   │  (Port 9092)    │
                   └─────────────────┘
```

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Node.js 20+
- Docker & Docker Compose
- Maven 3.9+

### Running with Docker Compose (Easiest)

```bash
# Clone the repository
git clone https://github.com/BharAnu2109/Vehicle.git
cd Vehicle

# Start all services
docker-compose up --build

# Access services:
# - Service Registry: http://localhost:8761
# - API Gateway: http://localhost:8080
# - Angular Admin: http://localhost:4200
# - React Portal: http://localhost:3000
# - Vue Dashboard: http://localhost:5173
```

## 📦 What's Included

### Backend Microservices
1. **Service Registry** (Eureka) - Service discovery and health monitoring
2. **Config Server** - Centralized configuration management
3. **API Gateway** - Unified entry point with routing
4. **Vehicle Service** - Vehicle CRUD operations with Kafka events
5. **Manufacturing Service** - Production order management
6. **Inventory Service** - Parts and stock management
7. **Order Service** - Customer order processing

### Frontend Applications
1. **Angular 18 Admin Dashboard** - Complete administrative interface
2. **React Customer Portal** - Customer-facing application
3. **Vue.js Manufacturing Dashboard** - Real-time production monitoring

### Infrastructure
- **Apache Kafka** - Event streaming for microservices communication
- **Zookeeper** - Kafka coordination
- **Docker** - Containerization for all services
- **Kubernetes** - Production deployment manifests for Azure

## 🔥 Key Features

### Complex Scenarios Implemented

1. **Event-Driven Architecture**
   - Kafka integration across services
   - Asynchronous event publishing
   - Real-time updates

2. **Microservices Patterns**
   - Service Discovery (Eureka)
   - API Gateway
   - Circuit Breaker (ready)
   - Load Balancing

3. **Manufacturing Workflow**
   - 10-stage production pipeline
   - Progress tracking
   - Quality control integration

4. **Inventory Management**
   - Real-time stock tracking
   - Low stock alerts
   - Automatic reorder points

5. **Order Processing**
   - Complete order lifecycle
   - Customer management
   - Delivery tracking

## 📚 Documentation

- [Detailed Documentation](./README-DETAILED.md) - Complete setup and API documentation
- [API Endpoints](./README-DETAILED.md#-api-endpoints) - All REST API endpoints
- [Kafka Events](./README-DETAILED.md#-kafka-events) - Event specifications

## 🏭 Production Stages

The system tracks vehicles through these manufacturing stages:
1. PLANNING → 2. MATERIAL_PROCUREMENT → 3. FRAME_ASSEMBLY → 
4. BODY_ASSEMBLY → 5. PAINTING → 6. ENGINE_INSTALLATION → 
7. INTERIOR_ASSEMBLY → 8. QUALITY_INSPECTION → 
9. FINAL_TESTING → 10. COMPLETED

## ☁️ Azure Deployment

Kubernetes manifests and Azure Pipeline configurations included for:
- Azure Kubernetes Service (AKS)
- Azure Container Registry (ACR)
- Azure Service Bus (Kafka alternative)
- Azure Database for PostgreSQL

```bash
# Deploy to AKS
kubectl apply -f kubernetes/namespace.yml
kubectl apply -f kubernetes/deployment.yml
kubectl apply -f kubernetes/service.yml
```

## 🛠️ Technology Stack

**Backend:**
- Java 17
- Spring Boot 3.2.0
- Spring Cloud (Eureka, Gateway, Config)
- Spring Data JPA
- Spring Kafka
- Maven

**Frontend:**
- Angular 18 with TypeScript
- React 18 with JavaScript
- Vue.js 3 with TypeScript

**DevOps:**
- Docker & Docker Compose
- Kubernetes
- Azure DevOps Pipelines
- Apache Kafka 7.5.0

## 📊 Monitoring & Health

- Eureka Dashboard: `http://localhost:8761`
- H2 Consoles: Available on each service
- Actuator endpoints: `/actuator/health` on all services

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is licensed under the MIT License.

## 👥 Author

**BharAnu2109** - [GitHub Profile](https://github.com/BharAnu2109)

---

⭐ **Star this repository if you find it helpful!**
