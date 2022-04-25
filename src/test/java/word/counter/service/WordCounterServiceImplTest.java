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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import org.springframework.test.context.junit.jupiter.SpringExtension;


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
        System.out.println(wordCounterServiceImpl);
        System.out.println(wordCounterServiceImpl.getWordCounter());
        wordCounterServiceImpl.getWordCounter().getAlphabetsOnlyWordList().clear();
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
    public void shouldInsertWordInWordCounterListSuccessfully() throws IllegalCharacterException {

        List<String> expectedWordList = new ArrayList<>(Arrays.asList("flower","TREE","flor","blum","Rock"));

        wordCounterServiceImpl.addWords("flower");
        wordCounterServiceImpl.addWords("TREE");
        wordCounterServiceImpl.addWords("flor");
        wordCounterServiceImpl.addWords("blum");
        wordCounterServiceImpl.addWords("Rock");

        assertEquals(expectedWordList,wordCounterServiceImpl.getWordCounter().getAlphabetsOnlyWordList());

    }

    @Test
    public void shouldReturnWordCountSuccessfullyFromWordCounterList() throws IllegalCharacterException{
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
    public void shouldReturnWordCountSuccessfullyWhenWordNotPresentInWordCounterListButPresentIntranslatorService() throws IllegalCharacterException{

        when(translateService.translateToEnglish("olla")).thenReturn("hello");
        when(translateService.translateToEnglish("salut")).thenReturn("Hello");

        wordCounterServiceImpl.addWords("olla");
        wordCounterServiceImpl.addWords("salut");
        wordCounterServiceImpl.addWords("blum");
        wordCounterServiceImpl.addWords("Rock");

        assertEquals(2,wordCounterServiceImpl.countSimilarMeaningWords("hello"));

    }

}
