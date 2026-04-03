package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryTest {

    private WordleDictionary dictionary;

    @BeforeEach
    void init() {
        dictionary = new WordleDictionary(
                List.of("арбуз", "банан", "вишня"));
    }

    @Test
    void testDictionarySize() {
        assertEquals(3, dictionary.getDictionarySize());
    }

    @Test
    void testWordExists() {
        assertTrue(dictionary.isWordInDictionary("арбуз"));
    }

    @Test
    void testWordNotExists() {
        assertFalse(dictionary.isWordInDictionary("груша"));
    }

    @Test
    void testGetWordByIndex() {
        assertEquals("банан", dictionary.getWordByIndex(1));
    }

    @Test
    void testGetRandomWord() {
        String word = dictionary.getRandomWord();

        assertNotNull(word);
        assertTrue(dictionary.getWords().contains(word));
    }
}
