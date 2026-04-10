# 🏃‍♂️ MetaboTrack API (CWK1: Web Services API Development)

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![MyBatis-Plus](https://img.shields.io/badge/MyBatis--Plus-Supported-red?style=for-the-badge)
![Swagger](https://img.shields.io/badge/OpenAPI-Swagger_3-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

> **Module:** XJCO3011 Web Services and Web Data | **Assessment:** Coursework 1
> A data-driven RESTful API designed to manage and analyze a massive dataset (15,000+ records) of daily metabolic and physiological activity.

---

## 📑 Table of Contents
1. [Project Overview](#-project-overview)
2. [Technology Stack](#-technology-stack)
3. [Project Structure](#-project-structure)
4. [API Endpoints Reference](#-api-endpoints-reference)
5. [Key Architectural Decisions](#-key-architectural-decisions)
6. [Getting Started](#-getting-started)
7. [Deliverables & Documentation](#-deliverables--documentation)

---

## 💡 Project Overview
MetaboTrack API is a robust backend service designed not just to store physiological data, but to extract meaningful health insights. It goes beyond simple CRUD operations by implementing a custom **Rule-Based Physiological Simulator** and performing complex **Data Binning and Aggregations** to demonstrate the correlations between exercise streaks, sleep quality, and metabolic efficiency.

---

## 🛠 Technology Stack

| Component | Technology | Justification |
| :--- | :--- | :--- |
| **Language** | Java 17 | Object-oriented paradigm, strong typing, and excellent ecosystem. |
| **Framework** | Spring Boot 3.x | Rapid development, embedded Tomcat server, and dependency injection. |
| **ORM** | MyBatis-Plus | Simplifies standard CRUD with `LambdaQueryWrapper` while allowing highly optimized custom `@Select` queries for complex analytics. |
| **Database** | MySQL 8.x | Relational database handling 15,000+ rows efficiently with B-Tree indexing. |
| **API Docs** | Springdoc (OpenAPI 3) | Automated, interactive API documentation (Swagger UI). |

---

## 📂 Project Structure
The application strictly follows the **Controller-Service-Mapper** layered architecture, ensuring the Separation of Concerns (SoC).
```
📦 src/main/java/com/metabotrackapi
┣ 📂 config          # Global configurations (e.g., MyBatis-Plus Pagination)
┣ 📂 constant        # System-wide constants (Exception, Rank, Simulator)
┣ 📂 controller      # REST API endpoints (Record & Analytics)
┣ 📂 converter       # MapStruct/Manual converters mapping DTOs <-> Entities
┣ 📂 dto             # Data Transfer Objects (Input boundaries)
┃ ┣ 📜 RecordCreateDTO.java
┃ ┣ 📜 RecordPageQueryDTO.java
┃ ┣ 📜 SimulationRequestDTO.java
┃ ┗ ... 
┣ 📂 entity          # Database Object Models
┣ 📂 enumeration     # Type-safe Enums (e.g., CalorieEfficiencyEnum)
┣ 📂 exception       # Custom Business Exceptions
┣ 📂 handler         # Global Exception Handlers & Auto-fill meta objects
┣ 📂 mapper          # Data Access Layer (MyBatis interfaces & @Select SQL)
┣ 📂 result          # Standardized API response wrappers (Result<T>, PageResult)
┣ 📂 service         # Business Logic Layer (Interfaces & Implementations)
┣ 📂 vo              # Value Objects (Output boundaries for analytics)
┃ ┣ 📜 EfficiencyDistributionVO.java
┃ ┣ 📜 ExerciseStreakBenefitVO.java
┃ ┣ 📜 SimulationResultVO.java
┃ ┗ ...
┗ 📜 MetaboTrackApiApplication.java # Bootstrapping class
```

---

## 🚀 API Endpoints Reference

### 📝 1. Record Management (CRUD)
Standardized endpoints for managing daily metabolic snapshots.

| Method | Endpoint | Description |
| :---: | :--- | :--- |
| `GET` | `/api/record/{id}` | Retrieve specific record details |
| `GET` | `/api/record` | Paginated and filtered query of records |
| `POST`| `/api/record` | Create a new daily metabolic record |
| `PUT` | `/api/record/{id}` | Update dynamic/anomalous metrics only |
| `DELETE`| `/api/record` | Batch hard-delete of extreme outliers |

### 📊 2. Advanced Analytics (Insights)
High-level data aggregation and predictive analytics.

| Method | Endpoint | Description                                           |
| :---: | :--- |:------------------------------------------------------|
| `POST`| `/api/analytics/simulator` | Dynamic target predictor & simulator                  |
| `GET` | `/api/analytics/users/{userId}/percentile` | Individual gamified percentile ranking (Subquery)     |
| `GET` | `/api/analytics/population/efficiency-distribution`| Macro population efficiency tier distribution         |
| `GET` | `/api/analytics/leverage/sleep-quality` | Sleep quality vs. Cardiovascular burden (Data Binning)|
| `GET` | `/api/analytics/leverage/exercise-streak` | Benefits of continuous exercise habits                |

*(Note: Full interactive testing is available via the Swagger UI)*

---

## 🧠 Key Architectural Decisions

1. **DTO & VO Pattern for Security and Clarity:**
   Directly exposing database entities (`DailyMetabolicRecord`) compromises security. This project uses `RecordCreateDTO` and `RecordUpdateDTO` to prevent over-posting attacks, and uses highly structured `VOs` (Value Objects) to deliver clean data to the frontend.
2. **Database Engine Offloading:**
   For macro-analytics (like Population Distribution), transferring 15,000 rows into the JVM for calculation risks `OutOfMemoryError`. Instead, logic was offloaded to the database using native SQL `GROUP BY` and `CASE WHEN` (Data Binning) via MyBatis `@Select` annotations, achieving O(1) application-level memory complexity.
3. **Synthetic Rule Engine (Reverse Engineering):**
   The `/simulator` endpoint is not merely a database query. It reverse-engineers the physiological heuristic of the original dataset (e.g., efficiency = Calories / (Steps + 20 * ActiveMins)), applying non-linear scalers, multipliers, and hard-drop penalties for sleep deprivation entirely in Java memory.

---

## ⚙️ Getting Started

### Prerequisites
* Java 17 or higher
* MySQL 8.0+
* Maven 3.8+

### Setup Instructions
1. **Clone the repository:**
    ```bash
   git clone https://github.com/your-username/MetaboTrack-API.git
   cd MetaboTrack-API
   ```

2. **Configure Database:**
    * Create a MySQL database named `metabotrack_db`.
    * Update the credentials in `src/main/resources/application-dev.yaml`:
   ```yaml
    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/metabotrack_db?useSSL=false&serverTimezone=UTC
        username: your_username
        password: your_password
   ```

3. **Run the Application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Access Swagger UI Documentation:**
   Open your browser and navigate to: `http://localhost:8081/swagger-ui/index.html`

---

## 📎 Deliverables & Documentation
* 📄 **API Documentation:** [MetaboTrack_API_Documentation.pdf](./docs/MetaboTrack_API_Documentation.pdf)
* 📘 **Technical Report:** [MetaboTrack_API_Technical_Report.pdf](./docs/MetaboTrack_API_Technical_Report.pdf)
* 📊 **Presentation Slides:** [MetaboTrack_API_Presentation_Slides.pptx](./docs/MetaboTrack_API_Presentation_Slides.pptx)

---
*Developed for University Coursework. Explicit use of Generative AI has been declared and documented in the technical report appendix.*