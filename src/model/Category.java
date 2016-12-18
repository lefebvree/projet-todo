package model;

import model.task.Task;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {

    private String name;
    private ArrayList<Task> associated_tasks;

    // A Category has a name and a list of tasks
    public Category (String n) {
        this.name = n;
        this.associated_tasks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Remove category from all Task associated with it
    public void removeAllTask() {
        for (Task task : this.associated_tasks) task.setCategory(null);

        this.associated_tasks = new ArrayList<>();
    }

    // Get all tasks with this category
    public ArrayList<Task> getAssociated_tasks() {
        return associated_tasks;
    }
}
