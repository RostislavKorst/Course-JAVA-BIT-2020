import java.util.*;

public class ScalableThreadPool implements ThreadPool {
    private final int minThreadsNum;
    private final int maxThreadsNum;
    private final Deque<Runnable> tasksQueue = new ArrayDeque<>();
    private final List<Thread> threads = new ArrayList<>();
    private volatile boolean isActivePoll = false;
    private volatile int numReadyThreads; // Потоки, выполняющие задачу + ожидающие новых задач
    private volatile int numThreadsEmployed = 0; // Потоки, выполняющие какую-то задачу в текущий момент

    public ScalableThreadPool(int minThreadsNum, int maxThreadsNum) {
        this.minThreadsNum = minThreadsNum;
        this.maxThreadsNum = maxThreadsNum;
        for (int i = 0; i < maxThreadsNum; i++) {
            threads.add(new Thread(new TaskHandler()));
        }
        numReadyThreads = minThreadsNum;
    }

    @Override
    public void start() {
        if (isActivePoll) return;
        threads.subList(0, minThreadsNum).forEach(Thread::start);
        isActivePoll = true;
    }

    public void stopAll() {
        isActivePoll = false;
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (tasksQueue) {
            tasksQueue.add(runnable);
            tasksQueue.notifyAll();
        }
    }

    private class TaskHandler implements Runnable {
        @Override
        public void run() {
            Runnable task;
            while (isActivePoll) {
                synchronized (tasksQueue) {
                    if (numThreadsEmployed == numReadyThreads) {
                        addNewThread();
                    }
                    if (numReadyThreads - numThreadsEmployed > 1 && numReadyThreads > minThreadsNum) {
                        numReadyThreads -= 1;
                        break;
                    }
                    while (tasksQueue.isEmpty()) {
                        doWait();
                    }
                    task = Objects.requireNonNull(tasksQueue.pollFirst());
                }
                incrementEmployed();
                task.run();
                decrementEmployed();
            }
        }
    }

    private synchronized void addNewThread() {
        if (numReadyThreads < maxThreadsNum){
            threads.get(numReadyThreads).start();
            numReadyThreads += 1;
        }
    }

    private synchronized void incrementEmployed() {
        numThreadsEmployed += 1;
    }

    private synchronized void decrementEmployed() {
        numThreadsEmployed -= 1;
    }

    private void doWait() {
        try {
            tasksQueue.wait();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}