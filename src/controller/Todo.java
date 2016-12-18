package controller;

import model.*;
import model.task.*;
import controller.exceptions.*;
import view.Window;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

// Main class: store tasks by status, store categories and manage todos
public class Todo implements Serializable {

    // Default file name
    private final static String UNNAMED_TODO_NAME = "unnamed";
    // Date format to use (display and input)

    private String name;

    // Tasks listed by status
    private ArrayList<Task> task_inprogress;
    private ArrayList<Task> task_late;
    private ArrayList<Task> task_done;

    // Categories saved with todo
    private ArrayList<Category> categories;


    // New todo with empty tasks lists and two default categories
    public Todo (String n) {

        this.name = n;

        // Each list contains tasks with certain status
        // Lists are updated with checkLateTasks
        this.task_inprogress = new ArrayList<>();
        this.task_done       = new ArrayList<>();
        this.task_late       = new ArrayList<>();

        this.categories = new ArrayList<>();
        // Default categories
        this.categories.add(new Category("Work"));
        this.categories.add(new Category("Personal"));

    }

    public ArrayList<Task> getTask_done() {
        return task_done;
    }

    public ArrayList<Task> getTask_inprogress() {
        return task_inprogress;
    }

    public ArrayList<Task> getTask_late() {
        return task_late;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    // Return first category matching name or null if not found
    public Category getCategoryByName(String name) {
        for (Category category : this.categories) {
            if (category.getName().equals(name)) return category;
        }
        return null;
    }

    // Create a new category
    public void newCategory(String name) {
        this.categories.add(new Category(name));
        this.saveTodo();
    }

    // Rename category at index i to name n
    public void renameCategory(int i, String n) {
        System.out.println(this.categories.get(i).getName());
        this.categories.get(i).setName(n);
        this.saveTodo();
    }

    // Remove category from todo and affect all task to no category
    public void removeCategory(int index) {
        this.categories.get(index).removeAllTask();
        this.categories.remove(index);

        this.saveTodo();
    }

    // todo name used in filename
    public String getName() {
        return name;
    }

    // Create a new task (longterm or simple) and add it to inprogress tasks list
    public void newTask (String name, boolean isLongtermTask, String ddate, String scategory, String sdate) {

        Task newtask;
        Date duedate, startdate;
        Category category;

        SimpleDateFormat format = Task.getDateformat();

        try {
            duedate   = format.parse(ddate);
        } catch (ParseException e) {
            // Due date will be set as tomorrow in constructor
            duedate   = null;
        }

        try {
            startdate = format.parse(sdate);
        } catch (ParseException e) {
            startdate = null;
        }

        // Get the associated category or null
        category = getCategoryByName(scategory);

        // Task type (longterm or simple)
        if (isLongtermTask) {
            newtask = new LongtermTask(name, duedate, category, startdate);
        } else {
            newtask = new SimpleTask(name, duedate, category);
        }

        this.task_inprogress.add(newtask);

        this.checkLateTasks();
    }

    // Check tasks not ended to mark them as late or in progress
    public void checkLateTasks() {

        // Are task in progress all in time
        for (int i = 0; i < this.task_inprogress.size(); i++) {
            Task t = this.task_inprogress.get(i);

            if (t.isLate()) {
                this.task_inprogress.remove(i);
                this.task_late.add(t);
            }
        }

        // Sort task in progress by due date
        this.task_inprogress.sort(Comparator.comparing(Task::getNextExpectedDate));

        // Are task marked as late all late
        for (int i = 0; i < this.task_late.size(); i++) {
            Task t = this.task_late.get(i);

            if (!t.isLate()) {
                this.task_late.remove(i);
                this.task_inprogress.add(t);
            }
        }

        // Sort late task by due date
        this.task_late.sort(Comparator.comparing(Task::getNextExpectedDate));
    }

    // Add class to DONE list
    public void addTaskDone (Task t) {
        this.task_done.add(t);
    }

    // Serialize the todo with todo serializer
    public void saveTodo() {

        try {

            //System.out.println("Saving Todo: " + this.name);
            TodoSerializer.saveTodo(this, this.name);

        } catch (TodoFileUnsaveableException e) {

            Window.displayErrorMessage("Filename \"" + e.getFilename() + "\" cannot be saved to disk");

        }
    }

    // LOad todo with todo serializer
    private static Todo loadTodo(String name) {

        try {

            //System.out.println("Loading Todo: " + name);
            return TodoSerializer.getTodo(name);

        } catch (TodoFileDoesNotExistException e) {

            //System.out.println("Todo " + e.getFilename() + " does not exist, creating it");
            return createNewTodo(name);

        } catch (TodoFileUnreadableException e) {

            String errormessage = "Filename \"" + e.getFilename() + "\" cannot be read on disk";
            Window.displayErrorMessage(errormessage);

        }
        return null;
    }

    public static Todo loadTodo() {
        return loadTodo(UNNAMED_TODO_NAME);
    }

    // Create the file and save the todo
    private static Todo createNewTodo(String name) {

        try {

            //System.out.println("Creating new Todo: " + name);
            return TodoSerializer.createTodo(name);

        } catch (TodoFileUnsaveableException e) {

            String errormessage = "Filename \"" + e.getFilename() + "\" cannot be saved to disk";
            Window.displayErrorMessage(errormessage);

        }
        return null;
    }

    // Remove todo file form disk
    public void deleteTodoFile() {

        try {

            TodoSerializer.deleteTodoIfExist(this.name);

        } catch (TodoFileUnreadableException e) {

            String errormessage = "Filename \"" + e.getFilename() + "\" could not be deleted";
            Window.displayErrorMessage(errormessage);

        }
    }

    // Change todo name and filename on disk
    public void renameTodo(String n) {

        deleteTodoFile();
        this.name = n;
        saveTodo();

    }
}
