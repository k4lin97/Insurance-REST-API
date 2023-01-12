# Insurance REST API

REST API made for insuring cars. It is possible to create policies with all necessary components, such as clients, cars, covers etc.

## Table of contents
* [Technologies](#technologies)
* [Setup](#setup)
* [Project details](#project-details)
* [TODO](#todo)

## Technologies
Technologies used:
* Java
* Spring Boot
* Hibernate
* Maven
* Spring Security
* JUnit
* Mockito

## Setup
By default API is using MS SQL database with the following properties:
```sh
databaseName = InsuranceDB
```
```sh
username = INSURANCE_LOGIN
```
```sh
password = insurance_login
```

In order to use some other database you have to provide credentials in a file:
```sh
src\main\resources\application.properties
```
To run the application a database should be up. Then run the following command in a terminal:
```sh
./mvnw spring-boot:run
```
App will be up with de default endpoint:
```sh
localhost:8080/api/v1/
```

## Project details
Insurance REST API was developed to register policies, allowing to insurance a car. A policy consists of a couple different components:
* client - person insuring his car
* address - client's address
* car - client's insured car
* cover - cover applied to a car

Address can only be of type specified in a database. Only user with admin credentials can create/update/delete address types. Client must have a valid address. Car is an object a client wants to insure. For an insured car it is possible to only select a valid cover. All that information is included under a policy.

Endpoints:
* ```localhost:8080/api/v1/addresses/types```
* ```localhost:8080/api/v1/addresses```
* ```localhost:8080/api/v1/clients```
* ```localhost:8080/api/v1/cars```
* ```localhost:8080/api/v1/covers```
* ```localhost:8080/api/v1/policies```

API was secured with basic authorization and two in memory users - agent and admin. Admin is able to access all endpoints, where agent cannot create/update/delete address types. Credentials:

Admin:
```sh
username = admin
```
```sh
password = admin
```
Agent:
```sh
username = agent
```
```sh
password = agent
```

## TODO
Currently working on / wanting to add in the future:
* Add Data Transfer Objects layer.
* Develop new features, such as claims, non-crud operations etc.
* Implement more business logic, satisfying real world insurance scenarios.
* Create documentation with OpenAPI.
* Develop better security, such as database-backed authentication etc.

