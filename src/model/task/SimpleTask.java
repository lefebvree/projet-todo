package model.task;

import model.Category;
import view.task.TaskPanel;

import java.util.Date;

public class SimpleTask extends Task {

    // Simple are either done or not
    private boolean isDone;

    // Only isDone boolean change
    public SimpleTask(String n, Date d, Category c) {
        super(n, d, c);

        this. isDone = false;
    }

    public void setProgress(int i) {
        if (i > 0) this.setDone();
    }

    public void setDone() {
        this. isDone = true;
    }

    // Is task set to be done before current day ?
    public boolean isLate() {
        Date today = new Date();

        return today.after(this.getDate());
    }

    // Depending of due date and isDone
    public TaskStatus getStatus() {
        if (this. isDone) return TaskStatus.DONE;
        else return (this.isLate()) ? TaskStatus.LATE : TaskStatus.INPROGRESS;
    }

    public Date getNextExpectedDate() {
        return this.getDate();
    }

    public String getTimeLeft() {
        int daysLeft = Task.daysBetween(new Date(), this.getDate());
        int absdays = Math.abs(daysLeft);
        // Format: day"s" if more than one and "late" if negative days
        return absdays + ((absdays > 1) ? " days" : " day") + ((daysLeft < 0) ? " late" : "");
    }

    // Panel is constructed in TaskPanel class
    public TaskPanel getPanel() {
        return new TaskPanel(this);
    }
}
