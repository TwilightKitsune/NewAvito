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
<img width="1885" height="818" alt="Главная страница" src="https://github.com/user-attachments/assets/6f67efd5-f9f1-4577-a315-c068f5eeac2d" />


### Карточка объявления
<img width="1211" height="753" alt="Карточка объявления" src="https://github.com/user-attachments/assets/4189fe3e-24e8-47ba-8cb2-5a3ce539cbc2" />


### Фильтрация и поиск
<img width="1804" height="489" alt="Фильтрация и поиск" src="https://github.com/user-attachments/assets/5c5c7d9e-9489-4036-993a-0ab930471789" />


### Админ-панель: Главная
<img width="1605" height="498" alt="Админ-панель: Главная" src="https://github.com/user-attachments/assets/e6c9436e-fd29-4ac1-b9e7-88d001e308f3" />


### Admin panel: Create rule
<img width="670" height="429" alt="Admin panel: Create rule" src="https://github.com/user-attachments/assets/605e7168-82dd-49a7-a937-acbee592ff74" />


### Админ-панель: Создание правила
<img width="963" height="901" alt="Админ-панель: Создание правила" src="https://github.com/user-attachments/assets/e7ca7bc0-f2c7-43f8-a43c-fd42fbf989bf" />
