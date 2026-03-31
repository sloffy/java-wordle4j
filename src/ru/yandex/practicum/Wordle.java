package ru.yandex.practicum;

import ru.yandex.practicum.customExceptions.GameException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {

        String filename = "words_ru.txt";
        String logfilename = "log.txt";

        WordleDictionaryLoader loader = new WordleDictionaryLoader();
        try {
            WordleDictionary dictionary = loader.loadWordleDictionary(filename);
            WordleGame game = new WordleGame(dictionary);
            startGame(game);
        }  catch (Exception exception) {
            try (Writer logWriter = new FileWriter(logfilename, true)) {
                logWriter.write(exception.toString());
            } catch (IOException e) {
                System.out.println("Ошибка записи логов в файл: " + e.getMessage());
            }
        }
    }

    private static void startGame(WordleGame game) throws GameException {
        Scanner scanner = new Scanner(System.in);

        String userInput;
        String comparisonResult;
        boolean guessed;

        printGreetings(game);


        while(game.getCurrentStep() < game.getSteps()) {
            printPreInfoCurrentStep(game);

            userInput = scanner.nextLine().toLowerCase().replace("ё", "е");

            if (userInput.isEmpty()) {
                System.out.println("Подсказка: " + game.giveHint());
                continue;
            }

            try {
                comparisonResult = game.getComparisonResult(userInput);

                guessed = game.isAnswerCorrect(comparisonResult);

                System.out.println(comparisonResult);
                if (guessed) {
                    System.out.println("Поздравляю! Вы отгадали слово \"" + game.getAnswer() + "\" за " +
                            game.getCurrentStep() + " попыток!");
                    return;
                }
            } catch (GameException exception) {
                System.out.println(exception.getMessage());
            }
        }

        System.out.println("Вам не удалось отгадать слово " + game.getAnswer() + " :'(");
    }

    private static void printGreetings(WordleGame game) {
        System.out.println("WORDLE");
        System.out.println("Игра началась! Загаданое слово состоит из " + game.getWordLength() + " букв!");
        System.out.println("У вас есть " + game.getSteps() + " попыток!");
    }

    private static void printPreInfoCurrentStep(WordleGame game) {
        System.out.println("Игра продолжается...");
        System.out.println();
        System.out.println("Текущая попытка " + (game.getCurrentStep() + 1) + "!");
        System.out.println("Введите слово:");
    }
}
