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
<img width="1885" height="818" alt="Home page" src="https://github.com/user-attachments/assets/49e68e34-51e4-48fa-8aae-32cea128db58" />


### Ad details page
<img width="1211" height="753" alt="Ad details page" src="https://github.com/user-attachments/assets/44055e09-dde5-43e8-ab5d-20dd29518d26" />


### Filtering and search
<img width="1804" height="489" alt="Filtering and search" src="https://github.com/user-attachments/assets/cfaea8a0-cb43-43b6-bd7b-6a78e67655fd" />


### Admin panel: Dashboard
<img width="1605" height="498" alt="Admin panel: Dashboard" src="https://github.com/user-attachments/assets/00e14bae-419c-4ac8-90f2-9a48fe36215e" />


### Admin panel: Create rule
<img width="670" height="429" alt="Admin panel: Create rule" src="https://github.com/user-attachments/assets/30ba6779-31bc-44df-adff-c8cc0eb6e241" />


### Admin panel: User management
<img width="963" height="901" alt="Admin panel: User management" src="https://github.com/user-attachments/assets/db47d889-adc0-4cbb-bf08-ececcfaaad12" />
