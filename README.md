# Employee Management API

[![CI](https://github.com/GSujaykumar/employee-management-jenkins/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/GSujaykumar/employee-management-jenkins/actions/workflows/ci.yml)
![Last Commit](https://img.shields.io/github/last-commit/GSujaykumar/employee-management-jenkins?logo=github)
![Top Language](https://img.shields.io/github/languages/top/GSujaykumar/employee-management-jenkins?logo=openjdk&logoColor=white)

### Tech Stack
![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-6DB33F?logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?logo=apache-maven&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-1.18.46-BC4521)

### DevOps & Tooling
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?logo=docker&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-CI-2088FF?logo=github-actions&logoColor=white)
![Jenkins](https://img.shields.io/badge/Jenkins-Planned-D24939?logo=jenkins&logoColor=white)
![JUnit 5](https://img.shields.io/badge/JUnit-5-25A162?logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-Tests-78A641)

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