# Micronaut, Quarkus, Spring Boot RealWorld Example App

> ### Java codebase containing real world examples (CRUD, auth, advanced patterns, etc) that adheres to the [RealWorld](https://github.com/gothinkster/realworld) spec and API.

This codebase was created to demonstrate a fully fledged fullstack application built with Java including CRUD
operations, authentication, routing, pagination, and more.

We've gone to great lengths to adhere to the framework's community style guides & best practices.

For more information on how to this works with other frontends/backends, head over to
the [RealWorld](https://github.com/gothinkster/realworld) repo.

## How it works

This is a Java based implementation of the [RealWorld](https://github.com/gothinkster/realworld) in different
frameworks. Currently, the following frameworks are implemented:

* [Quarkus](quarkus)
* [Spring Boot](spring-boot)

This application tries to use as much common code as possible - this common code is located under [base](base).

## Getting started

### Prerequisites

You'll need [OpenJDK 17](https://adoptium.net/) and a [current Maven version](https://maven.apache.org/download.cgi)
installed.

### Quarkus

Run the following command to run the application:

```shell
mvn install
mvn quarkus:dev -pl :realworld-example-app-quarkus 
```

Then you can access the application under http://localhost:8080.

### Spring Boot

```shell
mvn install
mvn spring-boot:run -pl :realworld-example-app-spring-boot 
```

Then you can access the application under http://localhost:8080.
