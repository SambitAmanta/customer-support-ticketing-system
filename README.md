# Customer Support Ticketing System 🎟️🚀


A full-stack customer support ticketing system built using:

- ✅ **Backend**: Spring Boot + Spring Security + JWT + MySQL
- ✅ **Frontend**: Next.js (React) + Material-UI
- ✅ **Authentication**: Role-based access (Admin, Agent, Customer)
- ✅ **Features**: Ticket creation, management, filtering, assignments, dashboards

## **📝 Features**

### **1️⃣ Authentication & Authorization**
- Secure JWT-based login system.
- Role-based access for Admins, Agents, and Customers.

### **2️⃣ Ticket Management System**
- Create, View, Update, and Delete Tickets.
- Admins can assign tickets to Agents.
- Customers can view & update their tickets before resolution.

### **3️⃣ Dashboards & Analytics**
- Admin Dashboard: Ticket analytics & agent workload.
- Agent Dashboard: Assigned tickets & resolution stats.
- Customer Dashboard: Personal ticket history.

### **4️⃣ Search & Filtering**
- Search tickets by title, description, or ID.
- Filter by status, priority, and assigned agent.

### **5️⃣ Email Notifications**
- Customers notified when ticket is assigned or status changes.
- Agents notified when new tickets are assigned.

### **📂 Project Structure**
📦 customer-support-ticketing-system/
┣ 📂 customer-support-system/ (Spring Boot)
┃ ┣ 📂 src/main/java/com/example/ticketing/
┃ ┃ ┣ 📂 controller/      # REST API Controllers
┃ ┃ ┣ 📂 model/           # Entities (User, Ticket)
┃ ┃ ┣ 📂 repository/      # Database Repositories
┃ ┃ ┣ 📂 service/         # Business Logic
┃ ┃ ┣ 📂 security/        # JWT & Auth
┃ ┃ ┗ 📂 util/            # Helper classes (e.g., EmailUtil)
┃ ┣ 📂 resources/
┃ ┃ ┗ 📄 application.properties  # Configurations
┃ ┗ 📄 pom.xml            # Maven Dependencies
┣ 📂 ticketing-system-frontend/ (Next.js)
┃ ┣ 📂 pages/
┃ ┃ ┣ 📂 dashboard/       # Role-based dashboards
┃ ┃ ┣ 📂 tickets/         # Ticket listing & details
┃ ┃ ┣ 📂 api/             # API handlers for authentication
┃ ┃ ┗ 📄 login.tsx        # Login page
┃ ┣ 📂 components/        # Navbar, ProtectedRoute
┃ ┣ 📂 utils/             # JWT utilities
┃ ┣ 📂 styles/            # Global CSS & modules
┃ ┗ 📄 next.config.js     # Next.js configuration


---

## **⚙️ Tech Stack**
### **🖥️ Backend**
- **Spring Boot** (REST API)
- **Spring Security + JWT** (Authentication)
- **Spring Data JPA** (Database access)
- **MySQL** (Database)
- **H2 Database** (For local development)
- **Lombok** (Boilerplate reduction)
- **JavaMailSender** (Email notifications)

### **🌐 Frontend**
- **Next.js** (React framework)
- **Axios** (API requests)
- **Material-UI (MUI)** (UI components)
- **React Hook Form** (Form handling)

---

## **🚀 Installation & Setup**

### **1️⃣ Clone the Repository**
```bash
git clone https://github.com/your-username/customer-support-system.git
cd customer-support-system
```

### **2️⃣ Backend Setup (Spring Boot)**
📌 Configure Database
Create a MySQL database:
```sql
CREATE DATABASE support_ticket_system;
Configure application.properties in backend/src/main/resources/
```
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/support_ticket_system
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.mail.host=smtp.gmail.com
spring.mail.username=your-email@gmail.com
spring.mail.password=your-email-password
```
📌 Run the Backend
```bash
cd customer-support-system
./mvnw spring-boot:run
```
- API is available at http://localhost:8080/api

## **3️⃣ Frontend Setup (Next.js)**
📌 Install Dependencies
```bash
cd ticketing-system-frontend
npm install
```
📌 Run the Development Server
```bash
npm run dev
```
- Frontend is available at http://localhost:3000
  
## 🔐 API Endpoints

### 1️⃣ Authentication API
| Method | Endpoint         | Role  | Description         |
|--------|----------------|------|---------------------|
| `POST` | `/auth/login`   | All  | User Login         |
| `POST` | `/auth/register` | All  | User Registration  |

---

### 2️⃣ Ticket Management API
| Method   | Endpoint                 | Role         | Description                                     |
|----------|--------------------------|-------------|-------------------------------------------------|
| `POST`   | `/tickets`               | Customer    | Create a new ticket                            |
| `GET`    | `/tickets`               | Admin       | View all tickets                              |
| `GET`    | `/tickets/my-tickets`    | Customer    | View tickets created by the customer          |
| `GET`    | `/tickets/assigned-tickets` | Agent    | View tickets assigned to an agent             |
| `GET`    | `/tickets/{id}`          | All         | View details of a specific ticket             |
| `PUT`    | `/tickets/{id}`          | Customer    | Update own ticket (before resolution)         |
| `DELETE` | `/tickets/{id}`          | Customer, Admin | Delete a ticket                             |
| `POST`   | `/tickets/{id}/assign`   | Admin       | Assign a ticket to an agent                   |
| `PATCH`  | `/tickets/{id}/status`   | Agent, Admin | Update the status of a ticket               |

---

### 3️⃣ Ticket Search & Filtering API
| Method  | Endpoint                  | Role   | Description                                      |
|---------|---------------------------|--------|--------------------------------------------------|
| `GET`   | `/tickets/search?query=`  | All    | Search tickets by title, ID, or description      |
| `GET`   | `/tickets/filter`         | All    | Filter tickets by status, priority, or agent    |

📌 **Example:**  
- Search: `/tickets/search?query=login issue`  
- Filter by status: `/tickets/filter?status=OPEN`  
- Filter by priority: `/tickets/filter?priority=HIGH`  
- Filter by assigned agent: `/tickets/filter?agentId=5`  

---

### 4️⃣ Dashboard API
| Method | Endpoint            | Role     | Description                                      |
|--------|---------------------|---------|--------------------------------------------------|
| `GET`  | `/dashboard/admin`  | Admin   | View analytics (ticket count, agent workload)    |
| `GET`  | `/dashboard/agent`  | Agent   | View assigned tickets & resolution time stats    |
| `GET`  | `/dashboard/customer` | Customer | View customer’s ticket history                  |

---

### 5️⃣ User Management API
| Method  | Endpoint            | Role  | Description                 |
|---------|---------------------|------|-----------------------------|
| `GET`   | `/users`            | Admin | List all users              |
| `GET`   | `/users/{id}`       | Admin | Get a specific user's details |
| `GET`   | `/users?role=AGENT` | Admin | Get a list of agents        |
| `PUT`   | `/users/{id}`       | User  | Update user profile         |
| `DELETE` | `/users/{id}`      | Admin | Delete a user               |

---



## **🔍 Testing**
Run backend tests:

``` bash
cd backend
./mvnw test
```
Run frontend tests:
```bash
cd frontend
npm test
```

## **🎯 Future Improvements:**

🔄 Real-time ticket updates (WebSockets).
📱 Mobile-responsive UI (PWA).
📊 Advanced analytics for Admin.
💬 Chat support for customers.

