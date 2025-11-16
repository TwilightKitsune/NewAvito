# NewAvito / AdBoardService
Course project: Ads board application with user/admin roles and moderation

## Описание
Полноценное веб-приложение для публикации и управления объявлениями с ролями пользователя и администратора, модерацией и фильтрацией.

Проект работает в Docker и поднимается через Docker Compose.

## Технологии
- **Backend:** Java, Spring Boot
- **База данных:** PostgreSQL
- **Админ-панель:** pgAdmin
- **Контейнеризация:** Dpcker, Docker Compose
- **Ftrontend:** HTML, CSS, JS

 ## Функционал

 ### Пользователь
 - Регистрация и авторизация
 - Создание, редактирование и удаление объявлений
 - Просмотр списка объявлений
 - Просмотр детальной карточки объявления
 - Фильтрация по категориям (например: "Дома", "Услуги" и др.)
 - Возможность пожаловаться на объявление
   - один пользователь → одна жалоба на одно объявление
   - после 3 жалоб объявление автоматически блокируется и скрывается
  
### Администратор
- Авторизация администратора
- Добавление нового администратора
- Просмотр всех объявлений и пользователей
- Блокировка/разблокировка пользователей
- Блокировка/разблокировка объявлений
- Просмотр полученных жалоб
- Создание правил для объявлений (запрет использоваения определённых слов в тексте объявления)

---

## Запуск проекта через Docker
### 1. Склонировать репозиторий
```bash
git clone https://github.com/TwilightKitsune/NewAvito.git
cd NewAvito
```

### 2. Поднять контейнеры
```bash
docker-compose up -d
```

### 3. Контейнеры, которые будут созданы
 - **postgres** - база данных PostgreSQL
 - **pgadmin** - веб-интерфейс для управления базой

 - **new-avito** - backend приложения

### 4. Доступы

- Приложение:
http://localhost:8080/
- pgAdmin:
http://localhost:5555/
(логин/пароль см. в docker-compose.yml)

---

## Скриншоты
### Главная страница
<img width="1885" height="818" alt="Главная страница" src="https://github.com/user-attachments/assets/49e68e34-51e4-48fa-8aae-32cea128db58" />


### Карточка объявления
<img width="1211" height="753" alt="Карточка объявления" src="https://github.com/user-attachments/assets/44055e09-dde5-43e8-ab5d-20dd29518d26" />


### Фильтрация и поиск
<img width="1804" height="489" alt="Фильтрация и поиск" src="https://github.com/user-attachments/assets/cfaea8a0-cb43-43b6-bd7b-6a78e67655fd" />


### Админ-панель: Главная
<img width="1605" height="498" alt="Админ-панель: Главная" src="https://github.com/user-attachments/assets/00e14bae-419c-4ac8-90f2-9a48fe36215e" />


### Админ-панель: Создание правила
<img width="670" height="429" alt="Админ-панель: Создание правила" src="https://github.com/user-attachments/assets/30ba6779-31bc-44df-adff-c8cc0eb6e241" />


### Админ-панель: Управление пользователями
<img width="963" height="901" alt="Админ-панель: Управление пользователями" src="https://github.com/user-attachments/assets/db47d889-adc0-4cbb-bf08-ececcfaaad12" />
