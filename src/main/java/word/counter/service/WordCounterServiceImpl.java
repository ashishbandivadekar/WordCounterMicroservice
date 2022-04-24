package word.counter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import word.counter.exception.IllegalCharacterException;
import word.counter.translator.TranslatorService;

@Service
public class WordCounterServiceImpl implements WordCounterService{

    @Autowired
    private WordCounter wordCounter;
    @Autowired
    private TranslatorService translatorService;


    public WordCounterServiceImpl(WordCounter wordCounter, TranslatorService translatorService) {
        super();
        this.wordCounter = wordCounter;
        this.translatorService = translatorService;
    }

    @Override
    public void addWords(String word) throws IllegalCharacterException {
        if(null == word || !word.matches("[a-zA-Z]+")){
            throw new IllegalCharacterException("IllegalCharacterException: "+word+ "contains non-alphabet characters");
        }
        wordCounter.getAlphabetsOnlyWordList().add(word);
        System.out.println("------"+ wordCounter.getAlphabetsOnlyWordList()+"-----------");
    }

    @Override
    public long countSimilarMeaningWords(String word){
        if(null != word)
            return this.wordCounter.getAlphabetsOnlyWordList().stream().filter(s -> s.equalsIgnoreCase(word) || (null != translatorService.translateToEnglish(s) && translatorService.translateToEnglish(s).equalsIgnoreCase(word))).count();
        else
            return 0;
    }

    public WordCounter getWordCounter() {
        return wordCounter;
    }
}
