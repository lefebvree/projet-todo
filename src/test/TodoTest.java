package test;

import controller.Todo;
import model.task.LongtermTask;
import model.task.SimpleTask;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TodoTest {
    @org.junit.jupiter.api.Test
    void getCategoryByName() {
        // Test if return correct category or null
        Todo T = new Todo("Test1");

        T.newCategory("test");

        assertEquals("test", T.getCategoryByName("test").getName());
        assertNull(T.getCategoryByName("null"));

        T.deleteTodoFile();
    }

    @org.junit.jupiter.api.Test
    void renameCategory() {
        // Test if return correct category or null
        Todo T = new Todo("Test2");

        T.newCategory("test1");

        assertEquals("test1", T.getCategoryByName("test1").getName());

        // New category is third one after work and personal
        T.renameCategory(2, "test2");

        assertEquals("test2", T.getCategoryByName("test2").getName());
        assertNull(T.getCategoryByName("test1"));

        T.deleteTodoFile();
    }

    @org.junit.jupiter.api.Test
    void removeCategory() {
        Todo T = new Todo("Test3");

        T.newCategory("test3");

        T.removeCategory(2);

        assertNull(T.getCategoryByName("test3"));

        T.deleteTodoFile();
    }

    @org.junit.jupiter.api.Test
    void newTask() {
        Todo T = new Todo("Test3");

        assertEquals(0, T.getTask_inprogress().size());

        T.newTask("task1", false, "01/01/2000", null, "");

        assertNotNull(T.getTask_inprogress().get(0));

        T.newTask("task2", true, "01/01/2000", null, "");

        assertNotNull(T.getTask_inprogress().get(1));

        assertEquals(2, T.getTask_inprogress().size());

        T.deleteTodoFile();
    }

    @org.junit.jupiter.api.Test
    void checkLateTasks() {
        Todo T = new Todo("Test4");

        assertEquals(0, T.getTask_late().size());

        T.newTask("task1", true, "01/01/2000", null, "");
        T.checkLateTasks();
        assertEquals(0, T.getTask_late().size());

        T.newTask("task2", true, "01/01/2000", null, "01/01/2000");
        T.checkLateTasks();
        assertNotNull(T.getTask_late().get(0));

        assertEquals(1, T.getTask_late().size());

        T.deleteTodoFile();
    }

    @org.junit.jupiter.api.Test
    void addTaskDone() {
        Todo T = new Todo("Test5");

        assertEquals(0, T.getTask_done().size());
        T.addTaskDone(new LongtermTask("test", new Date(), null, new Date()));

        assertNotNull(T.getTask_done().get(0));
        assertEquals(1, T.getTask_done().size());

        T.addTaskDone(new SimpleTask("test", new Date(), null));
        assertNotNull(T.getTask_done().get(1));
        assertEquals(2, T.getTask_done().size());

        T.deleteTodoFile();
    }
}