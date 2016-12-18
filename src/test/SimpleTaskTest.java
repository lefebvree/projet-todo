package test;

import model.task.SimpleTask;
import model.task.Task;
import model.task.TaskStatus;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SimpleTaskTest {
    @Test
    void isLate() {
        // A simple task cannot be late at creation, since minimum due date is one day later

        Date today = new Date();

        Task STontime = new SimpleTask("", today, null);
        assertFalse(STontime.isLate());
    }

    @Test
    void getStatus() {

        Task Tinprogress = new SimpleTask("", new Date(), null);
        assertEquals(TaskStatus.INPROGRESS, Tinprogress.getStatus());

        Task Tdone = new SimpleTask("", new Date(), null);
        Tdone.setDone();
        assertEquals(TaskStatus.DONE, Tdone.getStatus());

    }

    @Test
    void getNextExpectedDate() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 5);

        Date fivedays = calendar.getTime();

        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR, 23);

        Task Tfivedays = new SimpleTask("", fivedays, null);

        assertEquals(calendar.getTime(), Tfivedays.getNextExpectedDate());

    }

    @Test
    void getTimeLeft() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);

        Date oneweek = calendar.getTime();

        Task Toneweek = new SimpleTask("", oneweek, null);

        assertEquals("7 days", Toneweek.getTimeLeft());

    }
}