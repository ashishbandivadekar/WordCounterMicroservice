package word.counter.service;

import word.counter.exception.IllegalCharacterException;

public interface WordCounterService {


    public void addWords(String word) throws IllegalCharacterException;

    public int countSimilarMeaningWords(String word);
}
