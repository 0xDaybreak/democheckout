# Discount Review Demo

A deliberately small, end-to-end checkout application for a code-review talk. It contains a Spring Boot REST API, H2 + Flyway persistence, and a Vue UI. The Git history models a pull request: `main` is the pre-discount application, and `feature/returning-customer-discount` adds the feature.

> **Workshop/demo project, not production payment software.** `PaymentAdapter` simulates a payment provider; the catalog is hard-coded; the H2 database is in-memory. The feature branch intentionally contains review issues documented in `presenter/answer-key.md`. Do not use this as a production checkout implementation.

## Requirements

- Java 21+
- Maven 3.9+
- Node.js 20+ and npm (for the optional UI)

The project uses Spring Boot 3.5.16 and Java 21. See the official [Spring Boot system requirements](https://docs.spring.io/spring-boot/3.5/system-requirements.html).

## Start on the discount feature branch

The repository opens on `feature/returning-customer-discount`.

```bash
mvn test
mvn spring-boot:run
```

The backend starts at `http://localhost:8080`. H2 is in-memory; historical workshop orders are inserted by `V41__create_orders.sql` every time a fresh database is created.

Optional Vue UI, in a second terminal:

```bash
cd frontend
npm install
npm run dev
```

Open `http://localhost:5173`. Vite proxies `/api` requests to the backend.

## Try the API

New customer (no prior order):

```bash
curl -X POST http://localhost:8080/api/checkout \
  -H 'Content-Type: application/json' \
  -d '{"customerId":2000,"items":[{"sku":"COFFEE","quantity":1}]}'
```

Returning customer with a paid order in the demo data:

```bash
curl -X POST http://localhost:8080/api/checkout \
  -H 'Content-Type: application/json' \
  -d '{"customerId":1001,"items":[{"sku":"COFFEE","quantity":1}]}'
```

Demo customer `1002` has a **cancelled** order. Try them too, then investigate whether that order should qualify.

Available SKUs: `COFFEE` ($12.00), `BOOK` ($25.00), `LAPTOP_STAND` ($30.00). The customer IDs and prices are fictional workshop fixtures.

## Use it as a PR-review exercise

```bash
git diff --stat main...feature/returning-customer-discount
git diff main...feature/returning-customer-discount
```

The feature PR touches the discount eligibility logic, amount calculation, order persistence, payment boundary, response DTO, controller/UI integration, migration and tests. The diff is intentionally small enough to inspect in a talk but crosses several system boundaries.

Suggested live exercise:

1. Give the audience only the business requirement: “Give a 10% discount if the customer has a completed order in the previous six calendar months.”
2. Ask them to map the call path from the controller to persistence and payment.
3. Ask an AI coding assistant to identify impacted components and rank risks. Use `presenter/blast-radius-prompt.md`.
4. Inspect its cited evidence yourselves; do not accept an unverified dependency or finding.
5. Only then open `presenter/answer-key.md`.

## Requirements / deliberate simplifications

- For the exercise, `PAID` represents a completed order; `CANCELLED` must not qualify.
- “Six calendar months” is intentional wording, not “180 days”.
- The backend uses server-side catalog prices; the client submits only SKU and quantity.
- No real payment is taken. `PaymentAdapter` generates a fake reference only.
- The feature branch's tests are green but incomplete by design; passing tests are not proof of correctness.
