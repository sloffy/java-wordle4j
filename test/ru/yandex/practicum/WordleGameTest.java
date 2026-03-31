package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.customExceptions.InvalidWordLengthException;
import ru.yandex.practicum.customExceptions.WordNotFoundInDictionaryException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleGameTest {

    private WordleGame game;

    @BeforeEach
    void init() {
        WordleDictionary dictionary =
                new WordleDictionary(List.of("арбуз"), 5);

        game = new WordleGame(dictionary);
    }

    @Test
    void testInvalidWordLength() {

        assertThrows(
                InvalidWordLengthException.class,
                () -> game.getComparisonResult("дом")
        );
    }

    @Test
    void testWordNotInDictionary() {

        assertThrows(
                WordNotFoundInDictionaryException.class,
                () -> game.getComparisonResult("груша")
        );
    }

    @Test
    void testCorrectGuess() throws Exception {

        String result = game.getComparisonResult("арбуз");

        assertEquals("+++++", result);
        assertTrue(game.isAnswerCorrect(result));
    }

    @Test
    void testComparisonLogic() throws Exception {

        WordleDictionary dictionary =
                new WordleDictionary(List.of("арбуз", "баруз"), 5);

        WordleGame game = new WordleGame(dictionary);

        String answer = game.getAnswer();

        String result = game.getComparisonResult(answer);

        assertEquals("+++++", result);
    }

    @Test
    void testStepIncrement() throws Exception {

        game.getComparisonResult("арбуз");

        assertEquals(1, game.getCurrentStep());
    }

    @Test
    void testHint() throws Exception {

        WordleDictionary dictionary =
                new WordleDictionary(List.of("арбуз", "банан", "вишня"), 5);

        WordleGame game = new WordleGame(dictionary);

        game.getComparisonResult("арбуз");

        String hint = game.giveHint();

        assertNotNull(hint);
    }

    @Test
    void testCreateGameWithNullDictionary() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new WordleGame(null)
        );
    }

    @Test
    void testCreateGameWithEmptyDictionary() {

        WordleDictionary dictionary =
                new WordleDictionary(List.of(), 5);

        assertThrows(
                IllegalArgumentException.class,
                () -> new WordleGame(dictionary)
        );
    }
}