# Personal Finance Tracker 

This project is a full-stack web application designed to help users manage their income and expenses efficiently. Built with React (client side) and Spring Boot (server side), the app provides intuitive tools for tracking one-time and recurring transactions, visualizing balances, and maintaining financial control.

##  Features

###  Client (React + TypeScript + Redux)
-  Login & user state persistence
-  Add new transaction (one-time / recurring)
-  View full transaction table by date range
-  Recurring transaction management
-  Update personal user info
-  Toast notifications & beautiful UI with MUI

### Server (Spring Boot)
-  User authentication and management
-  Category-based transaction classification
-  Date-range filtering and monthly summaries
-  Recurring transaction logic
-  Email alerts for important events
-  Data saved via

### Running the Server 

To run the backend server:

1. **Open the `server` folder** in VS Code, IntelliJ, or any other IDE.

2. Make sure you have the following installed:

   - [Java 17 or higher](https://adoptium.net/)
   - [Maven](https://maven.apache.org/) (if not using the project's wrapper)

3. If required, create a `.env` file inside the `server` folder with the following variables:

   ```env
   GMAIL_USERNAME=your_email
   GMAIL_PASSWORD=your_password
   
4. To run the server:

✅ Option 1: From VS Code or IntelliJ
Simply open MainApplication.java (the class with public static void main) and click Run ▶️.
 🧪 Option 2: Using terminal
 
```bash
./mvnw spring-boot:run
```

Or (if Maven is installed globally):

```bash
mvn spring-boot:run

5. The server will start at: http://localhost:8080

### Running the Client

```powershell
cd client
npm install
npm start
