Run microservices

 add to Config-Server(used for externalize configuration) run configuration next env variables with values:
- GIT_USER
- GIT_USER_SECRET
- USER_NAME 
- USER_SECRET

add to Twitter-To-Kafka-Service run configuration next env variables with values:
- USER_NAME
- USER_SECRET

Useful commands:
- for checking topics use command `docker run -it --network=host confluentinc/cp-kafkacat kafkacat -L -b localhost:19092`