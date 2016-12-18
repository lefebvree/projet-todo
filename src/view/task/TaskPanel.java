package view.task;

import model.task.LongtermTask;
import model.task.SimpleTask;
import model.task.Task;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.ParseException;
import java.util.Date;

public class TaskPanel {

    private JPanel pnPanel;
    private JTextField tfprogress, tfDuedate;
    private JButton btdone, btdelete, btupdateDate;
    private boolean isEditable, isLongtermTask;

    public TaskPanel (SimpleTask task) {

        JLabel lbtitle, lbdate, lbtimeleft, lbcategory;

        this.isEditable = task.isEditable();
        this.isLongtermTask = false;

        pnPanel = new JPanel();
        pnPanel.setBorder(new EmptyBorder(5, 0, 10, 0));

        GridBagLayout       gbPanel = new GridBagLayout();
        GridBagConstraints gbcPanel = new GridBagConstraints();

        pnPanel.setLayout( gbPanel );

        lbtitle = new JLabel(task.getName());

        gbcPanel.gridx = 0;
        gbcPanel.gridy = 0;
        gbcPanel.gridwidth  = 2;
        gbcPanel.gridheight = 1;
        gbcPanel.fill = GridBagConstraints.BOTH;
        gbcPanel.weightx = 1;
        gbcPanel.weighty = 1;
        gbcPanel.anchor  = GridBagConstraints.WEST;
        gbPanel.setConstraints( lbtitle, gbcPanel );
        pnPanel.add( lbtitle );

        if (task.getCategory() != null) {
            String category = task.getCategory().getName();
            lbcategory = new JLabel(category);
            gbcPanel.gridx = 1;
            gbcPanel.gridy = 1;
            gbcPanel.gridwidth  = 1;
            gbcPanel.gridheight = 1;
            gbcPanel.fill = GridBagConstraints.BOTH;
            gbcPanel.weightx = 1;
            gbcPanel.weighty = 1;
            gbcPanel.anchor  = GridBagConstraints.WEST;
            gbPanel.setConstraints( lbcategory, gbcPanel );
            pnPanel.add( lbcategory );
        }

        // Only show buttons, date change and time left if task is not done
        if (this.isEditable) {

            btdone = new JButton("DONE");
            gbcPanel.gridx = 3;
            gbcPanel.gridy = 1;
            gbcPanel.gridwidth  = 1;
            gbcPanel.gridheight = 1;
            gbcPanel.fill = GridBagConstraints.BOTH;
            gbcPanel.weightx = 1;
            gbcPanel.weighty = 0;
            gbcPanel.anchor  = GridBagConstraints.EAST;
            gbPanel.setConstraints( btdone, gbcPanel );
            pnPanel.add( btdone );

            btdelete = new JButton("DELETE");
            gbcPanel.gridx = 2;
            gbcPanel.gridy = 1;
            gbcPanel.gridwidth  = 1;
            gbcPanel.gridheight = 1;
            gbcPanel.fill = GridBagConstraints.BOTH;
            gbcPanel.weightx = 1;
            gbcPanel.weighty = 0;
            gbcPanel.anchor  = GridBagConstraints.EAST;
            gbPanel.setConstraints( btdelete, gbcPanel );
            pnPanel.add( btdelete );


            lbtimeleft = new JLabel(task.getTimeLeft());
            gbcPanel.gridx = 0;
            gbcPanel.gridy = 1;
            gbcPanel.gridwidth  = 1;
            gbcPanel.gridheight = 1;
            gbcPanel.fill = GridBagConstraints.BOTH;
            gbcPanel.weightx = 1;
            gbcPanel.weighty = 1;
            gbcPanel.anchor  = GridBagConstraints.WEST;
            gbPanel.setConstraints( lbtimeleft, gbcPanel );
            pnPanel.add( lbtimeleft );

            tfDuedate = new JTextField(task.getDueDate());
            tfDuedate.setHorizontalAlignment(SwingConstants.RIGHT);
            gbcPanel.gridx = 2;
            gbcPanel.gridy = 0;
            gbcPanel.gridwidth  = 1;
            gbcPanel.gridheight = 1;
            gbcPanel.fill = GridBagConstraints.BOTH;
            gbcPanel.weightx = 1;
            gbcPanel.weighty = 1;
            gbcPanel.anchor  = GridBagConstraints.EAST;
            gbPanel.setConstraints( tfDuedate, gbcPanel );
            pnPanel.add( tfDuedate );

            btupdateDate = new JButton("CHANGE");
            gbcPanel.gridx = 3;
            gbcPanel.gridy = 0;
            gbcPanel.gridwidth  = 1;
            gbcPanel.gridheight = 1;
            gbcPanel.fill = GridBagConstraints.BOTH;
            gbcPanel.weightx = 1;
            gbcPanel.weighty = 1;
            gbcPanel.anchor  = GridBagConstraints.EAST;
            gbPanel.setConstraints( btupdateDate, gbcPanel );
            pnPanel.add( btupdateDate );

        } else {
            // Task is non editable, date is displayed as label
            lbdate = new JLabel(task.getDueDate(), SwingConstants.RIGHT);
            gbcPanel.gridx = 3;
            gbcPanel.gridy = 0;
            gbcPanel.gridwidth  = 2;
            gbcPanel.gridheight = 1;
            gbcPanel.fill = GridBagConstraints.BOTH;
            gbcPanel.weightx = 1;
            gbcPanel.weighty = 1;
            gbcPanel.anchor  = GridBagConstraints.EAST;
            gbPanel.setConstraints( lbdate, gbcPanel );
            pnPanel.add( lbdate );
        }
    }

