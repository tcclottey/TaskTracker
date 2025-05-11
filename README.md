TaskTracker
A simple Spring Boot-based Task Management API that allows users to create, view, update, and delete tasks.

🚀 Features
Create new tasks with title, description, due date, and status
View all tasks or a single task by ID
Update existing tasks
Delete tasks
H2 in-memory database for development
RESTful API built with Spring Boot and JPA
🛠 Tech Stack
Java 17+
Spring Boot 3.x
Spring Data JPA
H2 Database
Maven
Git + GitHub
IntelliJ IDEA / Eclipse
📦 API Endpoints
Method	Endpoint	Description
GET	/api/tasks	Get all tasks
GET	/api/tasks/{id}	Get task by ID
POST	/api/tasks	Create a new task
PUT	/api/tasks/{id}	Update a task
DELETE	/api/tasks/{id}	Delete a task
🔧 Setup Instructions
Clone the repository:
git clone https://github.com/tcclottey/TaskTracker.git
cd TaskTracker

Run the app:

With Maven:

bash Copy Edit ./mvnw spring-boot:run Or from your IDE (Eclipse/IntelliJ)

Access:

App: http://localhost:8080

H2 Console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:testdb)

📫 Author Leslie Clottey Project Manager & Software Development Entrepreneur, Atlanta, GA LinkedIn: www.linkedin.com/in/leslietcclottey
