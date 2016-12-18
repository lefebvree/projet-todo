package view.bilan;

import controller.Bilan;
import controller.Todo;
import model.task.Task;
import view.Window;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BilanWindow {

    // Generate a new bilan and shows a window listing all infos
    public BilanWindow (Todo t, Date fromdate, Date todate) {

        // If dates are incorect (getDate sends null value) don't show anything
        if (fromdate == null || todate == null) return;

        Bilan bilan = new Bilan(fromdate, todate, t);

        if (bilan.generateBilan()) {

            // If no tasks found no need to display the window
            if (bilan.getTasks_bilan().size() == 0) {
                Window.displayErrorMessage("No task due in this time frame.");
                return;
            }

            SimpleDateFormat format = Task.getDateformat();

            JFrame frame = new JFrame();
            frame.setTitle("Bilan from " + format.format(fromdate) + " to " + format.format(todate));
            frame.setSize(700, 400);

            JPanel toppanel = new JPanel();
            toppanel.add(new JLabel("Total tasks: " + bilan.getTotaltasks()));
            toppanel.add(new JLabel("Complete: " + bilan.getDone_percent() + "%"));
            toppanel.add(new JLabel("Late: " + bilan.getLate_percent() + "%"));
            toppanel.add(new JLabel("In progress: " + bilan.getInprogress_percent() + "%"));
            frame.add(toppanel, BorderLayout.NORTH);

            JPanel mainpanel = new JPanel();

            mainpanel.setLayout(new GridLayout(0,1));
            mainpanel.setBorder(new EmptyBorder(15, 15, 15, 15));

            mainpanel.add(new JLabel("Tasks due between " + format.format(fromdate) + " and " + format.format(todate)));

            for (Task task : bilan.getTasks_bilan()) {
                // Display compact view of every tasks
                JPanel taskpanel = new JPanel();
                taskpanel.setLayout(new GridLayout(1,0));
                taskpanel.add(new JLabel (task.getName(), JLabel.LEFT));
                if (task.getCategory() != null)
                    taskpanel.add(new JLabel (task.getCategory().getName()));
                taskpanel.add(new JLabel (format.format(task.getDate()) + " - " + task.getStatus(), JLabel.RIGHT));

                mainpanel.add(taskpanel);
            }

            // If content is larger than window, shows a scroll bar
            JScrollPane scrollpane = new JScrollPane();
            // Speed up scroll speed
            scrollpane.getVerticalScrollBar().setUnitIncrement(16);

            scrollpane.setLayout(new ScrollPaneLayout());
            scrollpane.setViewportView(mainpanel);
            frame.add(scrollpane);

            frame.setVisible(true);

        } else {
            Window.displayErrorMessage("Start date must be before end date.");
        }

    }
}
