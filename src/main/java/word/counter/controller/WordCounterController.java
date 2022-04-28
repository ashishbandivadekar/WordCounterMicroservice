package word.counter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import word.library.exception.IllegalCharacterException;
import word.counter.model.Word;
import word.library.service.WordCounterService;
import word.library.service.WordCounterServiceImpl;

@RestController
public class WordCounterController {

    @Autowired
    private WordCounterService wordCounterService;

    public WordCounterController(WordCounterService wordCounterService) {
        super();
        this.wordCounterService = wordCounterService;
    }

    @PostMapping("/api/wordadd")
    public void createWord(@RequestBody Word word) throws IllegalCharacterException {
        wordCounterService.addWords(word.getWord());
    }

    @PostMapping("/api/wordcount")
    public long getWordCount(@RequestBody Word word) {
        return wordCounterService.countSimilarMeaningWords(word.getWord());
    }

}
