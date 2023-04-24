Run microservices

 Populate next environment variables in application.tml files for Config-Server and Twitter-To-Kafka-Service 
- GIT_USER
- GIT_USER_SECRET
- USER_NAME 
- USER_SECRET

Useful commands:
- for checking topics use command `docker run -it --network=host confluentinc/cp-kafkacat kafkacat -L -b localhost:19092`