# Smart Expense Tracker 💰

A full stack web application to track daily expenses, manage budgets and visualize spending patterns.

## What This Project Does
- User registration and login system
- Add, edit and delete daily expenses
- Categorize expenses — food, travel, shopping, bills etc
- Set monthly budget limits
- Get alerts when budget exceeds limit
- Visual charts showing spending patterns by category

## How It Works
User opens the app → registers or logs in → adds daily expenses with category and amount → app stores data in MySQL database → dashboard shows charts of spending → app alerts user if monthly budget is exceeded

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java Spring Boot |
| Database | MySQL |
| Frontend | HTML, CSS, JavaScript |
| Charts | Chart.js |
| Version Control | Git and GitHub |

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | /register | Register new user |
| POST | /login | User login |
| GET | /expenses | Get all expenses |
| POST | /expenses | Add new expense |
| PUT | /expenses/{id} | Update expense |
| DELETE | /expenses/{id} | Delete expense |
| GET | /dashboard | Get spending summary |

## How To Run This Project

Step 1 — Clone the repository
git clone https://github.com/YASH7732/Smart-Expense-Tracker.git
Step 2 — Open in VS Code

Step 3 — Run the application
./mvnw spring-boot:run
Step 4 — Open browser and go to
http://localhost:8080

## Current Status
- [x] Spring Boot server setup complete
- [x] Project structure created
- [ ] MySQL database connection
- [ ] User authentication
- [ ] Expense CRUD operations
- [ ] Frontend dashboard
- [ ] Chart.js integration
- [ ] Deployment

## Developer
Yash — Backend Development, Database Design, Frontend Integration

Third Year CSE Student | Java Spring Boot | MySQL | HTML CSS JS
