# WordCounterMicroservice
Microservice for Word counter

WordCounter is exposed as a Rest Microservice.

The clients will access the service using rest api 

http://localhost:8080/api/wordadd  (post operation for adding the word)
{
     "word": "abcd"    
}

http://localhost:8080/api/wordcount  (post operation for getting the wordcount)
{
     "word": "abcd"    
}

The microservice can be hosted on kubernetes cluster to provide resilience.
