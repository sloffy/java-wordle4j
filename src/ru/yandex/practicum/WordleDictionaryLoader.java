package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionaryLoader {

    public WordleDictionary loadWordleDictionary(String fileName) throws IOException {
        List<String> dictionatyList = new ArrayList<>();
        int wordsLength = 5;

        try (BufferedReader fileReader = new BufferedReader(
                new FileReader(fileName, StandardCharsets.UTF_8))) {
            String line;

            while ((line = fileReader.readLine()) != null) {
                if (line.length() == wordsLength) {
                    dictionatyList.add(line.toLowerCase().replace('ё', 'е'));
                }
            }
        }
        return new WordleDictionary(dictionatyList, wordsLength);
    }
}
