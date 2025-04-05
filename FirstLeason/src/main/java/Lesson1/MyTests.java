package Lesson1;

import Lesson1.annatations.*;

public class MyTests {
    @BeforeSuite
    public static void setup() {
        System.out.println("BeforeSuite: Подготовка тестов");
    }

    @AfterSuite
    public static void teardown() {
        System.out.println("AfterSuite: Завершение тестов");
    }

    @BeforeTest
    public void beforeEachTest() {
        System.out.println("BeforeTest: Подготовка перед тестом");
    }

    @AfterTest
    public void afterEachTest() {
        System.out.println("AfterTest: Очистка после теста");
    }

    @Test(priority = 7)
    public void test1() {
        System.out.println("Running test1 with priority 7");
    }

    @Test(priority = 2)
    public void test2() {
        System.out.println("Running test2 with priority 2");
    }

    @Test
    @CsvSource("10, Java, 20, true")
    public void csvTest(int a, String b, int c, boolean d) {
        System.out.println("CsvTest: " + a + ", " + b + ", " + c + ", " + d);
    }

    public static void main(String[] args) {
        TestRunner.runTests(MyTests.class);
    }
}
