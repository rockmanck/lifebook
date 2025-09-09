# Technology Stack Overview

This document describes the core technology stack used in this project. All technology choices are standardized across services to ensure consistency, maintainability, and supportability.

## Platform & Runtime
- **Java**: 17 (LTS, Required)
- **Spring Boot**: 3.2.x (Latest stable 3.x)
- **Gradle**: 7.5+ (Groovy DSL, use Gradle Wrapper)

## Core Frameworks & Libraries
- **Spring Boot**: Application framework for dependency injection, configuration, and service orchestration
- **Spring Data JDBC** or **jOOQ**: Data access layer for PostgreSQL (choose one per integration)
- **PostgreSQL**: Primary relational database
- **Flyway**: Database migration management
- **OpenAPI 3.0+**: Contract-first API development
- **SLF4J**: Logging API (with centralized configuration via commons-logging)
- **JUnit 5, Mockito, AssertJ**: Unit testing
- **Testcontainers, WireMock**: Integration and external service testing
- **Micrometer, OpenTelemetry**: Observability, metrics, and tracing

## Dependency & Version Management
- All dependencies and plugin versions are managed centrally in the `libs.versions.toml` file using Gradle's version catalog.
- Reference dependencies in build scripts using the `libs.` notation only.

## Configuration & Secrets
- Use `application.yml` for base configuration, with environment-specific overrides (e.g., `application-k8s.yml`).
- Secrets and sensitive configuration are managed via environment variables or `.env/local.env` for local development.

## Security
- No authentication or authorization is implemented in this service by default. See the security documentation for details and future requirements.