    public TaskPanel (LongtermTask task) {

        JLabel  lbtitle, lbdate, lbtimeleft, lbcategory;

        this.isEditable     = task.isEditable();
        this.isLongtermTask = true;

        pnPanel = new JPanel();

        GridBagLayout       gbPanel = new GridBagLayout();
        GridBagConstraints gbcPanel = new GridBagConstraints();

        pnPanel.setLayout( gbPanel );

        lbtitle = new JLabel(task.getName());

        gbcPanel.gridx = 0;
        gbcPanel.gridy = 0;
        gbcPanel.gridwidth  = 2;
        gbcPanel.gridheight = 1;
        gbcPanel.fill = GridBagConstraints.BOTH;
        gbcPanel.weightx = 1;
        gbcPanel.weighty = 1;
        gbcPanel.anchor  = GridBagConstraints.WEST;
        gbPanel.setConstraints( lbtitle, gbcPanel );
        pnPanel.add( lbtitle );

        if (task.getCategory() != null) {
            String category = task.getCategory().getName();
            lbcategory = new JLabel(category);
            gbcPanel.gridx = 1;
            gbcPanel.gridy = 1;
            gbcPanel.gridwidth  = 1;
            gbcPanel.gridheight = 1;
            gbcPanel.fill = GridBagConstraints.BOTH;
            gbcPanel.weightx = 1;
            gbcPanel.weighty = 1;
            gbcPanel.anchor  = GridBagConstraints.WEST;
            gbPanel.setConstraints( lbcategory, gbcPanel );
            pnPanel.add( lbcategory );
        }

        // Only show buttons and time left if task is not done
        if (this.isEditable) {

            lbtimeleft = new JLabel(task.getTimeLeft());
            gbcPanel.gridx = 0;
            gbcPanel.gridy = 1;
            gbcPanel.gridwidth  = 1;
            gbcPanel.gridheight = 1;
            gbcPanel.fill = GridBagConstraints.BOTH;
            gbcPanel.weightx = 1;
            gbcPanel.weighty = 1;
            gbcPanel.anchor  = GridBagConstraints.WEST;
            gbPanel.setConstraints( lbtimeleft, gbcPanel );
            pnPanel.add( lbtimeleft );

            tfprogress = new JTextField();
            tfprogress.setText(task.getProgression() + "%");
            tfprogress.setHorizontalAlignment(SwingConstants.RIGHT);
            gbcPanel.gridx = 3;
            gbcPanel.gridy = 1;
            gbcPanel.gridwidth  = 1;
            gbcPanel.gridheight = 1;
            gbcPanel.fill = GridBagConstraints.BOTH;
            gbcPanel.weightx = 1;
            gbcPanel.weighty = 0;
            gbcPanel.anchor  = GridBagConstraints.EAST;
            gbPanel.setConstraints( tfprogress, gbcPanel );
            pnPanel.add( tfprogress );

            btdone = new JButton("UPDATE");
            gbcPanel.gridx = 4;
            gbcPanel.gridy = 1;
            gbcPanel.gridwidth  = 1;
            gbcPanel.gridheight = 1;
            gbcPanel.fill = GridBagConstraints.BOTH;
            gbcPanel.weightx = 1;
            gbcPanel.weighty = 0;
            gbcPanel.anchor  = GridBagConstraints.EAST;
            gbPanel.setConstraints( btdone, gbcPanel );
            pnPanel.add( btdone );

            btdelete = new JButton("DELETE");
            gbcPanel.gridx = 2;
            gbcPanel.gridy = 1;
            gbcPanel.gridwidth  = 1;
            gbcPanel.gridheight = 1;
            gbcPanel.fill = GridBagConstraints.BOTH;
            gbcPanel.weightx = 1;
            gbcPanel.weighty = 0;
            gbcPanel.anchor  = GridBagConstraints.EAST;
            gbPanel.setConstraints( btdelete, gbcPanel );
            pnPanel.add( btdelete );

            tfDuedate = new JTextField(task.getDueDate());
            tfDuedate.setHorizontalAlignment(SwingConstants.RIGHT);
            gbcPanel.gridx = 3;
            gbcPanel.gridy = 0;
            gbcPanel.gridwidth  = 1;
            gbcPanel.gridheight = 1;
            gbcPanel.fill = GridBagConstraints.BOTH;
            gbcPanel.weightx = 1;
            gbcPanel.weighty = 1;
            gbcPanel.anchor  = GridBagConstraints.EAST;
            gbPanel.setConstraints( tfDuedate, gbcPanel );
            pnPanel.add( tfDuedate );

            btupdateDate = new JButton("CHANGE");
            gbcPanel.gridx = 4;
            gbcPanel.gridy = 0;
            gbcPanel.gridwidth  = 1;
            gbcPanel.gridheight = 1;
            gbcPanel.fill = GridBagConstraints.BOTH;
            gbcPanel.weightx = 1;
            gbcPanel.weighty = 1;
            gbcPanel.anchor  = GridBagConstraints.EAST;
            gbPanel.setConstraints( btupdateDate, gbcPanel );
            pnPanel.add( btupdateDate );
        } else {
            // Task is non editable, date is displayed as label
            lbdate = new JLabel(task.getDueDate(), SwingConstants.RIGHT);
            gbcPanel.gridx = 4;
            gbcPanel.gridy = 0;
            gbcPanel.gridwidth  = 2;
            gbcPanel.gridheight = 1;
            gbcPanel.fill = GridBagConstraints.BOTH;
            gbcPanel.weightx = 1;
            gbcPanel.weighty = 1;
            gbcPanel.anchor  = GridBagConstraints.EAST;
            gbPanel.setConstraints( lbdate, gbcPanel );
            pnPanel.add( lbdate );
        }
    }

