import controller.Todo;
import view.Window;

public class Main {

    public static void main(String[] args) {

        // Default todo "unnamed.todo" is loaded
        Todo todo = Todo.loadTodo();
        new Window(todo);

    }
}
