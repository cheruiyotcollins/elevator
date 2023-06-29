# Getting Started


## About Ezra Elevator Management System
This is a Elevator Management system with built using spring boot 3.0 and java 20

To get started with this project, you will need to have the following installed on your local machine:
## Database Setup 
In this project MySql db have been used, make sure that this is installed in your machine.
when Mysql server is up, create database schema called `elevator_db` as this is the one that the application will use and will auto generate all the required tables

## Dependencies
JDK 17+ Maven 3+ To build and run the project, follow these steps:

## Setting up the project
Clone the repository with the command `git clone https://github.com/cheruiyotcollins/elevator.git` for https or ` git clone git@github.com:cheruiyotcollins/elevator.git` for ssh
Navigate to the project directory and :
1. Build the project: `mvn clean install -DskipTests` This will build a jar file in target folder
2. If you are running the application as a standalone jar file, be sure to copy `application.properties` into the same location as the jar file and run using command `java -jar jarname.jar &`. & is included to run it on background.
3. If you are running the project using an IDE or from command line use: `mvn spring-boot:run` .
4. 
5.The application will be available on http://localhost:9001.
6.If you want to build a dockerfile for your project, ensure you have run `step 1` correctly and works without any issues. You can then build a docker image using the command `docker build -t elevator .`

## Swagger documentation
Make sure the application is up and access it via link the endpoint `URI/swagger-ui/index.html i.e http://localhost:9001/swagger-ui/index.html`

