# 📘 Engineering Guide: Single Responsibility Principle (SRP)

Goal: Increase maintainability by isolating "Reasons to Change."

## 1. The Real Definition

The SRP is often misunderstood as "a class should do only one thing."  
The actual principle: a class should have one, and only one, reason to change.  
In a professional environment, a "reason to change" is usually a request from a specific stakeholder or technical domain.

## 2. The "Three-Domain" Violation

In Java (for example, Spring Boot) applications we often see "God Classes" that mix these three distinct domains:

- Domain / Responsibility / Example
  - Business / How the business works / Calculating tax, applying a discount
  - Persistence / How data is stored / SQL queries, JSON serialization
  - Infrastructure / How we talk to the world / Logging, WiFi connection, HTTP calls

## 3. Case Study: The Smart Thermostat

### ❌ The Anti-Pattern (The "Everything" Class)

This class is fragile. If you update your WiFi security library, you risk breaking the temperature logic because they reside in the same file and share the same scope.

```java
public class SmartThermostat {
    private int currentTemperature;

    // Reason 1: Change in Hardware/Business Logic
    public void setTemperature(int targetTemp) {
        this.currentTemperature = targetTemp;
    }

    // Reason 2: Change in Networking Infrastructure
    public void connectToWiFi(String ssid, String password) {
        // Complex logic for WPA3/Connection protocols
    }

    // Reason 3: Change in Logging Requirements (Local vs Cloud)
    public void logTemperatureHistory(String filename) {
        // File I/O logic
    }
}
```

### ✅ The Professional Refactor

We decompose based on the rate of change: networking protocols change at a different frequency than temperature algorithms. Example split:

```java
// Focus: Core Business Domain
public class SmartThermostat {
    private int currentTemperature;
    public void setTemperature(int targetTemp) { /* ... */ }
}

// Focus: Infrastructure (Network)
public class WifiConnector {
    public void connect(String ssid, String password) { /* ... */ }
}

// Focus: Infrastructure (Persistence)
public class TemperatureLogger {
    public void log(int temp) { /* ... */ }
}
```

## 4. Medior-Level Warning: Avoid "Class Explosion"

- SRP does not mean every method needs a class.
- DO split if the class handles two different stakeholders (e.g., Finance wants a change vs. DevOps wants a change).
- DON'T split cohesive logic. If you have `getTemperature()`, `setTemperature()`, and `isHeatingActive()`, these belong together — they are all subject-matter responsibilities on the thermostat's state.

## 5. How to Spot Violations in Code Review

When reviewing a PR, look for these "code smells":

- The "And" description: e.g. "This class validates the user and saves them to the DB."
- Diverse imports: A domain entity importing `java.io.File` or `java.sql.Connection`.
- Giant `switch`/`if-else` blocks: often a sign the class is trying to handle too many variations (this can lead into Open/Closed Principle violations).

## 🧠 Knowledge Check

Scenario: You have a `UserRegistrationService`. It validates the user, saves them to PostgreSQL, and sends a Welcome Email.

Question: How many classes should this be divided into to satisfy SRP?

Answer: Exactly 3 classes is the right answer for a clean, decoupled design:

- `UserValidator` (Business Rules)
- `UserRepository` (Persistence)
- `EmailService` (Infrastructure / Communication)
