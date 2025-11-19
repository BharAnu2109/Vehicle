# Vehicle Manufacturing and Automobile Application

A comprehensive microservices-based vehicle manufacturing and automobile management system built with modern technologies.

## 🏗️ Architecture Overview

This application follows a microservices architecture pattern with the following components:

### Backend Microservices (Spring Boot - Java 17)

1. **Service Registry (Eureka Server)** - Port 8761
   - Service discovery and registration
   - Monitors health of all microservices

2. **Config Server** - Port 8888
   - Centralized configuration management
   - External configuration support

3. **API Gateway (Spring Cloud Gateway)** - Port 8080
   - Single entry point for all client requests
   - Routing and load balancing
   - Cross-cutting concerns (authentication, logging, rate limiting)

4. **Vehicle Service** - Port 8081
   - CRUD operations for vehicles
   - Vehicle status management
   - Kafka event publishing for vehicle changes

5. **Manufacturing Service** - Port 8082
   - Production order management
   - Production stage tracking
   - Manufacturing workflow automation

6. **Inventory Service** - Port 8083
   - Parts and stock management
   - Low stock alerts
   - Inventory tracking

7. **Order Service** - Port 8084
   - Customer order management
   - Order status tracking
   - Delivery management

### Frontend Applications

1. **Angular 18 Admin Dashboard**
   - Complete vehicle management interface
   - Manufacturing oversight
   - Administrative functions

2. **React Customer Portal**
   - Customer-facing vehicle browsing
   - Order placement and tracking
   - User account management

3. **Vue.js Manufacturing Dashboard**
   - Real-time production monitoring
   - Manufacturing analytics
   - Operator interface

### Infrastructure Components

- **Apache Kafka** - Event-driven messaging
- **Zookeeper** - Kafka coordination
- **Docker** - Containerization
- **Docker Compose** - Multi-container orchestration

## 🚀 Technologies Used

### Backend
- Java 17
- Spring Boot 3.2.0
- Spring Cloud (Eureka, Gateway, Config)
- Spring Data JPA
- Spring Kafka
- H2 Database (in-memory)
- Maven 3.9.11
- Lombok

### Frontend
- Angular 18 (TypeScript)
- React 18 (JavaScript)
- Vue.js 3 (TypeScript)

### DevOps
- Docker
- Docker Compose
- Kafka 7.5.0

## 📋 Prerequisites

- Java 17 or higher
- Node.js 20.x
- npm 10.x
- Maven 3.9+
- Docker and Docker Compose
- Git

## 🛠️ Getting Started

### Option 1: Running with Docker Compose (Recommended)

1. **Clone the repository**
   ```bash
   git clone https://github.com/BharAnu2109/Vehicle.git
   cd Vehicle
   ```

2. **Build and run all services**
   ```bash
   docker-compose up --build
   ```

3. **Access the services**
   - Service Registry: http://localhost:8761
   - API Gateway: http://localhost:8080
   - Kafka: localhost:9092
   - Angular Admin: http://localhost:4200 (after running `cd angular-admin && npm start`)
   - React Portal: http://localhost:3000 (after running `cd react-customer-portal && npm start`)
   - Vue Dashboard: http://localhost:5173 (after running `cd vue-manufacturing-dashboard && npm run dev`)

### Option 2: Running Services Individually

#### Backend Services

1. **Start Service Registry**
   ```bash
   cd backend/service-registry
   mvn spring-boot:run
   ```

2. **Start Config Server**
   ```bash
   cd backend/config-server
   mvn spring-boot:run
   ```

3. **Start Kafka and Zookeeper**
   ```bash
   docker-compose up zookeeper kafka
   ```

4. **Start other microservices**
   ```bash
   # API Gateway
   cd backend/api-gateway && mvn spring-boot:run
   
   # Vehicle Service
   cd backend/vehicle-service && mvn spring-boot:run
   
   # Manufacturing Service
   cd backend/manufacturing-service && mvn spring-boot:run
   
   # Inventory Service
   cd backend/inventory-service && mvn spring-boot:run
   
   # Order Service
   cd backend/order-service && mvn spring-boot:run
   ```

#### Frontend Applications

1. **Angular Admin Dashboard**
   ```bash
   cd angular-admin
   npm install
   npm start
   ```
   Access at: http://localhost:4200

2. **React Customer Portal**
   ```bash
   cd react-customer-portal
   npm install
   npm start
   ```
   Access at: http://localhost:3000

3. **Vue.js Manufacturing Dashboard**
   ```bash
   cd vue-manufacturing-dashboard
   npm install
   npm run dev
   ```
   Access at: http://localhost:5173

## 📡 API Endpoints

All endpoints are accessible through the API Gateway at `http://localhost:8080`

### Vehicle Service
- `GET /api/vehicles` - Get all vehicles
- `GET /api/vehicles/{id}` - Get vehicle by ID
- `GET /api/vehicles/vin/{vin}` - Get vehicle by VIN
- `GET /api/vehicles/make/{make}` - Get vehicles by make
- `GET /api/vehicles/status/{status}` - Get vehicles by status
- `POST /api/vehicles` - Create new vehicle
- `PUT /api/vehicles/{id}` - Update vehicle
- `PATCH /api/vehicles/{id}/status?status={status}` - Update vehicle status
- `DELETE /api/vehicles/{id}` - Delete vehicle

