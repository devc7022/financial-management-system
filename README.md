# Financial Management System (Backend API)

A robust RESTful API built to securely manage and track financial records. This system features comprehensive user authentication, role-based access control, and dynamic financial data summaries. 

---

## 🚀 Technologies Used

- **Java 17** 
- **Spring Boot 3.2.4**: Core framework for absolute rapid REST API development
- **Spring Security & JWT**: For fully stateless and secure authentication/authorization 
- **SQLite & Spring Data JPA**: For streamlined, zero-configuration data persistence 
- **Swagger (OpenAPI 3)**: For interactive API documentation and testing

---

## ✨ Key Features

*   **User Management & Auth**: Secure registration, login, and JWT token issuance.
*   **Role-Based Access**: Role hierarchies ensuring users only access their own financial records.
*   **Financial Records Management**: Full CRUD operations for tracking financial incomes and expenses.
*   **Categories & Types**: Ability to categorize records and specify `INCOME` or `EXPENSE`.
*   **Dashboard Summaries**: Aggregate APIs delivering total balance, total income, and total expenses.
*   **Stateless Architecture**: Perfect for pairing with independent frontend frameworks like React or Next.js.

---

## 🛠️ Getting Started (Local Development)

### Prerequisites

1.  **Java Development Kit (JDK) 17** installed on your machine.
2.  **Maven** installed (or utilize your IDE's built-in Maven wrappers).
3.  Any modern IDE (IntelliJ IDEA, Eclipse, or VS Code).

### Installation & Running

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/devc7022/financial-management-system.git
    cd financial-management-system
    ```

2.  **Build the project:**
    ```bash
    mvn clean install
    ```

3.  **Run the application:**
    ```bash
    mvn spring-boot:run
    ```
    *The application will automatically start on `http://localhost:8080`. SQLite will automatically create `finance.db` in the root directory.*

---

## 📚 API Documentation

Once the application is running locally, you can view all endpoints, interact with them, and test them live via Swagger UI:

- **Interactive UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- **Raw OpenAPI JSON Spec:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

> **Important:** To test protected endpoints in Swagger, you must first create a user (`/api/auth/register`), login (`/api/auth/login`) to receive a JWT token, and click the **Authorize** icon in the Swagger UI to input your token.
