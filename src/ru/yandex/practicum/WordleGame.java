package ru.yandex.practicum;

import ru.yandex.practicum.customExceptions.InvalidWordLengthException;
import ru.yandex.practicum.customExceptions.WordNotFoundInDictionaryException;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class WordleGame {

    private static final int STEPS = 6;
    private int currentStep;

    private String answer;

    private int answerLength;

    private WordleDictionary dictionary;

    private List<String> userWordsInput;
    private final List<String> userResults;

    private static final Random RANDOM = new Random();

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
        userWordsInput = new ArrayList<>();
        userResults = new ArrayList<>();
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

        String resultString = new String(result);

        userWordsInput.add(word);
        userResults.add(resultString);

        updateCurrentStep();

        return resultString;
    }

    public void validateWord(String word) throws InvalidWordLengthException,
            WordNotFoundInDictionaryException {
        if (word == null) {
            throw new WordNotFoundInDictionaryException("");
        }

        if (word.isEmpty()) {
            throw new WordNotFoundInDictionaryException(word);
        }

        if (word.length() != answerLength) {
            throw new InvalidWordLengthException(answerLength);
        }

        if (!dictionary.isWordInDictionary(word)) {
            throw new WordNotFoundInDictionaryException(word);
        }
    }

    public String giveHint() {

        List<String> possibleWords = new ArrayList<>();

        for (String candidate : dictionary.getWords()) {

            boolean valid = true;

            for (int i = 0; i < userWordsInput.size(); i++) {

                String guess = userWordsInput.get(i);
                String result = userResults.get(i);

                if (!matchesHint(candidate, guess, result)) {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                possibleWords.add(candidate);
            }
        }

        if (possibleWords.isEmpty()) {
            return "Подходящих слов не найдено";
        }

        return possibleWords.get(RANDOM.nextInt(possibleWords.size()));
    }

    private boolean matchesHint(String candidate, String guess, String result) {

        for (int i = 0; i < answerLength; i++) {

            char letter = guess.charAt(i);

            if (result.charAt(i) == '+') {

                if (candidate.charAt(i) != letter) {
                    return false;
                }

            } else if (result.charAt(i) == '^') {

                if (candidate.charAt(i) == letter ||
                        !candidate.contains(String.valueOf(letter))) {
                    return false;
                }

            } else if (result.charAt(i) == '-') {

                if (candidate.contains(String.valueOf(letter))) {
                    return false;
                }
            }
        }

        return true;
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
