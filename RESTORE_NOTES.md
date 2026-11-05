# Restore Notes

## Project summary

**FinanceFlow Pro** is a Java 17 / Maven / JavaFX / SQLite banking management system built for a Software Testing course. The repository originally contained **two parallel projects**:

1. **Root project** (`pom.xml`, `src/main/java/*.java`): a plain-Java domain model (`Account`, `Card`, `Transaction`, ...) with a Swing GUI (`Gui.java`, 2100+ lines), and a `src/test/java/BankingSystem` package of JUnit 4-style white-box/unit test suites.
2. **`Bank/` subfolder**: a more advanced evolution of the same domain model, moved into `com.bank.model`, wrapped by a real JavaFX + SQLite application (`DatabaseManager`, `BankingService`, a 9-tab `ComprehensiveMainController` GUI) — but with only a thin service-level JUnit 5 test class and none of the root project's white-box test suites.

The two had diverged: `Bank/`'s model classes already contained bug fixes and missing constructors that the root project lacked, but the root project's test suites were the more thorough testing artifact (the actual point of a testing-course project) and had never even compiled, because the root `pom.xml` had no JUnit dependency at all.

## How to install, build, and run (fresh machine)

```bash
git clone https://github.com/hassanxadel/Testing-Banking-System.git
cd Testing-Banking-System
mvn test            # run all 54 tests
mvn javafx:run       # launch the desktop app
```

Requires JDK 17+ and Maven 3.6+. See `README.md` for packaging/jar instructions.

## Merge decision

`Bank/` was kept as the base (it already superseded the root project's model classes and added the real GUI/DB/service layers). The root project's 13 JUnit 4 test-suite files were **ported into `Bank/src/test/java/com/bank/model`**, converted to JUnit 5 (`@BeforeEach`/`@AfterEach`/`@BeforeAll`/`@AfterAll`, `org.junit.jupiter.api.Assertions`), and placed in the `com.bank.model` package so they can see the model classes' package-private fields/methods. `Bank/`'s contents were then promoted to the project root and the old duplicate top-level `src/`, `pom.xml`, `.project`, `.classpath`, `.settings` were removed.

**Removed as duplicate/dead:**
- Root `pom.xml` (had no dependencies — couldn't even compile a real test).
- Root `src/main/java/{Account,Asset,Card,Customer,Employee,Gui,Investment,Loan,Office,Repository,Transaction}.java` — all 1:1 superseded by `Bank/src/main/java/com/bank/model/*` (plus the Swing `Gui.java`, superseded by the JavaFX GUI).
- Root `src/test/java/org/junit/{BeforeAll,AfterAll}.java` — hand-rolled stub annotation classes that stood in for a missing real JUnit dependency; no longer needed now that JUnit 5 is a real Maven dependency.
- The duplicate `.project`/`.classpath`/`.settings` Eclipse metadata at the root.

## Bugs fixed

| # | File | Problem | Fix |
|---|------|---------|-----|
| 1 | root `pom.xml` (removed) | No JUnit dependency at all — the entire original test suite failed to compile, so it had never actually run. | Resolved by merging into `Bank/pom.xml`, which already declares `junit-jupiter`. |
| 2 | `pom.xml` (surefire config) | Surefire's default test pattern (`*Test.java`) silently skipped all 13 `*TestSuite.java` / `*WhiteBoxTesting.java` files — they compiled but never executed. | Added explicit `<includes>` for `*TestSuite.java` and `*WhiteBoxTesting.java` patterns. |
| 3 | `com.bank.model.Account` / `Transaction` | Several test suites called a `send_transaction(Account, double, boolean)` / `Transaction(Account, Account, double, boolean)` "deposit flag" overload that didn't exist anywhere in either project — a missing feature, not a typo. | Implemented the overloads: an `isDeposit` transaction is always accepted and credits the initiating account directly (an external deposit); a regular transfer keeps the original balance-check behavior. |
| 4 | `com.bank.service.BankingService#transferMoney` | Transfers from an account to itself were not rejected (`fromAccount.equals(toAccount)` was never checked), which the project's own `BankingSystemTest.testTransferValidation` caught as a failing assertion. | Added the same-account check alongside the existing null/amount validation. |
| 5 | `com.bank.database.DatabaseManager#getNextCardNumber` | Used `Math.random()` to generate card number digits — a non-cryptographic PRNG for financial-identifier generation. | Switched to `java.security.SecureRandom`. |
| 6 | `com.bank.gui.ComprehensiveMainController` (card-issuance CVV) | Same `Math.random()` weak-RNG issue for CVV generation. | Switched to `SecureRandom`. |
| 7 | Ported `AccountTestSuite.testTransactionSending` | The original test's expected balance (50.0) was inconsistent with any single coherent implementation of the "deposit flag" (see #3) — tracing through the fixed, real semantics gives 150.0. | Updated the expected value with a comment explaining the self-deposit-then-transfer sequence. |

## Dependency/SDK changes

None of the Maven dependency *versions* needed upgrading — `Bank/pom.xml` already targeted current, working versions (Java 17 toolchain, JavaFX 17.0.2, SQLite JDBC 3.42.0.0, JUnit Jupiter 5.9.3) that build cleanly on this machine. The only pom change was adding Surefire `<includes>` (bug #2 above).

## UI

No UI changes were needed — the existing JavaFX interface (dark theme, card-based dashboard, 9 tabs) was already clean and professional; see the screenshot in `README.md`.

## Known limitations

- `bank.db` ships with the repo as a seeded sample database (customers/accounts/cards/a loan/an investment) so the app has data on first run; delete it to start from an empty schema (`DatabaseManager` recreates it).
- The `com.bank.model` domain classes (and their 43 tests) are **not** wired into the running JavaFX app — the app talks to SQLite directly through `DatabaseManager`/`BankingService`. The model package exists purely as the testing-exercise domain layer from the original coursework; integrating it into the live app would be a feature addition beyond the scope of this restore.
- No secrets, API keys, or external services are required.
