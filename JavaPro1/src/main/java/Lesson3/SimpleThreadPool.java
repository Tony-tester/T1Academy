package Lesson3;

import java.util.LinkedList;
import java.util.List;

public class SimpleThreadPool {
    private final int poolSize;
    private final List<Worker> workers = new LinkedList<>();
    private final LinkedList<Runnable> taskQueue = new LinkedList<>();
    private boolean isShutdown = false;

    public SimpleThreadPool(int poolSize) {
        this.poolSize = poolSize;
        for (int i = 0; i < poolSize; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            worker.start();
        }
    }

    public synchronized void execute(Runnable task) {
        if (isShutdown) {
            throw new IllegalStateException("Thread pool is shutdown. Cannot accept new tasks.");
        }
        taskQueue.addLast(task);
        notify(); // разбудить один ожидающий поток
    }

    public synchronized void shutdown() {
        isShutdown = true;
        notifyAll(); // разбудить все потоки
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

    private class Worker extends Thread {
        @Override
        public void run() {
            Runnable task;
            while (true) {
                synchronized (SimpleThreadPool.this) {
                    while (taskQueue.isEmpty()) {
                        if (isShutdown) return;
                        try {
                            SimpleThreadPool.this.wait();
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
