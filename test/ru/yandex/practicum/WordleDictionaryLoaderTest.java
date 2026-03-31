package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryLoaderTest {

    @Test
    void testLoadDictionary() throws IOException {

        String filename = "test_words.txt";

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("арбуз\n");
            writer.write("дом\n");      // не 5 букв
            writer.write("ёлкаа\n");    // ё должно замениться
        }

        WordleDictionaryLoader loader = new WordleDictionaryLoader();
        WordleDictionary dictionary = loader.loadWordleDictionary(filename);

        assertEquals(2, dictionary.getDictionarySize());
        assertTrue(dictionary.isWordInDictionary("арбуз"));
        assertTrue(dictionary.isWordInDictionary("елкаа"));
    }
}