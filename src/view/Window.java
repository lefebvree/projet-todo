package view;

import controller.Todo;
import model.task.TaskStatus;
import view.bilan.BilanCreator;
import view.bilan.BilanWindow;
import view.category.CategoryList;
import view.task.TaskCreator;
import view.task.TaskList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Window {

    private Todo todo;

    private JFrame frame;
    private String framename;
    private JPanel mainpanel;

//    private JMenuBar menu;
//    private JMenu menuFile, menuEdition;
//    private JMenuItem menuFileNew,menuFileOpen, menuFileRename, menuFileDelete;

    private TaskList tasks_inprogress_panel;
    private TaskList tasks_late_panel;
    private TaskList tasks_done_panel;

    private TaskCreator task_creator;

    private BilanCreator bilan_creator;

    private CategoryList category_list;

    public Window(Todo t) {

        this.todo = t;
        this.todo.checkLateTasks();

        // Window name
        this.framename = "Todo List - \"" + t.getName() + "\"";

        this.frame = new JFrame();
        this.frame.setTitle(this.framename);
        this.frame.setSize(800, 500);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Try to use system style (buttons, borders ...)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not retrieve System look and feel");
            e.printStackTrace();
        }

        // Top menu with todo files managing (new, save...)
//        this.menu           = new JMenuBar();
//        this.menuFile       = new JMenu("File");
//        this.menuFileNew    = new JMenuItem("New");    this.menuFile.add(this.menuFileNew);
//        this.menuFileOpen   = new JMenuItem("Open");   this.menuFile.add(this.menuFileOpen);
//        this.menuFileRename = new JMenuItem("Rename"); this.menuFile.add(this.menuFileRename);
//        this.menuFileDelete = new JMenuItem("Delete"); this.menuFile.add(this.menuFileDelete);
//        this.menu.add(this.menuFile);

//        this.frame.setJMenuBar(this.menu);

        this.mainpanel = new JPanel();
        this.mainpanel.setLayout(new GridLayout(0,1));
        this.mainpanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // If content is larger than window, shows a scroll bar
        JScrollPane scrollpane = new JScrollPane();
        // Speed up scroll speed
        scrollpane.getVerticalScrollBar().setUnitIncrement(16);

        scrollpane.setLayout(new ScrollPaneLayout());
        scrollpane.setViewportView(this.mainpanel);
        this.frame.add(scrollpane);

        this.task_creator = new TaskCreator(this.todo);
        // On task creation (ADD button)
        this.task_creator.getBtnadd().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                // Task is created only if all fields are correct
                if (!task_creator.checkValid()) return;

                todo.newTask (
                        task_creator.getTaskName(),
                        task_creator.isLongtermTask(),
                        task_creator.getDueDate(),
                        task_creator.getCategory(),
                        task_creator.getStartDate()
                );

                task_creator.clear();

                updateAll();
            }
        });
        this.mainpanel.add(this.task_creator.getPanel());

        // Tasks lists
        this.tasks_late_panel = new TaskList(todo, TaskStatus.LATE, this);
        this.mainpanel.add(this.tasks_late_panel.getPanel());

        this.tasks_inprogress_panel = new TaskList(todo, TaskStatus.INPROGRESS, this);
        this.mainpanel.add(this.tasks_inprogress_panel.getPanel());

        // Category list
        this.category_list = new CategoryList(this.todo, this);
        this.mainpanel.add(this.category_list.getPanel());

        // Bilan creation panel
        this.bilan_creator = new BilanCreator();
        this.bilan_creator.getCreate().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // Show new window with bilan infos
                new BilanWindow(
                        todo,
                        bilan_creator.getFromdate(),
                        bilan_creator.getTodate()
                );
            }
        });
        this.mainpanel.add(this.bilan_creator.getPanel());

        // Tasks done at the end
        this.tasks_done_panel = new TaskList(todo, TaskStatus.DONE, this);
        this.mainpanel.add(this.tasks_done_panel.getPanel());

        this.frame.setVisible(true);
    }

    // Update All task lists
    public void updateAll() {
        this.tasks_inprogress_panel.updateAll();
        this.tasks_late_panel.updateAll();
        this.tasks_done_panel.updateAll();
        this.task_creator.updateCategories();
    }

    // Create a new window on top displaying custom message
    public static void displayErrorMessage (String message) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Could not retrieve System look and feel");
        }

        JFrame error = new JFrame();
        error.setTitle("Todo List - Error");
        error.add(new JLabel("Error: " + message));
        error.setSize(500, 100);
        error.setVisible(true);
        error.setAlwaysOnTop(true);
    }
}
