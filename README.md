# T1 OpenSchool JWT Case
Добро пожаловать в проект реализации Spring Security с использованием JWT, выполненного в рамках открытой школы от компании Т1.

## Как собрать и запустить проект?
* Клонировать проект на локальную машину.
* Создать БД с необходимыми параметрами, таблицами и подключить к проекту.
* Запустить приложение.

Пример успешного получения токена при авторизации.
![image](https://github.com/user-attachments/assets/2e47d83a-dfd6-40de-bb5a-32f0771c3c1f)

Пример успешного обновления токена.
![image](https://github.com/user-attachments/assets/28e4d311-a41c-4bcd-922b-8854646e3d9f)

## Ипользованные технологии:
* Java 19
* Gradle
* Spring Actuator
* Spring Data JPA
* Spring Boot
* OpenAPI
* PostgreSQL
* Git

<details>
<summary>Техническое задание на разработку</summary>
Задание: Реализация аутентификации и авторизации с использованием Spring Security и JWT

Цель задания: Создать базовое веб-приложение с использованием Spring Security и JWT для аутентификации и авторизации пользователей.

Шаги задания:

Настройка проекта:

Создайте новый проект Spring Boot.

Настройка конфигурации безопасности:

Настройте базовую конфигурацию Spring Security для вашего приложения.

Используйте JWT для аутентификации пользователей.

Создайте класс для генерации и проверки JWT токенов.

Реализация контроллеров:

Создайте контроллеры для аутентификации и регистрации пользователей.

Реализуйте методы для создания нового пользователя и генерации JWT токена при успешной аутентификации.

Реализуйте сохранение пользователей в базу данных PostgreSQL.

Добавьте поддержку ролей пользователей и настройте авторизацию на основе ролей.

Тестирование:

Напишите модульные тесты для контроллеров и сервисов.

Убедитесь, что аутентификация и авторизация работают корректно.

Проверьте, что только аутентифицированные пользователи имеют доступ к защищенным ресурсам.

Документация:

Добавьте краткую документацию к вашему API с использованием Swagger или OpenAPI.

Результат задания: Рабочее веб-приложение с базовой аутентификацией и авторизацией на основе Spring Security и JWT, сопровождаемое модульными тестами и краткой документацией к API.
</details>
