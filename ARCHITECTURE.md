# Vehicle Manufacturing Application - Architecture Documentation

## System Architecture

### Overview
This application follows a microservices architecture pattern with event-driven communication, service discovery, and API gateway patterns.

## Architecture Layers

### 1. Presentation Layer (Frontend)

#### Angular 18 Admin Dashboard
- **Purpose**: Administrative interface for system management
- **Port**: 4200
- **Key Features**:
  - Vehicle management (CRUD operations)
  - Manufacturing oversight
  - Dashboard with analytics
  - Real-time updates

#### React Customer Portal
- **Purpose**: Customer-facing application
- **Port**: 3000
- **Key Features**:
  - Vehicle browsing and search
  - Order placement
  - Order tracking
  - User account management

#### Vue.js Manufacturing Dashboard
- **Purpose**: Production monitoring and control
- **Port**: 5173
- **Key Features**:
  - Real-time production tracking
  - Manufacturing analytics
  - Operator interface
  - Stage management

### 2. Gateway Layer

#### API Gateway (Spring Cloud Gateway)
- **Port**: 8080
- **Responsibilities**:
  - Routing requests to appropriate microservices
  - Load balancing
  - Request/response transformation
  - Security enforcement
  - Rate limiting
  - Logging and monitoring

**Routing Configuration**:
```yaml
/api/vehicles/**       → vehicle-service
/api/manufacturing/** → manufacturing-service
/api/inventory/**     → inventory-service
/api/orders/**        → order-service
```

### 3. Service Layer (Microservices)

#### Service Registry (Eureka Server)
- **Port**: 8761
- **Purpose**: Service discovery and registration
- **Key Features**:
  - Dynamic service registration
  - Health monitoring
  - Load balancing information
  - Service metadata management

#### Config Server
- **Port**: 8888
- **Purpose**: Centralized configuration management
- **Key Features**:
  - External configuration
  - Environment-specific configs
  - Dynamic configuration refresh
  - Version control for configs

#### Vehicle Service
- **Port**: 8081
- **Database**: H2 (in-memory)
- **Responsibilities**:
  - Vehicle lifecycle management
  - Vehicle CRUD operations
  - VIN management
  - Status tracking
  - Kafka event publishing

**Domain Model**:
```
Vehicle {
  id, vin, make, model, year, color, type,
  engineType, transmission, price, status,
  manufacturingDate, createdAt, updatedAt
}
```

**Status Workflow**:
```
IN_PRODUCTION → QUALITY_CHECK → READY_FOR_DELIVERY → 
SHIPPED → DELIVERED → IN_SERVICE → MAINTENANCE_REQUIRED → RETIRED
```

#### Manufacturing Service
- **Port**: 8082
- **Database**: H2 (in-memory)
- **Responsibilities**:
  - Production order management
  - Manufacturing workflow orchestration
  - Stage progression tracking
  - Production analytics

**Domain Model**:
```
ProductionOrder {
  id, orderNumber, vehicleVin, vehicleModel, vehicleMake,
  currentStage, status, quantity, startDate,
  expectedCompletionDate, actualCompletionDate,
  assignedLine, completionPercentage, notes
}
```

**Production Stages**:
1. PLANNING (5%)
2. MATERIAL_PROCUREMENT (15%)
3. FRAME_ASSEMBLY (30%)
4. BODY_ASSEMBLY (45%)
5. PAINTING (60%)
6. ENGINE_INSTALLATION (75%)
7. INTERIOR_ASSEMBLY (85%)
8. QUALITY_INSPECTION (95%)
9. FINAL_TESTING (98%)
10. COMPLETED (100%)

#### Inventory Service
- **Port**: 8083
- **Database**: H2 (in-memory)
- **Responsibilities**:
  - Parts inventory management
  - Stock level monitoring
  - Reorder point management
  - Low stock alerts

**Domain Model**:
```
InventoryItem {
  id, partNumber, partName, category, description,
  quantityInStock, reorderLevel, maxStockLevel,
  supplier, unitPrice, location, status,
  lastRestocked, createdAt, updatedAt
}
```

#### Order Service
- **Port**: 8084
- **Database**: H2 (in-memory)
- **Responsibilities**:
  - Customer order management
  - Order lifecycle tracking
  - Delivery coordination
  - Customer relationship management

**Domain Model**:
```
Order {
  id, orderNumber, customerId, customerName,
  customerEmail, customerPhone, vehicleVin,
  vehicleModel, vehicleMake, vehicleYear,
  vehicleColor, totalPrice, depositAmount,
  status, deliveryAddress, orderDate,
  expectedDeliveryDate, actualDeliveryDate, notes
}
```

### 4. Messaging Layer

#### Apache Kafka
- **Port**: 9092
- **Purpose**: Event-driven communication between microservices

**Topics**:
1. **vehicle-events**
   - VEHICLE_CREATED
   - VEHICLE_UPDATED
   - VEHICLE_DELETED
   - VEHICLE_STATUS_CHANGED

