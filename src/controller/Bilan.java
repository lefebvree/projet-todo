package controller;

import model.task.Task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class Bilan {

    private Todo todo;

    private Date datefrom, dateto;

    private int totaltasks, inprogress, late, done;
    private int inprogress_percent, late_percent, done_percent;

    ArrayList<Task> tasks_bilan;

    // Create new empty bilan
    public Bilan (Date from, Date to, Todo t) {

        this.todo = t;

        // From date doesn't need to be set as end of day since it's obviously before 23:59:59
        this.datefrom = from;
        this.dateto   = Task.getEndOfDay(to);

        this.totaltasks = 0;
        this.inprogress = 0;
        this.late       = 0;
        this.done       = 0;

        // List f tasks fitting into bilan (due date is in time interval)
        this.tasks_bilan = new ArrayList<>();
    }


    // Generate Bilan from datefrom to dateto
    // Returns bilan validity
    public boolean generateBilan() {

        // Make sure from date is before to date
        if (this.datefrom.after(this.dateto)) return false;

        // For every tasks list all tasks must be checked
        for (Task task : this.todo.getTask_inprogress()) {
            if (task.getDate().after(this.datefrom) && task.getDate().before(this.dateto)) {
                tasks_bilan.add(task);
                inprogress++;
                totaltasks++;
            }
        }

        for (Task task : this.todo.getTask_late()) {
            if (task.getDate().after(this.datefrom) && task.getDate().before(this.dateto)) {
                tasks_bilan.add(task);
                late++;
                totaltasks++;
            }
        }

        for (Task task : this.todo.getTask_done()) {
            if (task.getDate().after(this.datefrom) && task.getDate().before(this.dateto)) {
                tasks_bilan.add(task);
                done++;
                totaltasks++;
            }
        }

        // Risk of division by 0
        if (tasks_bilan.size() > 0) {
            // Tasks are then sorted by due date
            tasks_bilan.sort(Comparator.comparing(Task::getDate));

            this.inprogress_percent = (int) (inprogress / (float)totaltasks * 100);
            this.late_percent       = (int) (late       / (float)totaltasks * 100);
            this.done_percent       = (int) (done       / (float)totaltasks * 100);
        }

        return true;
    }

    public ArrayList<Task> getTasks_bilan() {
        return tasks_bilan;
    }

    public int getTotaltasks() {
        return totaltasks;
    }

    public int getDone_percent() {
        return done_percent;
    }

    public int getInprogress_percent() {
        return inprogress_percent;
    }

    public int getLate_percent() {
        return late_percent;
    }
}
