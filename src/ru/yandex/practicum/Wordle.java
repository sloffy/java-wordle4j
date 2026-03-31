package ru.yandex.practicum;

import ru.yandex.practicum.customExceptions.GameException;

import java.io.FileWriter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {

        String filename = "words_ru.txt";
        String logfilename = "log.txt";

        try (PrintWriter log = new PrintWriter(new FileWriter(logfilename, true), true)) {

            WordleDictionaryLoader loader = new WordleDictionaryLoader();
            WordleDictionary dictionary = loader.loadWordleDictionary(filename);

            WordleGame game = new WordleGame(dictionary);
            log.println("Игра создана. Загаданное слово: " + game.getAnswer());

            startGame(game, log);

        } catch (Exception exception) {
            try (PrintWriter log = new PrintWriter(new FileWriter(logfilename, true), true)) {
                log.println("Ошибка в игре: " + exception);
            } catch (IOException e) {
                System.out.println("Не удалось записать в лог-файл: " + e.getMessage());
            }
        }
    }

    private static void startGame(WordleGame game, PrintWriter log) throws GameException {
        Scanner scanner = new Scanner(System.in);

        String userInput;
        String comparisonResult;
        boolean guessed;

        printGreetings(game);


        while (game.getCurrentStep() < game.getSteps()) {
            printPreInfoCurrentStep(game);

            userInput = scanner.nextLine().toLowerCase().replace("ё", "е");

            log.println("Ход " + (game.getCurrentStep() + 1) + ". Пользователь вводит: " + userInput);

            if (userInput.isEmpty()) {
                String hint = game.giveHint();
                System.out.println("Подсказка: " + hint);
                log.println("Выдана подсказка: " + hint);
                continue;
            }

            try {
                comparisonResult = game.getComparisonResult(userInput);

                log.println("Результат проверки: " + comparisonResult);

                guessed = game.isAnswerCorrect(comparisonResult);

                System.out.println(comparisonResult);
                if (guessed) {
                    System.out.println("Поздравляю! Вы отгадали слово \"" + game.getAnswer() + "\" за " +
                            game.getCurrentStep() + " попыток!");

                    log.println("Победа! Слово " + game.getAnswer() + " угадано за " + game.getCurrentStep() +
                            " попыток");
                    return;
                }
            } catch (GameException exception) {
                System.out.println(exception.getMessage());
            }
        }

        System.out.println("Вам не удалось отгадать слово " + game.getAnswer() + " :'(");
        log.println("Проигрыш. Слово не угадано: " + game.getAnswer());
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
