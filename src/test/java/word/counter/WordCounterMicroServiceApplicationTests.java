package word.counter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import word.counter.service.WordCounterService;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
@SpringBootTest
class WordCounterMicroServiceApplicationTests {

	@Autowired
	private WordCounterService wordCounterservice;
	@Test
	public void contextLoads() {
		assertNotEquals(null, wordCounterservice);
	}

}
