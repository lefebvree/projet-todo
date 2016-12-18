package model.task;

import model.Category;
import view.task.TaskPanel;

import java.util.Calendar;
import java.util.Date;

public class LongtermTask extends Task {

    // Progression is a percentage, starts a 0 up to 100
    private int progression;
    // Long-term Tasks needs a start date (default is current day)
    private Date startdate;


    public LongtermTask(String n, Date d, Category c, Date s) {
        super(n, d, c);

        // Current day by default for start date
        // The start date must be before task creation
        if (s == null || !s.before(new Date())) s = new Date();

        this.startdate   = Task.getEndOfDay(s);
        this.progression = 0;
    }

    // Only set new progress if task is editable and progress valid
    public void setProgress(int i) {
        if (this.isEditable() && isNewProgressValid(i)) {
            this.progression = i;
        }
    }

    // Can i be set as new progress
    private boolean isNewProgressValid(int i) {
        // Progress is in %, must be between 0 and 100
        // Progress can only increment
        return (i > 0 && i <= 100 && i > this.progression);
    }

    public void setDone() {
        this.progression = 100;
    }

    public int getProgression() {
        return progression;
    }

    // Task is declared done if progression is 100(%)
    public TaskStatus getStatus() {
        if (this.progression == 100) return TaskStatus.DONE;
        else return (this.isLate()) ? TaskStatus.LATE : TaskStatus.INPROGRESS;
    }

    // String with next Date and progression
    public String getTimeLeft() {

        Date next = this.getNextExpectedDate();
        int daysleft = daysBetween(new Date(), next);
        int absdays = Math.abs(daysleft);

        // Format: "20 day's' (50%) 'late'"
        return absdays + ((absdays > 1) ? " days" : " day") + ((daysleft < 0) ? " late (" : " (") + getNextStep(this.progression) + "%)";
    }

    // Date at which progression expected (25, 50, 75 or 100%) must be done
    public Date getNextExpectedDate() {
        int totalDays = daysBetween(this.startdate, this.getDate());
        int expectedProgression = this.getExpectedProgression();

        // If current progression is larger than expected one, check date for next step
        if (this.progression >= expectedProgression) {
            expectedProgression = getNextStep(this.progression);
        }

        // Days between start date and next expected progression date
        int daysFromStartDate = (int)(totalDays * ((float)expectedProgression / 100));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.startdate);
        calendar.add(Calendar.DATE, daysFromStartDate);

        // start_date + daysFromStartDate
        return calendar.getTime();
    }

    // Return next step for progress i
    private int getNextStep(int i) {
        if      (i >= 75) return 100;
        else if (i >= 50) return 75;
        else if (i >= 25) return 50;
        else              return 25;
    }

    // Is current progression bellow expected progression or more than 25% behind
    public boolean isLate() {
        return (this.progression < (this.getExpectedProgression() - 25)
                || this.getNextExpectedDate().before(new Date()));
    }

    // Return the expected progression (either 25, 50, 75 or 100) depending on start date and due date
    private int getExpectedProgression() {

        int totalDays   = daysBetween(this.startdate, this.getDate());
        int elapsedDays = daysBetween(this.startdate, new Date());

        int p = (int)(((float)elapsedDays / (float)totalDays));

        return getNextStep(p);
    }

    public TaskPanel getPanel() {
        return new TaskPanel(this);
    }
}
