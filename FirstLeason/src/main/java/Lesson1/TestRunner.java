package Lesson1;

import Lesson1.annatations.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestRunner {
    public static void runTests(Class<?> testClass) {
        /**
         * В коде beforeSuiteMethod и afterSuiteMethod переменные используются для хранения метода
         * с аннотацией @BeforeSuite и @AfterSuite соответственно.
         *
         * <p>В начале их значением является null, потому что метод с такой аннотацией может отсутствовать в классе.
         * При обработке аннотированных методов, если мы находим метод с @BeforeSuite, то сохраняем его
         * в beforeSuiteMethod. Аналогично с afterSuiteMethod.
         * При этом выполняется проверка: если таких методов больше одного, выбрасывается исключение.
         * То есть null здесь означает, что метод еще не найден или его вообще нет.
         *
         * <p>Остальные методы, такие как те, что отмечены @Test, @BeforeTest и @AfterTest, могут встречаться несколько
         * раз в одном классе.
         * Например, может быть несколько методов с @Test, и они должны выполняться в определенном порядке (по priority).
         * Аналогично может быть несколько @BeforeTest и @AfterTest, так как они выполняются перед и после каждого теста
         * соответственно.
         * Потому List<Method> удобнее: он позволяет хранить множество методов и потом обрабатывать их в нужном порядке.
         */

        //Определяем переменные для хранения методов
        Method beforeSuiteMethod = null;
        Method afterSuiteMethod = null;
        List<Method> beforeTestMethods = new ArrayList<>();
        List<Method> afterTestMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();

        // Сканируем методы
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                if (beforeSuiteMethod != null) {
                    throw new RuntimeException("Only one @BeforeSuite method allowed");
                }
                beforeSuiteMethod = method;
            } else if (method.isAnnotationPresent(AfterSuite.class)) {
                if (afterSuiteMethod != null) {
                    throw new RuntimeException("Only one @AfterSuite method allowed");
                }
                afterSuiteMethod = method;
            } else if (method.isAnnotationPresent(BeforeTest.class)) {
                beforeTestMethods.add(method);
            } else if (method.isAnnotationPresent(AfterTest.class)) {
                afterTestMethods.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }
        }

        // Сортируем тестовые методы по приоритету
        testMethods.sort(Comparator.comparingInt(m -> -m.getAnnotation(Test.class).priority()));

        try {
            Object testInstance = testClass.getDeclaredConstructor().newInstance();

            // Выполняем BeforeSuite
            if (beforeSuiteMethod != null) beforeSuiteMethod.invoke(null);

            // Запускаем тесты
            for (Method testMethod : testMethods) {
                // BeforeTest
                for (Method beforeTest : beforeTestMethods) beforeTest.invoke(testInstance);

                if (testMethod.isAnnotationPresent(CsvSource.class)) {
                    String csv = testMethod.getAnnotation(CsvSource.class).value();
                    runCsvTest(testInstance, testMethod, csv);
                } else {
                    testMethod.invoke(testInstance);
                }

                // AfterTest
                for (Method afterTest : afterTestMethods) afterTest.invoke(testInstance);
            }

            // Выполняем AfterSuite
            if (afterSuiteMethod != null) afterSuiteMethod.invoke(null);

        } catch (Exception e) {
            throw new RuntimeException("Error running tests", e);
        }
    }

    private static void runCsvTest(Object instance, Method method, String csv) throws Exception {
        //разбивает строку @CsvSource("10, Java, 20, true") на ["10", "Java", "20", "true"]
        String[] values = csv.split(",\\s*");
        Parameter[] parameters = method.getParameters();
        Object[] parsedValues = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            parsedValues[i] = convert(values[i], parameters[i].getType());
        }

        method.invoke(instance, parsedValues);
    }

    private static Object convert(String value, Class<?> type) {
        return switch (type.getSimpleName()) {
            case "int" -> Integer.parseInt(value);
            case "boolean" -> Boolean.parseBoolean(value);
            case "double" -> Double.parseDouble(value);
            case "String" -> value;
            default -> throw new IllegalArgumentException("Unsupported parameter type: " + type);
        };
    }
}
