package word.counter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import word.counter.exception.IllegalCharacterException;
import word.counter.translator.TranslatorService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class WordCounterServiceImplTest {

    @Mock
    private TranslatorService translateService;

    @Autowired
    @InjectMocks
    private WordCounterServiceImpl wordCounterServiceImpl;

     @BeforeEach
    public void clear(){
        wordCounterServiceImpl.getWordCounterMap().clear();
    }

    @Test
    public void shouldThrowIllegalcharacterExceptionwhenThereIsNonAlphabeticCharacterInTheWord() throws IllegalCharacterException{
        assertThrows(IllegalCharacterException.class,  () -> {
            wordCounterServiceImpl.addWords("jasdhja>@##$%^&*");
        });

        assertThrows(IllegalCharacterException.class,  () -> {
            wordCounterServiceImpl.addWords(null);
        });

        assertThrows(IllegalCharacterException.class,  () -> {
            wordCounterServiceImpl.addWords("jasdhja12345");
        });
    }

    @Test
    public void shouldInsertWordInWordCounterMapSuccessfully() throws IllegalCharacterException {
        when(translateService.translateToEnglish("flower")).thenReturn("flower");
        when(translateService.translateToEnglish("flor")).thenReturn("FLOWER");
        when(translateService.translateToEnglish("blum")).thenReturn("flower");
        when(translateService.translateToEnglish("TREE")).thenReturn("TREE");
        when(translateService.translateToEnglish("Rock")).thenReturn("Rock");

        wordCounterServiceImpl.addWords("flower"); // wil be returned as flower by translation service
        wordCounterServiceImpl.addWords("TREE");
        wordCounterServiceImpl.addWords("flor");   // wil be returned as flower by translation service
        wordCounterServiceImpl.addWords("blum");   // wil be returned as flower by translation service
        wordCounterServiceImpl.addWords("Rock");


        assertEquals(Integer.valueOf(3),wordCounterServiceImpl.getWordCounterMap().get("FLOWER"));
        assertEquals(Integer.valueOf(1),wordCounterServiceImpl.getWordCounterMap().get("TREE"));
        assertEquals(Integer.valueOf(1),wordCounterServiceImpl.getWordCounterMap().get("ROCK"));

    }

    @Test
    public void shouldReturnWordCountSuccessfullyFromWordCounterList() throws IllegalCharacterException{
        when(translateService.translateToEnglish("flower")).thenReturn("flower");
        when(translateService.translateToEnglish("flor")).thenReturn("FLOWER");
        when(translateService.translateToEnglish("blum")).thenReturn("flower");
        when(translateService.translateToEnglish("TREE")).thenReturn("tree");
        when(translateService.translateToEnglish("Rock")).thenReturn(null);


        wordCounterServiceImpl.addWords("flower");
        wordCounterServiceImpl.addWords("TREE");
        wordCounterServiceImpl.addWords("flor");
        wordCounterServiceImpl.addWords("blum");
        wordCounterServiceImpl.addWords("Rock");

        assertEquals(3,wordCounterServiceImpl.countSimilarMeaningWords("flower"));

    }

    @Test
    public void shouldReturnZeroWordCountWhenWordNotPresentInWordCounterList() throws IllegalCharacterException{

        wordCounterServiceImpl.addWords("flower");
        wordCounterServiceImpl.addWords("TREE");
        wordCounterServiceImpl.addWords("flor");
        wordCounterServiceImpl.addWords("blum");
        wordCounterServiceImpl.addWords("Rock");

        assertEquals(0,wordCounterServiceImpl.countSimilarMeaningWords(null));
        assertEquals(0,wordCounterServiceImpl.countSimilarMeaningWords("mountain"));

    }

    @Test
    public void shouldReturnWordCountSuccessfullyWhenOnlyNonEnglishWordAreAdded() throws IllegalCharacterException{

        when(translateService.translateToEnglish("olla")).thenReturn("hello");
        when(translateService.translateToEnglish("salut")).thenReturn("Hello");

        wordCounterServiceImpl.addWords("olla");
        wordCounterServiceImpl.addWords("salut");
        wordCounterServiceImpl.addWords("blum");
        wordCounterServiceImpl.addWords("Rock");

        assertEquals(2,wordCounterServiceImpl.countSimilarMeaningWords("hello"));

    }


}