### Manufacturing Service
- `GET /api/manufacturing/orders` - Get all production orders
- `GET /api/manufacturing/orders/{id}` - Get order by ID
- `GET /api/manufacturing/orders/number/{orderNumber}` - Get order by number
- `GET /api/manufacturing/orders/status/{status}` - Get orders by status
- `GET /api/manufacturing/orders/stage/{stage}` - Get orders by stage
- `POST /api/manufacturing/orders` - Create production order
- `PUT /api/manufacturing/orders/{id}` - Update order
- `PATCH /api/manufacturing/orders/{id}/advance?stage={stage}` - Advance production stage
- `DELETE /api/manufacturing/orders/{id}` - Delete order

### Inventory Service
- `GET /api/inventory` - Get all inventory items
- `GET /api/inventory/{id}` - Get item by ID
- `GET /api/inventory/part/{partNumber}` - Get item by part number
- `GET /api/inventory/low-stock` - Get low stock items
- `GET /api/inventory/out-of-stock` - Get out of stock items
- `POST /api/inventory` - Create inventory item
- `PUT /api/inventory/{id}` - Update item
- `PATCH /api/inventory/{id}/adjust-stock?quantity={quantity}` - Adjust stock
- `DELETE /api/inventory/{id}` - Delete item

### Order Service
- `GET /api/orders` - Get all orders
- `GET /api/orders/{id}` - Get order by ID
- `GET /api/orders/number/{orderNumber}` - Get order by number
- `GET /api/orders/customer/{customerId}` - Get orders by customer
- `GET /api/orders/status/{status}` - Get orders by status
- `POST /api/orders` - Create order
- `PUT /api/orders/{id}` - Update order
- `PATCH /api/orders/{id}/status?status={status}` - Update order status
- `DELETE /api/orders/{id}` - Delete order

## 🔄 Kafka Events

### Vehicle Events (Topic: vehicle-events)
- `VEHICLE_CREATED` - Published when a new vehicle is created
- `VEHICLE_UPDATED` - Published when a vehicle is updated
- `VEHICLE_DELETED` - Published when a vehicle is deleted
- `VEHICLE_STATUS_CHANGED` - Published when vehicle status changes

### Production Events (Topic: production-events)
- `PRODUCTION_ORDER_CREATED` - Published when production order is created
- `PRODUCTION_ORDER_UPDATED` - Published when production order is updated
- `PRODUCTION_STAGE_CHANGED` - Published when production stage advances

## 🎯 Complex Scenarios Implemented

1. **Event-Driven Architecture**
   - Kafka integration for asynchronous communication
   - Event publishing on entity changes
   - Decoupled microservices communication

2. **Service Discovery**
   - Dynamic service registration
   - Load balancing
   - Health monitoring

3. **API Gateway Pattern**
   - Centralized routing
   - Request filtering
   - Service-to-service communication

4. **Manufacturing Workflow**
   - Multi-stage production process
   - Production tracking
   - Status management

5. **Inventory Management**
   - Stock level monitoring
   - Automatic low stock detection
   - Reorder level management

6. **Order Processing**
   - Complete order lifecycle
   - Status tracking
   - Customer management

## 🏭 Production Stages

The manufacturing service tracks the following production stages:
1. PLANNING
2. MATERIAL_PROCUREMENT
3. FRAME_ASSEMBLY
4. BODY_ASSEMBLY
5. PAINTING
6. ENGINE_INSTALLATION
7. INTERIOR_ASSEMBLY
8. QUALITY_INSPECTION
9. FINAL_TESTING
10. COMPLETED

## 📊 Vehicle Status Types

- IN_PRODUCTION
- QUALITY_CHECK
- READY_FOR_DELIVERY
- SHIPPED
- DELIVERED
- IN_SERVICE
- MAINTENANCE_REQUIRED
- RETIRED

## 🔐 Security Considerations

- CORS enabled for frontend applications
- H2 console accessible for development
- Service-to-service communication through Gateway
- Environment-based configuration support

## 📝 Development Notes

- All microservices use H2 in-memory database for simplicity
- Kafka is required for vehicle and manufacturing services
- Services register with Eureka on startup
- API Gateway provides unified access point

## 🚢 Deployment

### Docker Deployment
```bash
docker-compose up -d
```

### Azure Deployment (Coming Soon)
Azure deployment configurations are being prepared with:
- Azure Kubernetes Service (AKS)
- Azure Container Registry (ACR)
- Azure Service Bus (alternative to Kafka)
- Azure Database for PostgreSQL

## 🧪 Testing

Build and test all services:
```bash
cd backend
mvn clean test
```

Test individual service:
```bash
cd backend/vehicle-service
mvn test
```

## 📈 Monitoring

- Eureka Dashboard: http://localhost:8761
- H2 Console: http://localhost:8081/h2-console (for each service)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 👥 Authors

- BharAnu2109

## 🙏 Acknowledgments

- Spring Boot team for excellent microservices framework
- Angular, React, and Vue.js communities
- Apache Kafka for event streaming platform
