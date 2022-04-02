# JWT Demo

### Overview
This demo is my point of view of how spring boot application should look like, code structure and clean code principles.

The system is nothing but JWT handling using java 16 and spring boot 

### Features
* Rest API best practices 
* Spring Security 
* Exception Handling using AOP
* Junit 5 and Mockito
* Integration Tests with Test Containers
* Jacoco test coverage

### How to start up the application?
* You need to have docker installed, or if you have postgresql installed locally, you can skip second step
* Run docker-compose file under docker directory using, it will create local instance of postgre DB
```docker-compose up -d```
* Connect to DB and create new database using ``CREATE DATABASE "jwt-db"``
* Create user using ``create user jwtuser with password 'letmein'`` if you already have DB installed, or change username and password in application.yml file to match your existing database user.


### Application Testing
The project has admin setup under main class with user defined within application.yml (admin.details), you can use the default "msweelam" with password "sweelam123" to test APIs.
Or you can change it and use a user details of your choice.

Some curl command to try
* Login API
````
curl --location --request POST 'localhost:8082/api/login' \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --data-urlencode 'username=msweelam' \
  --data-urlencode 'password=sweelam123'
````
* Add user you need to replace the **access_token** with the provided header token from login API 
````
curl --location --request POST 'localhost:8082/api/user' \
--header 'Authorization: Bearer <access_token>' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Alaa Mahmoud",
    "username": "alaMahmoud",
    "password": "all123",
    "roles": [
        {
            "name": "USER"
        }
    ]
}'
````
* Refresh token API using the **refresh_token** provided from login if **access_token** expired
````
curl --location --request GET 'localhost:8082/api/token' \
--header 'Authorization: Bearer <refresh_token>' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Alaa Mahmoud",
    "username": "alaMahmoud",
    "password": "all123",
    "roles": [
        {
            "name": "USER"
        }
    ]
}'
````
* Retrieve al users 
````
curl --location --request GET 'localhost:8082/api/user' \
--header 'Authorization: Bearer <access_token>'
````

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

