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

### How to start up the application
* You need to have docker installed, or if you have postgresql installed locally, you can skip second step
* Run docker-compose file under docker directory using, it will create local instance of postgre DB
```docker-compose up -d```
* Connect to DB and create new database using ``CREATE DATABASE "jwt-db"``
* Create user using ``create user jwtuser with password 'letmein'`` if you already have DB installed, or change username and password in application.yml file to match your existing database user.

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

