 
# Clean Architecture Instructions

This project follows Clean Architecture principles to ensure maintainability, scalability, and testability. All code must adhere to the following guidelines:


## Hexagonal Architecture (Ports & Adapters)

This project uses Hexagonal Architecture (also known as Ports and Adapters) to maximize separation of concerns and enable flexible integration with external systems.

- **Core Domain**: Contains business logic, domain entities, and use cases. No dependencies on frameworks or external technologies.
- **Ports**: Interfaces that define how the core domain interacts with the outside world (e.g., repositories, services, event publishers). Ports are defined in the domain and application modules.
- **Adapters**: Implementations of ports that connect the core domain to external systems (e.g., databases, messaging, REST APIs, UI). Adapters reside in infrastructure and presentation modules.
- **Dependency Direction**: Adapters depend on ports, never the other way around. The core domain is isolated and testable.

### Principles
- All dependencies point inward toward the core domain.
- External changes (frameworks, databases, UIs) do not affect the core business logic.
- Use Dependency Injection to wire adapters to ports.

### Example Structure
- `domain/` — Core business logic, entities, and port interfaces
- `application/` — Use cases, application services, and port interfaces
- `infrastructure/` — Adapter implementations (e.g. JOOQ repositories, Kafka consumers)
- `rest-api/` — Web adapters (REST controllers)

For more details, see [00 - project overview.md](./00%20-%20project%20overview.md).

## Interfaces & Inversion of Control
- Use interfaces to decouple layers. Infrastructure and presentation layers implement interfaces defined in domain/application layers.
- Apply Dependency Injection for all cross-layer dependencies.

## Package Organization
- Organize code by layer and feature, not by technical type (see [00 - project overview.md](./00%20-%20project%20overview.md)).
- Avoid cyclic dependencies between packages.

## Testing
- Unit tests must target domain and application layers (see [05 - testing.md](./05%20-%20testing.md)).
- Use mocks/stubs for infrastructure dependencies.

## Logging, Metrics, Tracing
- Follow [03 - logging.md](./03%20-%20logging.md), [04 - metrics.md](./04%20-%20metrics.md), and [07 - tracing.md](./07%20-%20tracing.md) for cross-cutting concerns. Implement these in infrastructure layer only.

## Security
- Apply security rules as described in [10 - security.md](./10%20-%20security.md). Security logic should be part of the application layer, not infrastructure.

## References
- For further details, see:
  - [00 - project overview.md](./00%20-%20project%20overview.md)
  - [09 - codestyle.md](./09%20-%20codestyle.md)
  - [11 - database integration.md](./11%20-%20database%20integration.md)
  - [13 - rest-api.md](./13%20-%20rest-api.md)
