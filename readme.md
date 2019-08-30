# Spring Boot, Mongo and Elasticsearch demo

1. Clone the project then `cd` to its folder

2. Build it with Maven:
    ```shell
    mvn package 
    ```
3. Run the environment:
    ```shell
    docker-compose up -d
    ```
4. Load the following collection to Postman: https://documenter.getpostman.com/view/788154/S1ENye6m

5. Run requests in folder `Person` to create/update/delete entities 

6. Run the following requests to see the mirrored data from Elasticsearch  
    ```http
    GET http://localhost:8080/elastic/people
    GET http://localhost:8080/elastic/cars
    ```
7. Open Kibana: `http://localhost:5601`
    - check 'APM/demo-service/Transactions'
    - check 'Management/Elasticsearch/Index Management'   
8. Shut down the environment
    ```shell
    docker-compose down
    ```


