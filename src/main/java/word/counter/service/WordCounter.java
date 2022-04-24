package word.counter.service;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component("wordCounter")
public class WordCounter {

    private List<String> alphabetsOnlyWordList;

    public WordCounter() {
        this.alphabetsOnlyWordList = new ArrayList<>();
    }

}
