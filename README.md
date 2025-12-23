# 🌍 Global E-Commerce Platform
A scalable, multi-currency e-commerce platform built with Spring Boot that enables customers worldwide to discover, purchase, and receive products with trust and convenience.
## ✨ Key Features
<ul>
<li>User Authentication & Authorization - Secure JWT-based authentication with email and social login support</li>
<li>Multi-Language Product Catalog - Global product discovery with search, filters, and categories</li>
<li>Shopping Cart & Checkout - Seamless shopping experience with discount support</li>
<li>Multi-Currency Payment Processing - Stripe and PayPal integration with secure transactions</li>
<li>Order Management - Complete order tracking and status updates</li>
<li>Admin Dashboard - Comprehensive management interface for products, orders, and customers</li>
</ul>

## 🛠️ Technology Stack
<ul>
<li>Backend Framework: Spring Boot 3.x</li>
<li>Security: Spring Security with JWT</li>
<li>Database: PostgreSQL with Hibernate ORM</li>
<li>Payment Processing: Stripe API integration</li>
<li>API Documentation: Swagger/OpenAPI 3.0</li>
<li>Build Tool: Maven</li>
<li>Testing: JUnit 5, Mockito, Testcontainers</li>
</ul>

## 📋 Prerequisites
Before running this application, ensure you have:
<ul>
<li>Java 17 or higher</li>
<li>Maven 3.6+</li>
<li>PostgreSQL 12+</li>
<li>Stripe account for payment processing</li>
</ul>

## 🚀 Quick Start

<ol>
<li>

 ### Clone the Repository

```
git clone https://github.com/your-username/global-ecommerce-platform.git
cd global-ecommerce-platform
```
</li>
<li>

### Configure Database
you can skip this step if you use Postgres throw Docker
</li>
<li>

### Configure Application Properties

if you use Docker
```
spring.datasource.url=jdbc:postgresql://postgres:5432/globalcommerce

spring.datasource.username=your-db-username

spring.datasource.password=your-db-password
```
if you use local
```
spring.datasource.url=jdbc:postgresql://local:5432/globalcommerce

spring.datasource.username=your-db-username

spring.datasource.password=your-db-password
```

[//]: # ()
[//]: # (stripe.secret-key=your-stripe-secret-key)

[//]: # (stripe.public-key=your-stripe-public-key)

[//]: # ()
[//]: # (jwt.secret=your-jwt-secret-key)

[//]: # (jwt.expiration=86400000)
</li>
<li>

### Build and Run the Application

<ul>
<li>
if you use Docker

use this command in terminal
if it is you first time to run app
```docker-compose up --build```
if you run this app throw docker before use this command
```docker-compose up```
</li>
<li>
if you don't use docker

```
# Build the project
mvn clean package

# Run the application
mvn spring-boot:run
```
the app will start 'http://localhost:8080'
</li>
</ul>
</li>
</ol>

## 🗂️ Project Structure

```
src/
├── main/
│   ├── java/com/ihab/globalcommerce/
│   │   ├── config/          # Configuration classes
│   │   ├── controller/      # REST API controllers
│   │   ├── data/dto/            # Data Transfer Objects
│   │   ├── data/model/         # JPA entities
│   │   ├── data/enums/      # for enumeration values
│   │   ├── data/repo/     # Data access layer
│   │   ├── filter/       # for filtering each request
│   │   ├── service/        # Business logic layer
│   │   └── util/           # Utility classes
│   └── resources/
│       ├── application.yml     # Main configuration
│       ├── liquibase.yml # liquibase configuration
│       └── db/changelog/changelog.sql # migration file
└── test/                    # Test classes
```


[//]: # (here we will add all end point)

sequenceDiagram
autonumber

    actor Customer
    participant FE as Frontend (Web / Mobile)
    participant BE as Backend API (Spring Boot)
    participant PG as Payment Gateway
    participant Bank as Issuing Bank

    Customer ->> FE: Select product & checkout
    FE ->> BE: POST /payments (orderId, amount)
    BE ->> PG: Create PaymentIntent
    PG -->> BE: intent_id + client_secret

    FE ->> PG: Confirm payment (SDK)
    PG ->> Bank: Authorize payment
    Bank -->> PG: Auth result (approve / decline / 3DS)

    alt 3D Secure required
        PG -->> FE: Redirect / challenge
        FE -->> PG: 3DS completed
    end

    PG -->> PG: Process payment

    PG -->> BE: Webhook (payment.succeeded / failed)
    BE ->> BE: Verify signature
    BE ->> BE: Update payment & order status

    FE ->> BE: GET /payments/{id}
    BE -->> FE: Final payment status
    FE -->> Customer: Show success / failure


    