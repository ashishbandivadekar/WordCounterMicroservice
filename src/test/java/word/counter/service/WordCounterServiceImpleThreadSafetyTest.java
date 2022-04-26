package word.counter.service;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import word.counter.exception.IllegalCharacterException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WordCounterServiceImpleThreadSafetyTest {

    @Autowired
    private WordCounterServiceImpl wordCounterServiceImpl;


    @Test
    public void testForThreadSafety() throws InterruptedException {

        Runnable mapWriteOperations = () -> {
            try {
                wordCounterServiceImpl.addWords("olla");
                wordCounterServiceImpl.addWords("salut");
                wordCounterServiceImpl.addWords("blum");
                wordCounterServiceImpl.addWords("Rock");
                wordCounterServiceImpl.addWords("call");
                wordCounterServiceImpl.addWords("roll");
            } catch (IllegalCharacterException e) {
                throw new RuntimeException(e);
            }
        };

        Thread t1 = new Thread(mapWriteOperations);
        Thread t2 = new Thread(mapWriteOperations);
        Thread t3 = new Thread(mapWriteOperations);
        Thread t4 = new Thread(mapWriteOperations);
        Thread t5 = new Thread(mapWriteOperations);

        t1.start();t2.start();t3.start();t4.start();t5.start();
        t1.join();t2.join();t3.join();t4.join();t5.join();

        assertEquals(Integer.valueOf(5),wordCounterServiceImpl.getWordCounterMap().get("OLLA"));
        assertEquals(Integer.valueOf(5),wordCounterServiceImpl.getWordCounterMap().get("SALUT"));
        assertEquals(Integer.valueOf(5),wordCounterServiceImpl.getWordCounterMap().get("BLUM"));
        assertEquals(Integer.valueOf(5),wordCounterServiceImpl.getWordCounterMap().get("ROCK"));
        assertEquals(Integer.valueOf(5),wordCounterServiceImpl.getWordCounterMap().get("CALL"));
        assertEquals(Integer.valueOf(5),wordCounterServiceImpl.getWordCounterMap().get("ROLL"));
    }
}
