# NewAvito / AdBoardService
Course project: Ads board application with user/admin roles and moderation

## Description
A full-featured web application for creating and managing ads with user and administrator roles, moderation system, search, and filtering.

The project runs in Docker and is launched via Docker Compose.

## Technologies
- **Backend:** Java, Spring Boot
- **Database:** PostgreSQL
- **Admin panel:** pgAdmin
- **Containerization:** Dpcker, Docker Compose
- **Ftrontend:** HTML, CSS, JS

 ## Features

 ### User
 - Registration and login
 - Creating, editing, and deleting ads
 - Viewing the list of ads
 - Viewing detailed ad information
 - Filtering by categories (e.g., “Houses”, “Services”, etc.)
 - Ability to report an ad
   - one user → one report per ad
   - after 3 reports, the ad is automatically blocked and hidden
  
### Administrator
- Administrator login
- Adding new administrators
- Viewing all ads and users
- Blocking/unblocking users
- Blocking/unblocking ads
- Viewing submitted reports
- Creating rules for ads (e.g., banning the use of certain words in ad descriptions)

---

## Running the project with Docker
### 1. Clone the repository
```bash
git clone https://github.com/TwilightKitsune/NewAvito.git
cd NewAvito
```

### 2. Start the containers
```bash
docker-compose up -d
```

### 3. Containers that will be created
 - **postgres** - PostgreSQL database
 - **pgadmin** - web interface for database management
 - **new-avito** - application backend

### 4. Access

- Application:
http://localhost:8080/
- pgAdmin:
http://localhost:5555/
(login/password — see docker-compose.yml)

---

## Screenshots
### Home page
<img width="1885" height="818" alt="Home page" src="https://github.com/user-attachments/assets/6f67efd5-f9f1-4577-a315-c068f5eeac2d" />


### Ad details page
<img width="1211" height="753" alt="Ad details page" src="https://github.com/user-attachments/assets/4189fe3e-24e8-47ba-8cb2-5a3ce539cbc2" />


### Filtering and search
<img width="1804" height="489" alt="Filtering and search" src="https://github.com/user-attachments/assets/5c5c7d9e-9489-4036-993a-0ab930471789" />


### Admin panel: Dashboard
<img width="1605" height="498" alt="Admin panel: Dashboard" src="https://github.com/user-attachments/assets/e6c9436e-fd29-4ac1-b9e7-88d001e308f3" />


### Admin panel: Create rule
<img width="670" height="429" alt="Admin panel: Create rule" src="https://github.com/user-attachments/assets/605e7168-82dd-49a7-a937-acbee592ff74" />


### Admin panel: User management
<img width="963" height="901" alt="Admin panel: User management" src="https://github.com/user-attachments/assets/e7ca7bc0-f2c7-43f8-a43c-fd42fbf989bf" />
