# auto_customer_merge

A service for automatically detecting and merging duplicate customer records between a CRM system and a mobile application. When a new customer is created, the service fetches data from both systems, compares credentials, and merges matching profiles—ensuring all associated data and history are preserved.

---

## Features

- **Automatic Detection:**  
  Identifies when a customer exists in both CRM and the app by comparing credentials (such as phone, email, etc.).

- **Automated Merge:**  
  Merges customer profiles and all related data (orders, history, etc.) into a single unified record.

- **Data Integrity:**  
  Ensures all information is retained during merging—no data is lost.

- **Real-Time Sync:**  
  Triggers the merge process immediately after a new customer is created.

---

## Technologies Used

- **Java & Spring Boot**
- **REST APIs** (for fetching/updating customer data)
- **WEBHOOKS** 
- **PostgreSQL** (or your database of choice)
- **Scheduler / Webhooks** (for real-time detection, if used)
- **Docker** (optional)

---

## Usage

1. **Configure your CRM and application endpoints and credentials in `application.properties`.**
2. **Build and run the service:**
    ```bash
    mvn clean install
    java -jar target/your-jar-name.jar
    ```
3. **The service will automatically fetch, compare, and merge new customer records in real time.**

---

## Configuration

Sensitive information (API tokens, database credentials, etc.) should be placed in `application.properties`, which is **excluded from git** for security.  
See `application.properties.example` for required settings.

---

## Contributing

Contributions and issues are welcome!  
Feel free to open an issue or submit a pull request.

---

