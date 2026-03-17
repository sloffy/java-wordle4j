package ru.yandex.practicum;

import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {

    public static void main(String[] args) {

        String filename = "words_ru.txt";

        WordleDictionaryLoader loader = new WordleDictionaryLoader();
        WordleDictionary dictionary = loader.loadWordleDictionary(filename);

        WordleGame game = new WordleGame(dictionary);

        startGame(game);
    }

    private static void startGame(WordleGame game) {
        Scanner scanner = new Scanner(System.in);

        String userInput;
        String comparisonResult;
        boolean guessed;

        printGreetings(game);


        while(game.getCurrentStep() < game.getSteps()) {
            printPreInfoCurrentStep(game);

            userInput = scanner.nextLine().toLowerCase();

            comparisonResult = game.getComparisonResult(userInput);

            guessed = game.isAnswerCorrect(comparisonResult);

            System.out.println(comparisonResult);
            if (guessed) {
                System.out.println("Поздравляю! Вы отгадали слово \"" + game.getAnswer() + "\" за " +
                        (game.getCurrentStep() + 1) + " попыток!");
                return;
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
