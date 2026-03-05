# 📘 Engineering Guide: Open/Closed Principle (OCP)

Goal: Extend system functionality without risking regressions in existing code.

## 1. The Core Philosophy

"Software entities should be open for extension, but closed for modification."

Open for Extension: You can make the system do new things (add a new payment provider, a new report format, etc.).

Closed for Modification: You achieve that "new thing" without changing the source code of the classes that are already tested and running in production.

## 2. The "If-Else" Smell

The most common violation of OCP is the Type-Checking Switch. If you find yourself adding a new case or else if every time a business requirement changes, you are violating OCP.

### ❌ The Violation (Brittle Code)

This class must be "opened" and modified every time a new shipping provider is added. This requires re-testing the entire class.

```java
public class ShippingService {
    public double calculateCost(String carrier, double weight) {
        if (carrier.equals("FEDEX")) {
            return weight * 5.0;
        } else if (carrier.equals("UPS")) {
            return weight * 4.5;
        }
        // Adding DHL? You must modify this file!
        return 0;
    }
}
```

## 3. The Professional Refactor (Abstraction)

We move from Imperative Logic (checking types) to Declarative Logic (using polymorphism).

### ✅ Step 1: Define the Contract

```java
public interface ShippingCarrier {
    boolean supports(String carrierName);
    double calculate(double weight);
}
```

### ✅ Step 2: Implement Extensions

```java
@Component
public class FedexCarrier implements ShippingCarrier {
    public boolean supports(String name) { return "FEDEX".equalsIgnoreCase(name); }
    public double calculate(double weight) { return weight * 5.0; }
}

@Component
public class UpsCarrier implements ShippingCarrier {
    public boolean supports(String name) { return "UPS".equalsIgnoreCase(name); }
    public double calculate(double weight) { return weight * 4.5; }
}
```

### ✅ Step 3: The "Closed" Service

In Spring Boot, we use Constructor Injection to get all implementations. This service never changes again.

```java
@Service
public class ShippingService {
    private final List<ShippingCarrier> carriers;

    public ShippingService(List<ShippingCarrier> carriers) {
        this.carriers = carriers;
    }

    public double getCost(String carrierName, double weight) {
        return carriers.stream()
                .filter(c -> c.supports(carrierName))
                .findFirst()
                .map(c -> c.calculate(weight))
                .orElseThrow(() -> new IllegalArgumentException("Carrier not supported"));
    }
}
```

## 4. Pragmatic Trade-offs

For Medior engineers, don't apply OCP blindly. Follow the Rule of Three:

- First time: Write a simple if.
- Second time: Still okay to use an if.
- Third time: If you are adding a third branch, refactor to an Interface/Strategy pattern.

## 5. Summary for Code Reviews

- Is it closed? Can I add a feature without touching this file?
- Is it abstract? Does the high-level service depend on an Interface rather than a Concrete Class?
- Does it use DI? Are we injecting a list of behaviors instead of hardcoding them?


---

```markdown
# 🛠️ Implementation Patterns for Open/Closed Principle (OCP)

In Spring Boot, we typically use **Dependency Injection** to satisfy OCP. This allows us to add new functionality (Extensions) without modifying the core service (Closed).

There are two primary ways to manage these extensions:

## 1. The Map Pattern (The "Fast" Choice)
Spring can inject all beans of a specific interface into a `Map<String, Interface>`. The **key** of the map corresponds to the **Bean Name**.

### When to use:
* Simple 1:1 mapping (e.g., File extensions: "PDF", "CSV").
* You need $O(1)$ performance for high-throughput lookups.

```java
@Service
public class ExportService {
    // Key = bean name (e.g., "pdfExporter"), Value = the bean instance
    private final Map<String, Exporter> exporters;

    public ExportService(Map<String, Exporter> exporters) {
        this.exporters = exporters;
    }

    public void exportData(String type, Data data) {
        Exporter exporter = exporters.get(type + "Exporter");
        if (exporter == null) throw new IllegalArgumentException("Type not supported");
        exporter.export(data);
    }
}

```

---

## 2. The Support Pattern (The "Flexible" Choice)

The service iterates through a `List` of implementations and asks each bean if it is capable of handling the current request.

### When to use:

* Complex business rules (e.g., "If amount > 1000 AND currency is USD").
* The logic to choose a handler involves more than just a simple String key.

```java
@Service
public class PaymentService {
    private final List<PaymentProcessor> processors;

    public PaymentService(List<PaymentProcessor> processors) {
        this.processors = processors;
    }

    public void process(PaymentRequest request) {
        processors.stream()
            .filter(p -> p.supports(request)) // Logic lives inside each implementation
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No processor found"))
            .pay(request.getAmount());
    }
}

```

---

## 📋 Comparison Table

| Feature | Map Pattern | Support (List) Pattern |
| --- | --- | --- |
| **Selection Logic** | Centralized (Key-based) | Decentralized (Each class decides) |
| **Performance** | Faster ($O(1)$) | Slower ($O(n)$) |
| **Flexibility** | Low (Static keys only) | High (Dynamic logic) |
| **Spring Setup** | Uses `@Component("name")` | Uses generic `@Component` |

---

### ⚠️ Medior Tip: The "Rule of Three"

Do not create these patterns for a single `if/else`. Only refactor to these patterns if you anticipate having 3 or more implementations, or if the logic is expected to grow across different sprints.

```
