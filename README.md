# 📌 Task Management Backend

A **Spring Boot–based Task Management System** that provides clean REST APIs to manage **Users, Tasks, Teams, and Comments**.  
This backend is designed with monothelic architecture.
---

## 🚀 Features

- 👥 **User Management** – Create and fetch users  
- ✅ **Task Management** – Assign tasks to users, update task details, change status  
- 💬 **Comments** – Add and fetch comments on tasks  
- 📊 **Filtering** – Get tasks by user, team, and status  
- 📦 **Pagination** support for task listings  
- 🛠️ **Spring Boot & RESTful APIs** – Built with industry-standard best practices  

---

## 🛠️ Tech Stack

- **Java 21**  
- **Spring Boot 3.5.5**  
- **Spring Data JPA / Hibernate**  
- **REST APIs**  
- **Maven** for build automation  
- **MySQL /** (configurable)  

---

## 📖 API Documentation
  - 👥 User APIs (/users)
      - /create-user
      - /find-all-users
      - /find-user-by-id/{id}
  - 📌 Task APIs (/tasks)
      - /create-task
      - /find-AllTasks-ByStatus-TeamId-AndUserId
      - /update/{id}
      - /update-task-status?id={id}&status={status}
      - /user/{userId}
      - /find-all-task?page=0&size=25
   
- 👥 Team APis(/teams)
    -  /create-team
    -  /find-all-teams
    -  teams/add-team-member/teamId/1/userId/2
  
  
   
- 💬 Comment APIs (/comments)
    - /create-comment/taskId/{taskId}
    - /find-comment-bytaskId/{taskId}
    

## 📌 Example API Usage
  - Create User
    ```bash
    POST [/users/create-user](http://localhost:8080/task-management/users/create-user)
    Content-Type: application/json
{
  "id": 1,
  "name": "Israr Ali",
  "email": "israr.ali@abc.com",
  "role": "USER"
}


## 📂 Project Structure

```text
task-management-backend
├── src
│   └── main
│       ├── java
│       │   └── com
│       │       └── task_management
│       │           └── task_management
│       │               ├── controller/        # REST Controllers
|       |               ├── model/             # Entity classes
|       |               ├── exception/         # Custom Exceptional handling classes
│       │               ├── dto/               # Data Transfer Objects
│       │               ├── service/           # Service Layer
│       │               ├── repository/        # JPA Repositories
│       │               └── utils/             # Utility Classes
│       │
│       └── resources
│           └── application.yml                # App Configuration
│
└── pom.xml                                    # Maven Dependencies

```
---

## ⚙️ Setup & Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Israr-Ali243/task-mangement-backend.git
   cd task-management-backend

2. **Application properties**
```bash
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/task_management?createDatabaseIfNotExist=true&useSSL=true&serverTimezone=UTC
    username: root
    password: root
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

  server.servlet.context-path=/task-management
  server.port=8080
 ```


## 📌 Future Enhancements

- 🔐 **JWT Authentication & Authorization**  
- 📅 **Task Deadlines & Reminders**  
- 📊 **Dashboard Analytics** (Task Progress, User Productivity)  
- 🌍 **Docker & Kubernetes Deployment**  

---

## 🤝 Contributing

Contributions are welcome! 🎉  
Feel free to **fork this repo** and submit pull requests.  
Please ensure that your code follows **clean code practices** and is **well-documented**.

---

## 📜 License

 – feel free to use and modify.  

---

## 👨‍💻 Author

**Israr Ali**  
💼 Backend Developer | 🚀 Passionate about Spring Boot & Scalable Systems  
📧 [aisrar243@gmail.com])  

