package controller;

import controller.exceptions.*;

import java.io.*;
import java.nio.file.Files;

public class TodoSerializer {

    private static final String FILE_DIRECTORY        = "todos/";
    private static final String FILE_EXTENSION        = ".todo";
    private static final String UNNAMED_TODO_FILENAME = "unnamed";


    private static String getFileName(String name) {
        return FILE_DIRECTORY + name + FILE_EXTENSION;
    }

    static Todo getTodo(String name) throws TodoFileDoesNotExistException, TodoFileUnreadableException {

        String filename = getFileName(name);

        try {

            FileInputStream fis  = new FileInputStream(filename);
            ObjectInputStream is = new ObjectInputStream(fis);

            Todo todo = (Todo) is.readObject();
            is.close();

            return todo;

        } catch (IOException e) {
            throw new TodoFileDoesNotExistException(filename);
        } catch (ClassNotFoundException e) {
            throw  new TodoFileUnreadableException(filename);
        }
    }

    public static Todo getTodo() throws TodoFileDoesNotExistException, TodoFileUnreadableException {
        return getTodo(UNNAMED_TODO_FILENAME);
    }

    static void saveTodo(Todo todo, String name) throws TodoFileUnsaveableException {

        String filename = getFileName(name);

        try {

            FileOutputStream fos  = new FileOutputStream(filename);
            ObjectOutputStream os = new ObjectOutputStream(fos);

            os.writeObject(todo);
            os.close();

        } catch (IOException e) {
            throw new TodoFileUnsaveableException(filename);
        }
    }

    public static void saveTodo(Todo todo) throws TodoFileUnsaveableException {
        saveTodo(todo, UNNAMED_TODO_FILENAME);
    }

    static Todo createTodo(String name) throws TodoFileUnsaveableException {

        Todo todo = new Todo(name);
        saveTodo(todo, name);

        return todo;
    }

    public static Todo createTodo() throws TodoFileUnsaveableException {
        return createTodo(UNNAMED_TODO_FILENAME);
    }

    static void deleteTodoIfExist(String name) throws TodoFileUnreadableException {

        String filename = getFileName(name);

        File file = new File(filename);

        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            throw new TodoFileUnreadableException(filename);
        }

    }

}
