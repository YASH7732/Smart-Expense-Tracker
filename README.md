# Smart Expense Tracker 💰

🌐 **Live Demo: https://smart-expense-tracker-czrt.onrender.com**

A full stack web application to track daily expenses, manage budgets and visualize spending patterns.

## What This Project Does
- User registration and login system
- Add, edit and delete daily expenses
- Categorize expenses — food, travel, shopping, bills etc
- Real-time expense table with instant updates
- Visual pie chart showing spending by category
- Total expenses calculation

## How It Works
User registers or logs in → adds daily expenses with title, category and amount → app stores data in cloud MySQL database → dashboard shows pie chart of spending by category → total expenses updates automatically

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java Spring Boot |
| Database | MySQL (Cloud — FreeSQLDatabase) |
| Frontend | HTML, CSS, JavaScript |
| Charts | Chart.js |
| Authentication | Spring Security |
| Deployment | Render |
| Monitoring | UptimeRobot |
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

## How To Run Locally

Step 1 — Clone the repository
git clone https://github.com/YASH7732/Smart-Expense-Tracker.git

Step 2 — Open in VS Code

Step 3 — Run the application
./mvnw spring-boot:run

Step 4 — Open browser
http://localhost:8081

## Current Status
- [x] Spring Boot server setup
- [x] MySQL database connected
- [x] REST APIs — GET, POST, PUT, DELETE
- [x] User registration and login
- [x] Expense CRUD operations
- [x] Frontend HTML CSS JS
- [x] Chart.js analytics dashboard
- [x] Total expenses calculation
- [x] Deployed on Render
- [x] Cloud MySQL database
- [x] UptimeRobot monitoring — always awake

## Developer
**Yash Nimbalkar** — Full Stack Development, Backend APIs, Database Design, Deployment

Third Year CSE Student | Java Spring Boot | MySQL | HTML CSS JS | Render
GitHub: github.com/YASH7732
