# WordCounterMicroservice
Microservice for Word counter

WordCounter is exposed as a Rest service.

ConcurrentHashMap is used to provide thread safety for write operations .

To run the Rest service locally:
java -jar target/WordCounterMicroService-0.0.1-SNAPSHOT.jar

The clients will access the service using rest api 

http://localhost:8080/api/wordadd  (post operation for adding the word)
{
     "word": "abcd"    
}

http://localhost:8080/api/wordcount  (post operation for getting the wordcount)
{
     "word": "abcd"    
}

Can create a Docker image of WordCounterMicroservice application using the config provided by springboot maven plugin.(spring-boot:build-image)
WordCounterMicroservice  application can be hosted on the docker container by spinning up the docker image.

The WordCounterMicroservice image can be hosted on kubernetes cluster to provide resilience.
