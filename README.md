# Smart Expense Tracker 💰

A full stack web application to track daily expenses, manage budgets, and visualize spending patterns.

## What This Project Does

- User registration and login system
- Admin panel to view all users and all expenses
- Add, edit, and delete daily expenses
- Categorize expenses — food, travel, shopping, bills, etc.
- Real-time expense table with instant updates
- Visual pie chart showing spending by category
- Total expenses calculation

## How It Works

User registers or logs in → adds daily expenses with title, category, and amount → app stores data in a cloud MySQL database → dashboard shows a pie chart of spending by category → total expenses update automatically.

Admin users can also view all registered users and manage expenses across the platform.

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java Spring Boot |
| Database | MySQL |
| Frontend | HTML, CSS, JavaScript |
| Charts | Chart.js |
| Authentication | Session-based login |
| Deployment | Vercel (Docker container) |
| Version Control | Git and GitHub |

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | /register | Register new user |
| POST | /login | User login |
| POST | /logout | User logout |
| GET | /me | Current logged-in user |
| GET | /users | List all users (admin only) |
| GET | /admin | Admin access check |
| GET | /expenses | Get expenses |
| POST | /expenses | Add new expense |
| PUT | /expenses/{id} | Update expense |
| DELETE | /expenses/{id} | Delete expense |

## How To Run Locally

**Step 1 — Clone the repository**

```bash
git clone https://github.com/YASH7732/Smart-Expense-Tracker.git
cd Smart-Expense-Tracker
```

**Step 2 — Set database config**

Update `src/main/resources/application.properties` or set environment variables:

```properties
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/expensedb
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=your-password
```

**Step 3 — Run the application**

Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Mac/Linux:

```bash
./mvnw spring-boot:run
```

**Step 4 — Open browser**

http://localhost:8081


## Current Status

- [x] Spring Boot server setup
- [x] MySQL database connected
- [x] REST APIs — GET, POST, PUT, DELETE
- [x] User registration and login
- [x] Admin privileges and admin panel
- [x] Expense CRUD operations
- [x] Frontend HTML CSS JS
- [x] Chart.js analytics dashboard
- [x] Total expenses calculation
- [x] Vercel deployment configuration
- [x] Cloud MySQL database
- [x] Admin privileges and admin panel (Not deploy yet)

## Developer

**Yash Nimbalkar** — Full Stack Development, Backend APIs, Database Design, Deployment

Third Year CSE Student | Java Spring Boot | MySQL | HTML CSS JS | Vercel

GitHub: github.com/YASH7732
