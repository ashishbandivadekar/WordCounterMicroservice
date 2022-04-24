package word.counter;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import word.counter.exception.IllegalCharacterException;
import word.counter.service.WordCounterService;
import word.counter.service.WordCounterServiceImpl;

@SpringBootApplication
public class WordCounterMicroServiceApplication {

	public static void main(String[] args) throws IllegalCharacterException {
		SpringApplication.run(WordCounterMicroServiceApplication.class, args);
	}
}