2. **production-events**
   - PRODUCTION_ORDER_CREATED
   - PRODUCTION_ORDER_UPDATED
   - PRODUCTION_STAGE_CHANGED

**Event Flow Example**:
```
Vehicle Service → Kafka (vehicle-events) → Manufacturing Service
                                        → Inventory Service
                                        → Order Service
```

## Design Patterns Implemented

### 1. Microservices Pattern
- Independent, loosely coupled services
- Each service has its own database
- Service-specific business logic

### 2. API Gateway Pattern
- Single entry point for clients
- Request routing and aggregation
- Cross-cutting concerns

### 3. Service Discovery Pattern
- Dynamic service registration
- Client-side load balancing
- Health monitoring

### 4. Event-Driven Architecture
- Asynchronous communication
- Event sourcing capabilities
- Loose coupling between services

### 5. Database per Service
- Each microservice has its own database
- Data isolation and independence
- Polyglot persistence support

### 6. Circuit Breaker Pattern (Ready for implementation)
- Fault tolerance
- Graceful degradation
- Fallback mechanisms

## Communication Patterns

### Synchronous Communication
- REST APIs via API Gateway
- Service-to-service HTTP calls (minimal)
- Request/Response pattern

### Asynchronous Communication
- Kafka event publishing
- Event-driven updates
- Fire-and-forget pattern

## Scalability Considerations

### Horizontal Scaling
- Stateless microservices
- Multiple instances per service
- Load balancing via Eureka and API Gateway

### Data Partitioning
- Service-specific databases
- Kafka topic partitioning
- Sharding capabilities

### Caching Strategy
- Service-level caching (can be implemented)
- API Gateway caching
- Database query caching

## Security Architecture

### Current Implementation
- CORS enabled for frontend communication
- Service-to-service communication via API Gateway
- Environment-based configuration

### Recommended Enhancements
- OAuth2/JWT authentication
- API key management
- Role-based access control (RBAC)
- Service mesh (Istio) for mTLS

## Deployment Architecture

### Local Development
```
Developer Machine
├── Backend Services (Maven)
├── Frontend Apps (npm)
├── Kafka (Docker)
└── Zookeeper (Docker)
```

### Docker Compose
```
Docker Host
├── Containers
│   ├── zookeeper
│   ├── kafka
│   ├── service-registry
│   ├── config-server
│   ├── api-gateway
│   ├── vehicle-service
│   ├── manufacturing-service
│   ├── inventory-service
│   └── order-service
└── Networks
    └── vehicle-network
```

### Azure Kubernetes Service (AKS)
```
Azure Cloud
├── AKS Cluster
│   ├── Namespace: vehicle-manufacturing
│   ├── Deployments (7 services)
│   ├── Services (Load Balancer + ClusterIP)
│   └── Pods (auto-scaling)
├── Azure Container Registry
├── Azure Service Bus (Kafka alternative)
└── Azure Database for PostgreSQL
```

## Monitoring and Observability

### Health Checks
- Spring Boot Actuator endpoints
- Eureka health monitoring
- Kubernetes liveness/readiness probes

### Logging
- Centralized logging (recommended: ELK stack)
- Service-specific log files
- Request/response logging at API Gateway

### Metrics
- Service-level metrics
- JVM metrics
- Custom business metrics

### Tracing
- Distributed tracing (recommended: Zipkin/Sleuth)
- Request correlation IDs
- End-to-end transaction tracking

## Disaster Recovery

### Backup Strategy
- Database backups
- Configuration backups
- Event replay from Kafka

### High Availability
- Multiple service instances
- Load balancing
- Circuit breakers
- Fallback mechanisms

## Technology Decisions

### Why Spring Boot?
- Mature ecosystem
- Built-in microservices support
- Easy integration with Spring Cloud
- Excellent documentation

### Why Kafka?
- High throughput event streaming
- Scalable and distributed
- Event replay capabilities
- Industry standard for microservices

### Why Eureka?
- Native Spring Cloud integration
- Easy service discovery
- Client-side load balancing
- Self-healing capabilities

### Why Multiple Frontend Frameworks?
- Demonstrates polyglot frontend architecture
- Different use cases (admin, customer, manufacturing)
- Technology diversity showcase

## Future Enhancements

1. **Database Migration**
   - Move from H2 to PostgreSQL/MongoDB
   - Implement database migrations (Flyway/Liquibase)

2. **Security Enhancement**
   - Implement OAuth2/JWT
   - Add API authentication
   - Service mesh integration

3. **Monitoring**
   - Integrate Prometheus and Grafana
   - Add distributed tracing (Zipkin)
   - Implement ELK stack

4. **Resilience**
   - Circuit breaker implementation
   - Retry mechanisms
   - Rate limiting

5. **CI/CD**
   - Automated testing
   - Continuous deployment
   - Blue-green deployment

6. **Advanced Features**
   - GraphQL API
   - WebSocket for real-time updates
   - Machine learning integration for predictive maintenance
