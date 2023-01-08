# inpost-demo-app
Recruitment process project

## Prerequisites
To run application, Maven, Java 17 and Docker should be installed on the machine

## How to run
Step 1. Build the project using maven at main application directory:
```shell
mvn clean package
```

Step 2. Build docker image and run the application in a docker container using provided console command
```shell
docker build -t inpost-demo-app:latest . && docker run -p8080:8080 inpost-demo-app:latest
```
NOTE_1: keep in mind that using the provided command implies the 8080 port is available 

## Usage
You can check what endpoints are available via 
`` http://localhost:8080/swagger-ui/``

Additionally, a postman collection with example requests has been provided in the ``InpostDemoApp.postman_collection.json`` 

NOTE_1:
Please be aware that the /admin/** endpoint requires a Basic Authentication using the following credentials:
```
login: admin
password: password
```

## Assumptions
Below list of assumptions that were taken into consideration during development:
1. List od products is fixed and cannot be changed
2. Discount policies will not hold new values after restart
3. Usage of only one discount policy is permitted per calculation
4. Amount based discount policy has a configurable threshold of the maximum percentage discount
5. If no policy type is provided in request, the calculation does not include any discount
6. Default values for policies: 
    - Amount based policy: Max Discount - 25%, discount increment per item - 0.5%
    - Percentage based policy: Base percentage - 20%


## Additional Info
- Approach chosen: Medium (change policy in runtime - available only for admin user, no external database)
- Time of development: approx. 16-20 hrs
- Test coverage for lines: approx. 86%
