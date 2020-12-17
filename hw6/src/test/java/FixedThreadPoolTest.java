import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FixedThreadPoolTest {
    @Test
    public void execute_TwoThreadsThreeTasks_OneTaskInQueue() {
        //given
        FixedThreadPool executor = new FixedThreadPool(2);
        executor.start();
        //when
        executor.execute(this::someLogic);
        executor.execute(this::someLogic);
        executor.execute(this::someLogic);
        //then
        assertEquals(1, executor.getTasksQueue().size());
        executor.stopAll();
    }

    private void someLogic() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}