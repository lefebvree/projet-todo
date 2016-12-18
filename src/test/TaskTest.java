package test;

import model.task.SimpleTask;
import model.task.Task;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    void isEditable() {

        // Task is only editable if not done

        Task T = new SimpleTask("", new Date(), null);

        assertTrue(T.isEditable());

        T.setDone();

        assertFalse(T.isEditable());
    }

    @Test
    void daysBetween() {

        // Test number of days between two dates, including negative result if date1 > date2

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -5);

        assertEquals(-5, Task.daysBetween(new Date(), calendar.getTime()));


        calendar.add(Calendar.DATE, 15);
        assertEquals(10, Task.daysBetween(new Date(), calendar.getTime()));
    }

    @Test
    void getEndOfDay() {

        // Get the date at 23:59:59

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR, 23);

        assertEquals(calendar.getTime(), Task.getEndOfDay(new Date()));
    }

}