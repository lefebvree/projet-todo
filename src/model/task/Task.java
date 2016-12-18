package model.task;

import model.Category;
import view.task.TaskPanel;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// Superclass of LongtermTask and SimpleTask
public abstract class Task implements Serializable {

    // Name associated with task
    private String name;
    // Due date defined at task creation
    private Date due_date;
    // Category associated with class or null if none
    private Category category;

    // Format used to input due date / start date and to display dates
    // "31/12/1999"
    private final static SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

    // Assign common attributes
    Task(String n, Date d, Category c) {
        this.name     = n;
        this.category = c;

        // Due date must be after current date
        if (d != null && d.after(new Date())) this.due_date = getEndOfDay(d);
        else this.due_date = getEndOfDay(null);

        if(this.category != null) {
            this.category.getAssociated_tasks().add(this);
        }
    }

    Task(String n, Date d) {
        this(n, d, null);
    }

    public String getName() {
        return name;
    }

    public String getDueDate() {
        return dateformat.format(due_date);
    }

    public Category getCategory() {
        return category;
    }

    public void setName(String name) {
        // Task can only be renamed only if not done
        if (!this.isEditable()) return;

        this.name = name;
    }

    // Due date must be after current day, return if change happened
    public boolean setDueDate(Date d) {

        // Task can only be postponed if not done
        if (!this.isEditable() || d == null) return false;

        // Date is set at 23:59:59 at the same day
        // (or tomorrow if date is null)
        d = getEndOfDay(d);

        // Due date must be after today
        if (d.after(new Date())) {
            this.due_date = d;
            return true;
        } else return false;
    }

    // Progress between 0 and 100 for LongtermTask
    // Simple task with be completed if this method is called with i > 0 (progress is a boolean)
    public abstract void setProgress(int i);
    public abstract void setDone();

    public void setCategory(Category category) {
        // Category can only be changed only if task is not done
        if (!this.isEditable()) return;

        this.category = category;
    }

    public abstract TaskStatus getStatus();

    public abstract String getTimeLeft();

    public Date getDate() {
        return this.due_date;
    }

    public abstract Date getNextExpectedDate();

    public static SimpleDateFormat getDateformat() {
        return dateformat;
    }

    // A task is late if its due date is passed
    public abstract boolean isLate();

    public boolean isDone() {
        return this.getStatus() == TaskStatus.DONE;
    }

    public boolean isEditable() {
        return this.getStatus() != TaskStatus.DONE;
    }

    // JPanel constructed from class, differ depending of task type
    public abstract TaskPanel getPanel();

    // Return number of days between two dates (http://stackoverflow.com/a/7103141)
    public static int daysBetween(Date d1, Date d2){
        return ((int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24)));
    }

    // Return new date at 23:59:59 from provided date
    public static Date getEndOfDay(Date d) {

        Calendar calendar = Calendar.getInstance();

        // If date is null, the next day is chosen
        if (d == null) {
            calendar.setTime(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        } else {
            calendar.setTime(d);
        }

        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR, 23);

        return calendar.getTime();
    }
}
