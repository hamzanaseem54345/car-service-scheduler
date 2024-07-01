# Car Service Scheduler

## Description
This application provides a RESTful API for scheduling car service appointments with service operators.

## Requirements
- Java 11 or higher
- Maven (for building the project, not necessary for running the packaged JAR)

## How to Run
1. **Clone the repository** or extract the provided ZIP file.
2. **Navigate to the project directory**.
3. **Build the project** (if you haven't already):
   ```sh
   mvn clean package

## Path for jar file
```sh
./car-service-scheduler/car-service-scheduler/target/car-service-scheduler-1.0-SNAPSHOT.jar
````

## Installation

Make sure you have the Java 11 installed. Download [here](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
Or run:
```shell
brew cask install java

export JAVA_11_HOME=$(/usr/libexec/java_home -v11)
alias java11='export JAVA_HOME=$JAVA_11_HOME'

# default to Java 11
java11
  ```

## API Documentation
[Swagger](http://localhost:8082/swagger-ui/index.html)


