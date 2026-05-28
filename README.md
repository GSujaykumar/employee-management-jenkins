# Employee Management API

[![CI](https://github.com/GSujaykumar/employee-management-jenkins/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/GSujaykumar/employee-management-jenkins/actions/workflows/ci.yml)

A Spring Boot REST API for managing employees, backed by MySQL, with full CRUD endpoints, JUnit/Mockito unit tests, Spring Boot integration tests against a real MySQL service container, and a CI pipeline running on GitHub Actions.

## Features
- Spring Boot CRUD APIs
- MySQL Database
- Docker Support
- JUnit Testing

## Run Application

### Build
mvn clean install

### Run
mvn spring-boot:run

## Docker Commands

### Build Docker Image
docker build -t employee-management .

### Run Docker Compose
docker-compose up --build