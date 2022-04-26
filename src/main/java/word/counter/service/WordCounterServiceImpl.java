package word.counter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import word.counter.exception.IllegalCharacterException;
import word.counter.translator.TranslatorService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class WordCounterServiceImpl implements WordCounterService{

    private Map<String, Integer> wordCounterMap = new ConcurrentHashMap<>();

    @Autowired
    private TranslatorService translatorService;


    public WordCounterServiceImpl(TranslatorService translatorService) {
        super();
        this.translatorService = translatorService;
    }

    @Override
    public void addWords(String word) throws IllegalCharacterException {
        if(null == word || !word.matches("[a-zA-Z]+")){
            throw new IllegalCharacterException("IllegalCharacterException: "+word+ "contains non-alphabet characters");
        }

        String translatedWord = translatorService.translateToEnglish(word);
        if (null != translatedWord){
            if(wordCounterMap.containsKey(translatedWord.toUpperCase())){
                wordCounterMap.computeIfPresent(translatedWord.toUpperCase(),(k,v)->v+1);
            }else{
                wordCounterMap.put(translatedWord.toUpperCase(),1);
            }
        }
    }

    @Override
    public int countSimilarMeaningWords(String word){
        if(null != word && wordCounterMap.containsKey(word.toUpperCase()))
            return wordCounterMap.get(word.toUpperCase()) ;
        else
            return 0;
    }

    public Map<String, Integer> getWordCounterMap() {
        return wordCounterMap;
    }

}
