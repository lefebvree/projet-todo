package test;

import model.task.LongtermTask;
import model.task.TaskStatus;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LongtermTaskTest {
    @Test
    void setProgress() {

        // Progress can only be set if superior than current progress and in percentage (0 < p < 100)

        LongtermTask LTprogress = new LongtermTask("", new Date(), null, new Date());

        LTprogress.setProgress(25);
        assertEquals(25, LTprogress.getProgression());

        LTprogress.setProgress(0);
        assertEquals(25, LTprogress.getProgression());

        LTprogress.setProgress(200);
        assertEquals(25, LTprogress.getProgression());

        LTprogress.setProgress(100);
        assertEquals(100, LTprogress.getProgression());
    }

    @Test
    void setDone() {
        // Task is done when progress reaches 100%

        LongtermTask LTprogress = new LongtermTask("", new Date(), null, new Date());
        assertFalse(LTprogress.isDone());

        LTprogress.setProgress(50);
        assertFalse(LTprogress.isDone());

        LTprogress.setDone();
        assertTrue(LTprogress.isDone());
    }

    @Test
    void getStatus() {

        // Late tasks are created by given an old start date (31 days ago)

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -31);
        Date monthago = calendar.getTime();

        LongtermTask LTstatus = new LongtermTask("", new Date(), null, monthago);
        assertEquals(TaskStatus.LATE, LTstatus.getStatus());

        LTstatus.setProgress(99);
        assertEquals(TaskStatus.INPROGRESS, LTstatus.getStatus());

        LTstatus.setDone();
        assertEquals(TaskStatus.DONE, LTstatus.getStatus());
    }

    @Test
    void getTimeLeft() {
        // Test days left and next progressw

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -10);
        Date tendaysbefore = calendar.getTime();
        calendar.add(Calendar.DATE, 20);
        Date tendaysafter = calendar.getTime();

        LongtermTask LTtimeleft = new LongtermTask("", tendaysafter, null, tendaysbefore);
        assertEquals("4 days late (25%)", LTtimeleft.getTimeLeft());

        LTtimeleft.setProgress(50);
        assertEquals("5 days (75%)", LTtimeleft.getTimeLeft());

        LTtimeleft.setProgress(80);
        assertEquals("10 days (100%)", LTtimeleft.getTimeLeft());
    }

    @Test
    void getNextExpectedDate() {
        // Test date to reach next progress (can be past)

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE, -5);
        Date date1 = calendar.getTime();

        calendar.add(Calendar.DATE, 2);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR, 23);
        Date date2 = calendar.getTime();

        calendar.add(Calendar.DATE, 7);
        Date date4 = calendar.getTime();

        LongtermTask LTtimeleft = new LongtermTask("", date4, null, date1);
        assertEquals(date2, LTtimeleft.getNextExpectedDate());

        LTtimeleft.setProgress(80);
        assertEquals(date4, LTtimeleft.getNextExpectedDate());
    }

    @Test
    void isLate() {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE, -20);
        Date date1 = calendar.getTime();

        calendar.add(Calendar.DATE, 30);
        Date date2 = calendar.getTime();

        LongtermTask LTislate = new LongtermTask("", date2, null, date1);

        assertTrue(LTislate.isLate());

        LTislate.setProgress(30);
        assertTrue(LTislate.isLate());

        LTislate.setProgress(60);
        assertFalse(LTislate.isLate());
    }
}