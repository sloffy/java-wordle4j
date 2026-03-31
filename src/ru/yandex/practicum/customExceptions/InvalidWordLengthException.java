package ru.yandex.practicum.customExceptions;

public class InvalidWordLengthException extends GameException {
    public InvalidWordLengthException(int expectedLength) {
        super("Слово должно содержать " + expectedLength + " букв!");
    }
}
