package Lesson3;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleThreadPool {
    private final int poolSize;
    private final Thread[] workers;
    private final LinkedList<Runnable> taskQueue = new LinkedList<>();
    private final Object lock = new Object();
    private final AtomicBoolean isShutdown = new AtomicBoolean(false);

    public SimpleThreadPool(int poolSize) {
        this.poolSize = poolSize;
        this.workers = new Thread[poolSize];

        for (int i = 0; i < poolSize; i++) {
            workers[i] = new Thread(new Worker());
            workers[i].start();
        }
    }

    public void execute(Runnable task) {
        if (isShutdown.get()) {
            throw new IllegalStateException("Thread pool is shutdown. Cannot accept new tasks.");
        }
        synchronized (lock) {
            taskQueue.addLast(task);
            lock.notify(); // разбудить один поток
        }
    }

    public void shutdown() {
        isShutdown.set(true);
        synchronized (lock) {
            lock.notifyAll(); // разбудить все потоки, чтобы они могли завершиться
        }
    }

    public void awaitTermination() {
        for (Thread worker : workers) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Termination interrupted");
            }
        }
    }

    public int getPoolSize() {
        return poolSize;
    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            while (true) {
                Runnable task;
                synchronized (lock) {
                    while (taskQueue.isEmpty()) {
                        if (isShutdown.get()) return;
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    task = taskQueue.removeFirst();
                }

                try {
                    task.run();
                } catch (RuntimeException e) {
                    System.err.println("Task execution failed: " + e.getMessage());
                }
            }
        }
    }
}
