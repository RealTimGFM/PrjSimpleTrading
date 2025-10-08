# PrjSimpleTrading

A **simple stock-trading web app** built with **Jakarta EE (Servlets/JSP)** and **Tomcat**.  
It loads historical prices for a fixed set of tickers (AAPL, AMD, AMZN, GOOG, MSFT, ORCL, PINS, TSLA) from CSV files into **H2**, and lets users **register, log in, view stocks, buy/sell, and see their balance/portfolio**.

---

## ✨ Features

- User registration & login (hashed passwords)
- View stock list & details (prices loaded from CSV dataset)
- **Buy / Sell** with basic validations
- View balance & portfolio holdings
- Seeded **admin** account with special access
- Simple MVC + DAO structure (`UserDAO`, `UserStockDAO`, `DatasetDAO`, …)

---

## 🧰 Tech Stack

- **Java 17+**, **Jakarta EE 10** (Servlets/JSP)
- **Apache Tomcat 10+**
- **H2** database (file or in-memory)
- CSV loaders for historical price data
- JSP + JSTL + vanilla HTML/CSS/JS

---

## 📁 Project Structure (example)

```
PrjSimpleTrading/
├─ src/main/java/
│  ├─ web/servlets/           # Login, Register, Stocks, Buy, Sell, Portfolio…
│  ├─ dao/                    # UserDAO, UserStockDAO, DatasetDAO (+ impl)
│  ├─ model/                  # POJOs: User, DatasetRow, Holding, Trade…
│  ├─ util/                   # DB, hashing, CSV loader, config helpers
│  └─ filters/                # AuthFilter, AdminFilter
├─ src/main/resources/
│  ├─ data/                   # AAPL.csv, AMD.csv, …, TSLA.csv
│  └─ schema.sql              # tables + seed (optional)
├─ src/main/webapp/
│  ├─ WEB-INF/web.xml         # servlet mappings
│  ├─ views/                  # JSP: login.jsp, stocks.jsp, portfolio.jsp…
│  └─ assets/                 # css/js/img
├─ pom.xml
└─ README.md
```

---

## 🗄️ Data Model

- **Users**  
  `id (PK) | username | email | password_hash | created_at | balance`

- **User_Stock**  
  `id (PK) | user_id (FK) | symbol | quantity | avg_buy_price | last_updated`

- **Dataset** (all symbols’ price rows)  
  `stockID (VARCHAR(10)) | date | open | high | low | close | volume`  
  > Keep an index on `(stockID, date)` for fast lookups.

---

## 🚀 Getting Started

### Prerequisites
- JDK **17+**
- **Apache Tomcat 10+**
- **Maven 3.9+** (or your IDE’s Maven)
- (Optional) IntelliJ/Eclipse with Tomcat runner

### 1) Clone
```bash
git clone https://github.com/RealTimGFM/PrjSimpleTrading.git
cd PrjSimpleTrading
```

### 2) Configure
Set defaults in `src/main/resources/application.properties` (or your config class):

```
# H2 file path (folder will be created if missing)
h2.path=./data/trading-db

# Directory with the 8 CSVs (AAPL.csv, AMD.csv, …, TSLA.csv)
csv.dir=src/main/resources/data

# Seed admin
admin.username=T
admin.email=t@t
admin.password=1
```

If you use a JDBC URL directly, point it to the same H2 file for both init and runtime.

### 3) Build
```bash
mvn clean package
```
This produces `target/PrjSimpleTrading.war`.

### 4) Deploy & Run (Tomcat)
1. Copy the WAR into `TOMCAT_HOME/webapps/`
2. Start Tomcat
3. Open: `http://localhost:8080/PrjSimpleTrading/` *(or your context path)*

**First run does:**
- Create tables (if missing)
- Load the 8 CSVs into `Dataset`
- Seed **admin** user  
  **Username:** `T` • **Email:** `t@t` • **Password:** `1`

---

## ▶️ Usage Flow

1. **Register / Log in**
2. **Stocks** page → browse tickers, open details page
3. **Buy**: enter quantity; balance/price checks apply
4. **Sell**: checks holdings and updates `User_Stock`
5. **Portfolio**: view positions, market value, and **balance**

---

## 📥 CSV Format

- Folder: `src/main/resources/data` (or `csv.dir`)
- Filename = symbol (e.g., `AAPL.csv`)
- Headers:
  ```
  date,open,high,low,close,volume
  ```
- Dates: `YYYY-MM-DD`

---

## 🔐 Security Notes

- Passwords are **salted & hashed**
- Session auth + `AuthFilter` for protected routes
- Admin routes behind `AdminFilter`
- (Planned) CSRF token + brute-force protection

---

## 🧪 Tips & Troubleshooting

- **404 after deploy** → confirm context path (`/PrjSimpleTrading` vs `/`)
- **“Table not found: DATASET”** → ensure init ran; confirm DB URL points to the same H2 file in all places
- **CSV not loading** → verify `csv.dir` and filenames match symbols
- **Reset DB** → stop Tomcat, delete the H2 file/dir at `h2.path`, restart app
- **UTF-8** → run JVM with `-Dfile.encoding=UTF-8` if you see garbled text

---

## 📜 License

MIT — feel free to use and adapt.  
© 2025 Quy (Tim) Vu
