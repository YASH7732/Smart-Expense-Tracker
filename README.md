# Smart Expense Tracker & Admin Dashboard 💰

A production-ready full-stack application to track daily expenses, manage budgets, visualize spending patterns, and provide administrative user management.

🌐 **Live URL**: https://smart-expense-tracker-czrt.onrender.com

---

## Features

### User Features
- **Registration & Authentication**: Secure account creation and session-based login with BCrypt password hashing.
- **Expense Tracking**: Add, edit, view, and delete daily expenses.
- **Expense Categorization**: Organize spending by Food, Travel, Education, Entertainment, Bills, and Other.
- **Visual Analytics**: Interactive category breakdown chart powered by Chart.js.
- **Real-Time Totaling**: Instant automatic recalculation of total expenses.

### Admin Dashboard ("Project 2")
- **Embedded Admin Console**: Accessible exclusively to users with the `ADMIN` role.
- **User Management**: View all registered users across the platform with their unique IDs and roles.
- **Global Expense Oversight**: View and manage all expenses submitted across all users.
- **Access Control Guard**: Strict endpoint security ensuring regular users receive HTTP 403 Forbidden on administrative routes.

---

## Tech Stack

| Layer | Technology |
|---|---|
| **Backend** | Java 17 / 24, Spring Boot 3.5, Spring Data JPA, Spring Boot Actuator |
| **Security** | BCrypt Password Hashing (`PasswordEncoder`), Session-based authentication |
| **Database** | MySQL (Local `expensedb` / Cloud MySQL, TiDB Serverless, or Aiven) |
| **Connection Pool** | Tuned HikariCP with keepalive and auto-reconnect |
| **Frontend** | Responsive HTML5, CSS3 (`style.css`), Vanilla JavaScript (ES6+) |
| **Visualizations** | Chart.js |
| **Testing** | JUnit 5, MockMvc, H2 in-memory test database |
| **Containerization** | Multi-stage Dockerfile (Eclipse Temurin JRE) |

---

## API Endpoints

| Method | Endpoint | Access | Description |
|---|---|---|---|
| `GET` | `/actuator/health` | Public | Real-time service and database health check |
| `GET` | `/health` | Public | Backward-compatible health probe returning 200/503 |
| `POST` | `/register` | Public | Register a new user (`role = USER`) |
| `POST` | `/login` | Public | Authenticate user and establish session |
| `POST` | `/logout` | Authenticated | Invalidate session |
| `GET` | `/me` | Public / Auth | Check current user session status and role |
| `GET` | `/admin` | Admin Only | Verify admin authorization |
| `GET` | `/users` | Admin Only | List all registered users |
| `GET` | `/expenses` | Authenticated | List user expenses (or all expenses for admin) |
| `POST` | `/expenses` | Authenticated | Add a new expense (validated) |
| `PUT` | `/expenses/{id}`| Owner / Admin | Update an existing expense |
| `DELETE` | `/expenses/{id}`| Owner / Admin | Delete an expense |

---

## Environment Variables

| Variable | Required in Prod | Description | Example Format |
|---|---|---|---|
| `SPRING_DATASOURCE_URL` | **Yes** | JDBC connection string with parameters | `jdbc:mysql://<host>:3306/<database>?useSSL=true&allowPublicKeyRetrieval=true&serverTimezone=UTC` |
| `SPRING_DATASOURCE_USERNAME` | **Yes** | Database user | `app_user` |
| `SPRING_DATASOURCE_PASSWORD` | **Yes** | Database password | *(secret)* |
| `HIKARI_MAX_POOL_SIZE` | No (default: 1) | Connection pool cap for free database tiers | `1` |
| `APP_ADMIN_USERNAME` | No (default: admin) | Seed admin username | `admin` |
| `APP_ADMIN_PASSWORD` | **Yes** | Seed admin password — set a strong secret | *(secret)* |
| `PORT` | Set by host | HTTP server port | `8080` |

---

## How to Run Locally

### Prerequisites
- Java JDK 17 or higher
- MySQL running locally on port 3306 with database `expensedb` (or run automated tests with in-memory H2)

### Step 1: Clone the Repository
```bash
git clone https://github.com/YASH7732/Smart-Expense-Tracker.git
cd Smart-Expense-Tracker
```

### Step 2: Set Environment Variables (Optional)
If your local MySQL root user has a password:
```powershell
$env:SPRING_DATASOURCE_PASSWORD="your-mysql-password"
```

### Step 3: Run the Application
**Windows**:
```powershell
.\mvnw.cmd spring-boot:run
```

**Mac / Linux**:
```bash
./mvnw spring-boot:run
```

### Step 4: Access the Application
Open your browser at:
```
http://localhost:8081
```

> **Admin Credentials**: Set the admin username and password via the `APP_ADMIN_USERNAME` and `APP_ADMIN_PASSWORD` environment variables before starting the app. Default admin username is `admin` — always override the password in production.

---

## Running Automated Tests

Run the full integration test suite (runs against in-memory H2 database, zero external dependencies required):
```powershell
.\mvnw.cmd test
```

---

## Docker Deployment

Build and run using the optimized multi-stage Dockerfile:
```bash
docker build -t smart-expense-tracker .
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL="jdbc:mysql://host.docker.internal:3306/expensedb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC" \
  -e SPRING_DATASOURCE_USERNAME="root" \
  -e SPRING_DATASOURCE_PASSWORD="password" \
  smart-expense-tracker
```

---

## UI Screenshots

*Screenshots Placeholder*
- `[Screenshot: User Authentication & Registration]`
- `[Screenshot: Expense Management Table & Category Chart]`
- `[Screenshot: Admin Dashboard with User Management]`

---

## Author & Maintainer

**Yash Nimbalkar**
- GitHub: [@YASH7732](https://github.com/YASH7732)
