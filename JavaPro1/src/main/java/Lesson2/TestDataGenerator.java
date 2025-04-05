package Lesson2;

import java.util.Arrays;
import java.util.List;

public class TestDataGenerator {
    public static List<Integer> generateIntegers() {
        return Arrays.asList(5, 2, 10, 9, 4, 3, 10, 1, 13);
    }

    public static List<String> generateWords() {
        return Arrays.asList("яблоко", "банан", "киви", "клубника", "апельсин", "ананас");
    }

    public static List<Person> generatePeople() {
        return Arrays.asList(
                new Person("Иван", 35, "Инженер"),
                new Person("Петр", 42, "Инженер"),
                new Person("Анна", 29, "Менеджер"),
                new Person("Сергей", 50, "Инженер"),
                new Person("Мария", 45, "Инженер"),
                new Person("Ольга", 30, "Аналитик")
        );
    }

    public static List<String> generateStringLines() {
        return Arrays.asList(
                "яблоко груша апельсин арбуз виноград",
                "мандарин слива банан персик инжир",
                "гранат ананас лимон лайм киви"
        );
    }

    public static String generateString() {
        return "яблоко апельсин банан яблоко груша банан яблоко";
    }
}
