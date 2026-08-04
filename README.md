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

**Default admin login**

- Username: `admin`
- Password: `admin123`

## Deploy on Vercel

This project uses `Dockerfile.vercel` so Vercel can build and run the Spring Boot app as a container.

**Step 1 — Push code to GitHub**

```bash
git add .
git commit -m "Configure project for Vercel deployment"
git push
```

**Step 2 — Import project in Vercel**

1. Go to [vercel.com](https://vercel.com)
2. Click **Add New → Project**
3. Import your GitHub repository
4. Vercel will detect `Dockerfile.vercel` automatically

**Step 3 — Add environment variables**

In Vercel → Project → Settings → Environment Variables, add:

| Variable | Description |
|---|---|
| `SPRING_DATASOURCE_URL` | MySQL JDBC URL |
| `SPRING_DATASOURCE_USERNAME` | Database username |
| `SPRING_DATASOURCE_PASSWORD` | Database password |
| `APP_ADMIN_USERNAME` | Admin username (default: admin) |
| `APP_ADMIN_PASSWORD` | Admin password |

**Step 4 — Deploy**

Click **Deploy**. Vercel builds the Docker image and publishes your app.

**Step 5 — Open live URL**

After deployment, open the URL shown in the Vercel dashboard.

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

## Developer

**Yash Nimbalkar** — Full Stack Development, Backend APIs, Database Design, Deployment

Third Year CSE Student | Java Spring Boot | MySQL | HTML CSS JS | Vercel

GitHub: github.com/YASH7732
