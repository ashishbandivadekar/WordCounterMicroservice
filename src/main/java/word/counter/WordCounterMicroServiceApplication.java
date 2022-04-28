package word.counter;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import word.library.exception.IllegalCharacterException;

@SpringBootApplication
@ComponentScan({"word.counter","word.library"})
public class WordCounterMicroServiceApplication {

	public static void main(String[] args) throws IllegalCharacterException {
		SpringApplication.run(WordCounterMicroServiceApplication.class, args);
	}
}
