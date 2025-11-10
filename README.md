
---

## 🚀 How to Run the Application Locally (with Docker)

### 🧩 Prerequisites

Before you start, make sure you have installed:

* [Docker](https://www.docker.com/get-started)
* [Docker Compose](https://docs.docker.com/compose/)
* (Optional) [IntelliJ IDEA](https://www.jetbrains.com/idea/) — for viewing and running tests locally
* (Optional) [Postman](https://www.postman.com/) or `curl` — for sending API requests

---

### ⚙️ 1. Build the Application

From the project root directory, build the JAR file using Maven:

```bash
mvn clean package -DskipTests
```

This command creates the JAR file inside:

```
target/notesapp-0.0.1-SNAPSHOT.jar
```

---

### 🐳 2. Build and Run the Docker Containers

Run the following command to build and start all containers (the app and MongoDB):

```bash
docker-compose up --build
```

Once started, you should see logs from both containers:

* `notesapp-app` — your Spring Boot app
* `notes-mongo` — MongoDB database

By default, the services will be available at:

* **App:** [http://localhost:8080](http://localhost:8080)
* **MongoDB:** `mongodb://localhost:27017/notesdb`

---

### 🧪 3. Run Tests (Optional)

If you want to execute tests **inside a container**, rebuild with the test profile:

```bash
docker-compose -f docker-compose.test.yml up --build --abort-on-container-exit
```

If you just want to run tests locally (via IntelliJ or Maven):

```bash
mvn test
```

---

### 📬 4. Verify the Application

After containers start, test the API with the following commands:

**Create a Note:**

```bash
curl -X POST "http://localhost:8080/api/notes" \
  -H "Content-Type: application/json" \
  -d "{\"title\":\"Test Note\",\"text\":\"This is my first note\",\"tags\":[\"PERSONAL\"]}"
```

**Get All Notes:**

```bash
curl "http://localhost:8080/api/notes?page=0&size=10"
```

**Update a Note:**

```bash
curl -X PUT "http://localhost:8080/api/notes/{id}" \
  -H "Content-Type: application/json" \
  -d "{\"title\":\"Updated Title\",\"text\":\"Updated text\",\"tags\":[\"BUSINESS\",\"IMPORTANT\"]}"
```

**Delete a Note:**

```bash
curl -X DELETE "http://localhost:8080/api/notes/{id}"
```

---

### 🧹 5. Stop and Clean Up

To stop all containers:

```bash
docker-compose down
```

To remove all images and volumes:

```bash
docker system prune -af --volumes
```

---

