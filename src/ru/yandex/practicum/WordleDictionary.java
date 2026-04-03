package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordleDictionary {

    private List<String> words;

    public WordleDictionary(List<String> words) {
        this.words = new ArrayList<>(words);
    }

    public List<String> getWords() {
        return words;
    }

    public boolean isWordInDictionary(String word) {
        return words.contains(word);
    }

    public String getRandomWord() {
        return getWordByIndex(new Random().nextInt(getDictionarySize()));
    }

    public String getWordByIndex(int index) {
        return words.get(index);
    }

    public int getDictionarySize() {
        return words.size();
    }
}
