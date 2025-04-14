package Lesson3;

public class TestClass {
    public static void main(String[] args) {
        SimpleThreadPool pool = new SimpleThreadPool(3);

        for (int i = 0; i < 10; i++) {
            int taskId = i;
            pool.execute(() -> {
                System.out.println("Task " + taskId + " is running in " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
            });
        }

        pool.shutdown();
        pool.awaitTermination();

        System.out.println("All tasks completed.");
    }
}
