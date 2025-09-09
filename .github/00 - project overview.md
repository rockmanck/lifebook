# Project Overview

Lifebook is a monolithic web application built with Spring Boot, providing a comprehensive platform for personal data management. The application leverages a clean code and clean architecture, and hexagonal architecture. It integrates robust security, logging, and monitoring solutions, and is designed for easy deployment and scaling.

## Main components:
- Domain module for core business logic and ports for integrations
- db-migrations module with Flyway for database migrations
- REST API for client interactions
- postgres-db module with JOOQ data access
- Automated unit and integration tests in each module
