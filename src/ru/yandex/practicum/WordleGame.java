package ru.yandex.practicum;

import ru.yandex.practicum.customExceptions.InvalidWordLengthException;
import ru.yandex.practicum.customExceptions.WordNotFoundInDictionaryException;

import java.util.List;
import java.util.ArrayList;

/*
в этом классе хранится словарь и состояние игры
    всё что пользователь вводил

в этом классе нужны методы, которые
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private static final int STEPS = 6;
    private int currentStep;

    private String answer;

    private int answerLength;

    private WordleDictionary dictionary;

    private List<String> userInput;

    public WordleGame(WordleDictionary dictionary) {
        if (dictionary == null) {
            throw new IllegalArgumentException("Dictionary cannot be null");
        }

        if (dictionary.getDictionarySize() == 0) {
            throw new IllegalArgumentException("Dictionary is empty");
        }

        this.dictionary = dictionary;
        currentStep = 0;
        answer = dictionary.getRandomWord();
        answerLength = answer.length();
        userInput = new ArrayList<>();
    }

    private void updateCurrentStep() {
        currentStep++;
    }

    public boolean isAnswerCorrect(String result) {
        return result.equals("+".repeat(answerLength));
    }

    // Анализ совпадения слова с ответом
    public String getComparisonResult(String word) throws InvalidWordLengthException,
            WordNotFoundInDictionaryException {
        validateWord(word);

        char[] result = new char[answerLength];
        boolean[] used = new boolean[answerLength];

        for (int i = 0; i < answerLength; i++) {
            if (word.charAt(i) == answer.charAt(i)) {
                result[i] = '+';
                used[i] = true;
            }
        }

        for (int i = 0; i < answerLength; i++) {
            if (result[i] == '+') {
                continue;
            }

            char c = word.charAt(i);
            boolean found = false;

            for (int j = 0; j < answerLength; j++) {
                if (!used[j] && c == answer.charAt(j)) {
                    used[j] = true;
                    found = true;
                    break;
                }
            }

            if (found) {
                result[i] = '^';
            } else {
                result[i] = '-';
            }
        }

        updateCurrentStep();

        return new String(result);
    }

    public void validateWord(String word) throws InvalidWordLengthException,
            WordNotFoundInDictionaryException {
        if (word == null) {
            throw new WordNotFoundInDictionaryException("");
        }

        if (word.length() != answerLength) {
            throw new InvalidWordLengthException(answerLength);
        }

        if (!dictionary.isWordInDictionary(word)) {
            throw new WordNotFoundInDictionaryException(word);
        }
    }

    public int getWordLength() {
        return answerLength;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public int getSteps() {
        return STEPS;
    }

    public String getAnswer() {
        return answer;
    }
}
