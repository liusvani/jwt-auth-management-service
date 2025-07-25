# Microservice: User Manager

## Overview
The **User Manager** microservice is responsible for managing the security of system users. It provides a robust API that allows CRUD operations to be handled on this entity, ensuring compliance with defined business rules.

It also incorporates a highly customizable authentication and access control system using Spring Security. To strengthen security, JWT token generation is implemented using custom filters, and route protection is established based on the roles assigned to each user.

## Features

### Key Features
1. **User Management**
    - Define the username, password, email, firstname, lastname y role.
    - Enable or disable user using the `enabled` column.
    - Define the username, password, email, firstname, lastname y role.
    - DataInitializer create an admin user when not exist.

2. **Auth**
    - Login user.
    - Logout user.
    - Refresh access token.


---

## API Endpoints

### Users API
- **Base Path:** `/user`

| Method | Endpoint      | Description                     |
|--------|---------------|---------------------------------|
| POST   | `/user`       | Create a new user.              |
| GET    | `/users`      | List all user.                  |
| GET    | `/users/{id}` | Get an user by ID.              |
| PATH   | `/users/{id}` | Disabled or enabled user by ID. |
| UPDATE | `/user/{id}`  | Update an user by ID.           |
| DELETE | `/users/{id}` | Delete an user by ID.           |

### Auth API
- **Base Path:** `/auth`

| Method | Endpoint       | Description           |
|--------|----------------|-----------------------|
| POST   | `/auth/login`  | Login user.           |
| POST   | `/auth/logout` | Logout user.          |
| POST   | `/auth/refres` | Refresh access token. |

---

## Technology Stack

- **Java**: Programming language.
- **Spring Boot**: Framework for building the microservice.
- **Spring Security**: Framework for user authentication, access control, and route protection based on defined permissions
- **Jsonwebtoken**: JWT transmits secure data between parties, useful in authentication and authorization for apps and APIs.
- **Lombok**: Lombok simplifies development by avoiding repetitive code through annotations
- **Jakarta Validation**: For request validation.
- **Spring Web**: For REST controllers.
- **Spring Data JPA**: Simplifies access to databases using the Java Persistence API.
- **PostgreSQL**: Assumed as the database layer.
- **Swagger**: Open source toolkit that makes it easy to design, document, test, and consume RESTful APIs.


---

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/WEECOVER/ms-campaign-manager.git
   ```

2. Navigate to the project directory:
   ```bash
   cd ms-campaign-manager
   ```

3. Build the project:
   ```bash
   ./mvnw clean install
   ```

4. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

5. The service will be available at `http://localhost:8080`.

---

## Example Payloads

### Campaign Payloads
#### Create Campaign
```json
{
  "name": "Winter Sale",
  "description": "End of year discounts",
  "startDate": "2025-01-01",
  "endDate": "2025-02-01",
  "status": "ACTIVE"
}
```

### Discount Payloads
#### Create Discount
```json
{
  "campaignId": 1,
  "code": "WINTER20",
  "type": "PERCENTAGE",
  "value": 20,
  "usageLimit": 100
}
```

### Coupon Payloads
#### Generate Coupons
```json
{
  "discountId": 1,
  "quantity": 50
}
```

---

## License
This project is licensed under the MIT License. See the LICENSE file for details.
