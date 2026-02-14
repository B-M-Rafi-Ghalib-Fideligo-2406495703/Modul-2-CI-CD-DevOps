# eShop - Advanced Programming

## Reflection 1

### Coding Standards & Clean Code Principles
In the development of the "Edit Product" and "Delete Product" features, I have applied several Clean Code principles and Secure Coding practices:

1.  **Meaningful Names**: I used clear and descriptive variable and method names. For example, `editProductPage`, `deleteProduct`, and `productId` clearly convey their purpose without ambiguity.
2.  **Single Responsibility Principle (SRP)**: I maintained a strict separation of concerns by dividing the application into Controller, Service, and Repository layers.
    *   `ProductController` handles HTTP requests and routing.
    *   `ProductService` encapsulates business logic.
    *   `ProductRepository` manages data storage and retrieval.
    This makes the code modular, easier to test, and maintainable.
3.  **Consistency**: I ensured consistent URL patterns (`/product/{id}/edit` and `/product/{id}/delete`) and coding style (using `@Autowired` for dependency injection and Lombok for model boilerplate).

### Secure Coding Practices
1.  **UUID for IDs**: Instead of sequential integers, I implemented UUID generation for product IDs (`Category 4: Insecure Direct Object References`). This makes IDs unpredictable and harder for attackers to enumerate or guess other valid product IDs.
2.  **Product Not Found Handling**: The `findById` and `update` methods in the repository handle cases where a product isn't found (returning `null`), preventing potential NullPointerExceptions or data corruption.
3.  **Secure HTTP Methods**: I used `POST` (via `_method="DELETE"` hidden field) for the delete feature instead of `GET`. This adheres to RESTful principles and protects against CSRF attacks, ensuring that state-changing operations cannot be triggered by simple link clicks or malicious image tags.


### Areas for Improvement
1.  **Input Validation**: While the current implementation works, there is minimal validation on the product name and quantity. Adding basic validation (e.g., ensuring quantity is positive, name is not empty) would robustify the application against invalid data.

## Reflection 2

### Unit Testing and Code Coverage
Writing unit tests gives me confidence that my code behaves as expected, especially when handling edge cases like "product not found" during edit or delete operations. It acts as a safety net for future refactoring, ensuring that existing functionality remains intact.

**How many unit tests?**
There is no "magic number" of unit tests per class. The goal is to cover all meaningful scenarios:
1.  **Positive Cases**: Verify the happy path (e.g., successful creation, finding an existing product).
2.  **Negative Cases**: Verify error handling (e.g., product not found, invalid input).
3.  **Boundary Cases**: Test edge values (e.g., empty list, single item).

**Is 100% Code Coverage enough?**
No, 100% code coverage does **not** guarantee a bug-free application.
1.  **Logic Errors**: Code coverage only measures executed lines, not the correctness of the logic. A function might execute all lines but still produce the wrong output for certain inputs.
2.  **Missing Scenarios**: Coverage tools don't know about missing requirements or edge cases you simply forgot to write tests for.
3.  **Integration Issues**: Unit tests isolate components. They don't verify that components work correctly _together_ (e.g., database constraints, API integration).

### Code Cleanliness in Functional Tests
If verify the number of items in the product list by creating a new functional test suite with the same setup procedures and instance variables as `CreateProductFunctionalTest.java`, **it would reduce code quality due to code duplication**.

**Clean Code Issues:**
1.  **Duplication (DRY Violation)**: Repeating the setup logic (`serverPort`, `testBaseUrl`, `baseUrl`) and potentially helper methods (like navigating to the product list) across multiple test classes makes maintenance harder. If the base URL or setup procedure changes, we'd have to update every single test class.
2.  **Readability**: Boilerplate code distracts from the actual test logic.

**Suggested Improvements:**
1.  **Base Test Class**: Create a `BaseFunctionalTest` class that handles the common setup (e.g., `@LocalServerPort`, initializing `baseUrl`, driver setup). All specific functional test classes (e.g., `CreateProductFunctionalTest`, `ProductListFunctionalTest`) would extend this base class.
2.  **Page Object Model (POM)**: Implement the Page Object Model design pattern. Create a `ProductListPage` class that encapsulates the actions (e.g., `createProduct`, `getProductCount`, `isProductDisplayed`) and locators for the product list page. The tests would then interact with this higher-level abstraction instead of raw Selenium calls (`driver.findElement`). This makes tests more readable and resilient to UI changes.

## Reflection 3

### Code Quality Issues & Strategies (SonarCloud Analysis)

1.  **Empty Method in `EshopApplicationTests.java` (`contextLoads()`)**:
    *   **Issue**: SonarCloud flagged the empty `contextLoads()` method as a *Critical Issue*. While it's a standard test to ensure the Spring context loads, empty methods can be misleading.
    *   **Strategy**: I added a descriptive comment explaining that this test is intentionally empty because its sole purpose is to verify that the application context loads successfully without any exceptions. This clarifies the intent for other developers and resolving the SonarCloud warning.

2.  **Unnecessary `setUp()` in `ProductRepositoryTest.java`**:
    *   **Issue**: An empty `@BeforeEach void setUp()` method was present.
    *   **Strategy**: I removed this method completely. The test class uses `@InjectMocks` which automatically handles the necessary initialization, making the manual `setUp` method redundant and cluttering code.

3.  **Commented-out Code in `HomePageFunctionalTest.java`**:
    *   **Issue**: There were blocks of commented-out code which are considered "dead code" and reduce readability.
    *   **Strategy**: I deleted the commented-out lines. If this code is needed in the future, it can be retrieved from Git history. Removing it improves the maintainability and cleanliness of the codebase.

### CI/CD Implementation Analysis

**Does the current implementation meet the definition of CI and CD?**

Yes, the current implementation meets the core definitions of Continuous Integration and Continuous Deployment.

1.  **Continuous Integration (CI)**:
    *   Every time I push code or create a pull request, the `ci.yml` workflow is triggered. It automatically cleans the project, builds it, and runs the entire test suite (unit and functional tests). This ensures that new changes do not break existing functionality. Furthermore, `build.yml` integrates with SonarCloud to perform static code analysis, ensuring that quality gates are met before code is merged. This rapid feedback loop is the essence of CI.

2.  **Continuous Deployment (CD)**:
    *   The `deploy.yml` workflow automates the release process. Specifically, when changes are merged into the `main` branch, the workflow uses the Koyeb CLI to automatically deploy the application to the PaaS environment. This eliminates manual deployment steps, reducing human error and ensuring that the latest verified version of the application is always available to users, which fulfills the definition of CD.

3.  **Overall**:
    *   The combination of these workflows creates a streamlined pipeline where code flows from development to production with automated checks at every stage, significantly improving development velocity and reliability.

