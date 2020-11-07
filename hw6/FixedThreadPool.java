import java.util.*;

public class FixedThreadPool implements ThreadPool {
    private final int numThreads;
    private final Deque<Runnable> tasksQueue = new ArrayDeque<>();
    private final Collection<Thread> threads = new ArrayList<>();
    private boolean isActivePoll = false;

    public FixedThreadPool(int numThreads) {
        this.numThreads = numThreads;
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
            tasksQueue.notify();
        }
    }

    private class TaskHandler implements Runnable {
        @Override
        public void run() {
            Runnable task;
            while (true) {
                synchronized (tasksQueue) {
                    while (tasksQueue.isEmpty()) {
                        doWait();
                    }
                    if (isActivePoll) {
                        task = Objects.requireNonNull(tasksQueue.pollFirst());
                    } else {
                        break;
                    }
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
}