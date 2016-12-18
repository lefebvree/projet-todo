package view.task;

import controller.Todo;
import model.task.Task;
import model.task.TaskStatus;
import view.Window;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;

public class TaskList {

    private TaskStatus tasktype;
    private Todo todo;
    private Window window;

    private ArrayList<TaskPanel> tasks_panel;
    private JPanel panel;

    // A panel displaying all task with a certain status (DONE, INPROGRESS, LATE)
    public TaskList (Todo t, TaskStatus s, Window w) {
        this.todo     = t;
        this.tasktype = s;
        this.window   = w;

        this.panel = new JPanel();
        this.panel.setBorder(new EmptyBorder(0, 0, 15, 0));
        this.panel.setLayout(new GridLayout(0, 1));

        this.tasks_panel = new ArrayList<>();

        this.populate();
    }

    // Add all task with given status
    private void populate() {

        // Fetch tasks from todo
        ArrayList<Task> list = this.getTasks();

        // If no tasks do not display List name
        if (list.size() > 0) {
            this.panel.add(new JLabel("TASKS " + this.tasktype, SwingConstants.LEFT));

            // Add each task panel
            for (Task t : list) {
                TaskPanel p = t.getPanel();
                this.addPanel(p);
            }
        }

        this.panel.validate();
        this.panel.repaint();
    }

    private void addPanel(TaskPanel p) {

        int index = this.tasks_panel.size();
        this.tasks_panel.add(p);

        this.panel.add(p.getPanel());

        if (p.isEditable()) {
            p.getBtdelete().addMouseListener(new MouseAdapter() {
                public void mouseClicked (MouseEvent e) {
                    remove(index);
                }
            });

            // Depending on task type, task is marked as done or a new progression is set
            p.getBtdone().addMouseListener(new MouseAdapter() {
                public void mouseClicked (MouseEvent e) {
                    if (p.isLongtermTask()) {
                        // New progress will be fetch from task panel
                        setProgression(index, p.getnewProgress());
                    } else {
                        markDone(index);
                    }
                }
            });

            p.getBtupdateDate().addMouseListener(new MouseAdapter() {
                 public void mouseClicked (MouseEvent e) {
                     Date newdate = p.getNewDate();

                     // set due date only change date if after current due date
                     if (getTasks().get(index).setDueDate(newdate)) {
                         todo.checkLateTasks();
                         updateAll();
                     }
                 }
             });
        }
    }

    // Redrawn all tasks
    public void updateAll() {

        this.removeAll();
        this.populate();

        this.panel.validate();
        this.panel.repaint();

        this.todo.saveTodo();
    }

    // Clear tasks list
    private void removeAll() {
        this.panel.removeAll();
        this.tasks_panel = new ArrayList<>();
    }

    // Remove a certain task panel at index i
    private void remove(int index) {

        // Task to delete
        Task task = this.getTasks().get(index);

        // Remove task from category tasks list
        if (task.getCategory() != null)
            task.getCategory().getAssociated_tasks().remove(task);

        // Remove task from task list
        this.getTasks().remove(index);

        // Update task list view
        this.updateAll();

    }

    // Mark task at index i as DONE and move it to corresponding list
    private void markDone(int index) {

        Task task = this.getTasks().get(index);
        this.getTasks().remove(index);

        task.setDone();

        this.todo.addTaskDone(task);

        this.window.updateAll();
    }

    // For longterm Tasks, set new progression
    private void setProgression(int index, int progression) {

        if (progression >= 100) markDone(index);
        // If wrong input, progression will be -1
        else if (progression > 0) {
            this.getTasks().get(index).setProgress(progression);

            this.todo.checkLateTasks();
            this.window.updateAll();
        }

    }

    private ArrayList<Task> getTasks() {

        // Get the task list associated with task status
        switch (this.tasktype) {
            case DONE:
                return this.todo.getTask_done();
            case INPROGRESS:
                return this.todo.getTask_inprogress();
            case LATE:
                return this.todo.getTask_late();
        }
        return null;
    }

    public JPanel getPanel() {
        return panel;
    }
}
