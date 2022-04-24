package word.counter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import word.counter.exception.IllegalCharacterException;
import word.counter.model.Word;
import word.counter.service.WordCounterService;

@RestController
public class WordCounterController {

    @Autowired
    private WordCounterService wordCounterService;

    @PostMapping("/api/wordadd")
    public void createWord(@RequestBody Word word) throws IllegalCharacterException {
        wordCounterService.addWords(word.getWord());
    }

    @PostMapping("/api/wordcount")
    public long getWordCount(@RequestBody Word word) {
        return wordCounterService.countSimilarMeaningWords(word.getWord());
    }

}
