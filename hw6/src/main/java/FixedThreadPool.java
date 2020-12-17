import java.util.*;

public class FixedThreadPool implements ThreadPool {
    private final Deque<Runnable> tasksQueue = new ArrayDeque<>();
    private final Collection<Thread> threads = new ArrayList<>();
    private volatile boolean isActivePoll = false;

    public FixedThreadPool(int numThreads) {
        for (int i = 0; i < numThreads; i++) {
            threads.add(new Thread(new TaskHandler()));
        }
    }

    @Override
    public void start() {
        if (isActivePoll) return;
        threads.forEach(Thread::start);
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
                    while (tasksQueue.isEmpty()) {
                        doWait();
                    }
                    task = Objects.requireNonNull(tasksQueue.pollFirst());
                }
                task.run();
            }
        }
    }

    private void doWait() {
        try {
            tasksQueue.wait();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public Deque<Runnable> getTasksQueue() {
        return tasksQueue;
    }
}