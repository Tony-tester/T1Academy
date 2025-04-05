package Lesson2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestClass {
    public static void main(String[] args) {
        // ===== 1, 2. Список чисел =====
        List<Integer> list = TestDataGenerator.generateIntegers();

        int thirdMax = list.stream()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .map(i -> {
                    System.out.println("3-е наибольшее число: " + i);
                    return i;
                })
                .orElseThrow();
        int thirdUniqueMax = list.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .map(i -> {
                    System.out.println("3-е наибольшее уникальное число: " + i);
                    return i;
                })
                .orElseThrow();

        // ===== 3, 4. Список сотрудников =====
        List<Person> people = TestDataGenerator.generatePeople();

        List<String> top3Engineers = people.stream()
                .filter(e -> e.getPosition().equals("Инженер"))
                .sorted(Comparator.comparingInt(Person::getAge).reversed())
                .limit(3)
                .map(Person::getName)
                .toList();

        double avgAge = people.stream()
                .filter(e -> e.getPosition().equals("Инженер"))
                .mapToInt(Person::getAge)
                .average()
                .orElse(0);

        System.out.println("3 самых старших инженера: " + top3Engineers);
        System.out.println("Средний возраст инженеров: " + avgAge);

        // ===== 5. Самое длинное слово =====
        List<String> words = TestDataGenerator.generateWords();

        String longest = words.stream()
                .max(Comparator.comparingInt(String::length))
                .map(i -> {
                    System.out.println("Самое длинное слово: " + i);
                    return i;
                })
                .orElse("");

        // ===== 6. Подсчёт слов =====
        String text = TestDataGenerator.generateString();

        HashMap<String, Long> wordCount = Arrays.stream(text.split(" "))
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        HashMap::new,
                        Collectors.counting()
                ));

        System.out.println("Подсчёт слов: " + wordCount);

        // ===== 7. Сортировка строк по длине, потом по алфавиту =====

        List<String> sorted = words.stream()
                .sorted(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()))
                .toList();

        System.out.println("Отсортированные слова: " + sorted);

        // ===== 8. Самое длинное слово из массива строк =====
        List<String> lines = TestDataGenerator.generateStringLines();

        String longestWord = lines.stream()
                .flatMap(line -> Arrays.stream(line.split(" ")))
                .max(Comparator.comparingInt(String::length))
                .map(i -> {
                    System.out.println("Самое длинное слово среди всех строк: " + i);
                    return i;
                })
                .orElse("");
    }
}
