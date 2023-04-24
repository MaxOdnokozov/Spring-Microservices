Run microservices locally

add to Config-Server run configuration next env variables with values:
- GIT_USER
- GIT_USER_SECRET
- USER_NAME 
- USER_SECRET

add to Twitter-To-Kafka-Service run configuration next env variables with values:
- USER_NAME
- USER_SECRET

Run microservices in Docker Containers

change `placeholder` to real value in `docker-compose/services.yml` file for next environments:

    config-server:

    "SPRING_SECURITY_USER_NAME"
    "SPRING_SECURITY_USER_PASSWORD"
    "SPRING_CLOUD_CONFIG_SERVER_GIT_USERNAME"
    "SPRING_CLOUD_CONFIG_SERVER_GIT_PASSWORD"

    twitter-to-kafka-server:
    
    "SPRING_CLOUD_CONFIG_USERNAME"
    "SPRING_CLOUD_CONFIG_PASSWORD"


Useful commands:
- for checking topics use command `docker run -it --network=host confluentinc/cp-kafkacat kafkacat -L -b localhost:19092`