    JPanel getPanel() {
        return pnPanel;
    }

    JButton getBtdone() {
        return btdone;
    }

    JButton getBtdelete() {
        return btdelete;
    }

    JButton getBtupdateDate() {
        return btupdateDate;
    }

    // For longterm tasks, get the new progression from user input
    // Return -1 in case of bad input (not a number or percentage)
    int getnewProgress() {
        if (!isLongtermTask()) return -1;

        tfprogress.setBackground(null);

        // Input is trimmed without percentage
        String sn = tfprogress.getText().replace("%", "").trim();

        try {
            return Integer.parseInt(sn);
        } catch (NumberFormatException e) {
            // Value entered is not a number and cannot be parsed
            tfprogress.setBackground(Color.RED);
            return -1;
        }
    }

    Date getNewDate() {
        this.tfDuedate.setBackground(null);

        try {
            Date newduedate = Task.getDateformat().parse(this.tfDuedate.getText());
            if (newduedate.after(new Date())) return newduedate;

            // Color background red since new date is passed
            this.tfDuedate.setBackground(Color.RED);
            return null;
        } catch (ParseException e) {
            this.tfDuedate.setBackground(Color.RED);
            return null;
        }
    }

    boolean isEditable() {
        return isEditable;
    }

    boolean isLongtermTask() {
        return isLongtermTask;
    }
}
