package word.counter.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Word {
    private String word;

    public Word() {
    }

    public Word(String word) {
        this.word = word;
    }
}
