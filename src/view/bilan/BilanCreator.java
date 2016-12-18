package view.bilan;

import model.task.Task;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BilanCreator {

    private JPanel panel;
    private JTextField fromdate, todate;
    private JButton create;

    public BilanCreator () {

        SimpleDateFormat format = Task.getDateformat();

        this.panel = new JPanel();

        this.panel.add(new JLabel("NEW BILAN "), BorderLayout.WEST);

        this.fromdate = new JTextField(format.format(new Date()));
        this.todate   = new JTextField(format.format(new Date()));

        this.panel.add(new JLabel(" FROM: "));
        this.panel.add(this.fromdate);
        this.panel.add(new JLabel(" TO: "));
        this.panel.add(this.todate);

        this.create = new JButton(" CREATE ");
        this.panel.add(this.create);
    }

    public JPanel getPanel() {
        return panel;
    }

    public JButton getCreate() {
        return create;
    }

    public Date getFromdate() {
        // If input is not a date (not parsable by simple date format) returns null and color field
        this.fromdate.setBackground(null);
        try {
            return Task.getDateformat().parse(this.fromdate.getText());
        } catch (ParseException e) {
            this.fromdate.setBackground(Color.RED);
            return null;
        }
    }

    public Date getTodate() {
        // If input is not a date (not parsable by simple date format) returns null and color field
        this.todate.setBackground(null);
        try {
            return Task.getDateformat().parse(this.todate.getText());
        } catch (ParseException e) {
            this.todate.setBackground(Color.RED);
            return null;
        }
    }
}

