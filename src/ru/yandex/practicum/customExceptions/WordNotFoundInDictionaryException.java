package ru.yandex.practicum.customExceptions;

public class WordNotFoundInDictionaryException extends GameException {
    public WordNotFoundInDictionaryException(String word) {
        super("Слова \"" + word + "\" нет в словаре!");
    }
}
