package view.task;

import controller.Todo;
import model.Category;
import model.task.Task;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TaskCreator {

    private Todo todo;

    private JPanel pnPanel;
    private JTextField tfName, tfDate, tfStartdate;
    private JCheckBox cbType;
    private JButton btnAdd;
    private JComboBox cbCategory;

    public TaskCreator(Todo todo) {

        this.todo = todo;

        pnPanel = new JPanel();
        pnPanel.setBorder(new EmptyBorder(10, 0, 25, 0));

        GridBagLayout       gbPanel = new GridBagLayout();
        GridBagConstraints gbcPanel = new GridBagConstraints();

        pnPanel.setLayout( gbPanel );

        JLabel lbPanelname, lbLabelname, lbLabeltype, lbLabelstartdate, lbLabeldate;

        lbPanelname = new JLabel( "NEW TASK"  );
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 0;
        gbcPanel.gridwidth  = 20;
        gbcPanel.gridheight = 1;
        gbcPanel.fill = GridBagConstraints.BOTH;
        gbcPanel.weightx = 1;
        gbcPanel.weighty = 1;
        gbcPanel.anchor = GridBagConstraints.NORTHWEST;
        gbPanel.setConstraints( lbPanelname, gbcPanel );
        pnPanel.add( lbPanelname );

        lbLabelname = new JLabel( "Name"  );
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 1;
        gbcPanel.gridwidth  = 4;
        gbcPanel.gridheight = 1;
        gbcPanel.fill = GridBagConstraints.BOTH;
        gbcPanel.weightx = 1;
        gbcPanel.weighty = 1;
        gbcPanel.anchor = GridBagConstraints.WEST;
        gbPanel.setConstraints( lbLabelname, gbcPanel );
        pnPanel.add( lbLabelname );

        tfName = new JTextField( );
        gbcPanel.gridx = 4;
        gbcPanel.gridy = 1;
        gbcPanel.gridwidth  = 7;
        gbcPanel.gridheight = 1;
        gbcPanel.fill = GridBagConstraints.BOTH;
        gbcPanel.weightx = 1;
        gbcPanel.weighty = 0;
        gbcPanel.anchor = GridBagConstraints.WEST;
        gbPanel.setConstraints( tfName, gbcPanel );
        pnPanel.add( tfName );

        lbLabeldate = new JLabel( "Due Date"  );
        gbcPanel.gridx = 11;
        gbcPanel.gridy = 1;
        gbcPanel.gridwidth  = 4;
        gbcPanel.gridheight = 1;
        gbcPanel.fill = GridBagConstraints.BOTH;
        gbcPanel.weightx = 1;
        gbcPanel.weighty = 1;
        gbcPanel.anchor = GridBagConstraints.EAST;
        gbPanel.setConstraints( lbLabeldate, gbcPanel );
        pnPanel.add( lbLabeldate );

        tfDate = new JTextField( );
        gbcPanel.gridx = 15;
        gbcPanel.gridy = 1;
        gbcPanel.gridwidth  = 5;
        gbcPanel.gridheight = 1;
        gbcPanel.fill = GridBagConstraints.BOTH;
        gbcPanel.weightx = 1;
        gbcPanel.weighty = 0;
        gbcPanel.anchor = GridBagConstraints.EAST;
        gbPanel.setConstraints( tfDate, gbcPanel );
        pnPanel.add( tfDate );

        lbLabeltype = new JLabel( "Long-term Task"  );
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 2;
        gbcPanel.gridwidth  = 7;
        gbcPanel.gridheight = 1;
        gbcPanel.fill = GridBagConstraints.BOTH;
        gbcPanel.weightx = 1;
        gbcPanel.weighty = 1;
        gbcPanel.anchor = GridBagConstraints.WEST;
        gbPanel.setConstraints( lbLabeltype, gbcPanel );
        pnPanel.add( lbLabeltype );

        lbLabelstartdate = new JLabel( "Start Date"  );
        gbcPanel.gridx = 11;
        gbcPanel.gridy = 2;
        gbcPanel.gridwidth  = 4;
        gbcPanel.gridheight = 1;
        gbcPanel.fill = GridBagConstraints.BOTH;
        gbcPanel.weightx = 1;
        gbcPanel.weighty = 1;
        gbcPanel.anchor = GridBagConstraints.EAST;
        gbPanel.setConstraints( lbLabelstartdate, gbcPanel );
        pnPanel.add( lbLabelstartdate );

        tfStartdate = new JTextField( );
        gbcPanel.gridx = 15;
        gbcPanel.gridy = 2;
        gbcPanel.gridwidth  = 5;
        gbcPanel.gridheight = 1;
        gbcPanel.fill = GridBagConstraints.BOTH;
        gbcPanel.weightx = 1;
        gbcPanel.weighty = 0;
        gbcPanel.anchor = GridBagConstraints.EAST;
        gbPanel.setConstraints( tfStartdate, gbcPanel );
        tfStartdate.setEnabled(false);
        pnPanel.add( tfStartdate );

        cbType = new JCheckBox();
        gbcPanel.gridx = 4;
        gbcPanel.gridy = 2;
        gbcPanel.gridwidth  = 4;
        gbcPanel.gridheight = 1;
        gbcPanel.fill = GridBagConstraints.BOTH;
        gbcPanel.weightx = 1;
        gbcPanel.weighty = 0;
        gbcPanel.anchor = GridBagConstraints.WEST;
        gbPanel.setConstraints( cbType, gbcPanel );
        pnPanel.add( cbType );

        // Listen to longterm task checkbox
        cbType.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                boolean ischecked = isLongtermTask();

                tfStartdate.setEnabled(ischecked);

                // If longterm task fill start date, else leave empty
                if(!ischecked) tfStartdate.setText("");
                else tfStartdate.setText(Task.getDateformat().format(new Date()));
            }
        });

        btnAdd = new JButton( "ADD" );
        gbcPanel.gridx = 15;
        gbcPanel.gridy = 3;
        gbcPanel.gridwidth  = 5;
        gbcPanel.gridheight = 1;
        gbcPanel.fill = GridBagConstraints.BOTH;
        gbcPanel.weightx = 1;
        gbcPanel.weighty = 0;
        gbcPanel.anchor = GridBagConstraints.EAST;
        gbPanel.setConstraints( btnAdd, gbcPanel );
        pnPanel.add( btnAdd );

        cbCategory = new JComboBox();
        updateCategories();

        gbcPanel.gridx = 0;
        gbcPanel.gridy = 3;
        gbcPanel.gridwidth  = 5;
        gbcPanel.gridheight = 1;
        gbcPanel.fill = GridBagConstraints.BOTH;
        gbcPanel.weightx = 1;
        gbcPanel.weighty = 0;
        gbcPanel.anchor = GridBagConstraints.WEST;
        gbPanel.setConstraints( cbCategory, gbcPanel );
        pnPanel.add( cbCategory );

        clear();
    }

    // Check if every field is valid and not empty
    public boolean checkValid() {

        this.resetColors();

        SimpleDateFormat format = Task.getDateformat();

        String duedate   = this.tfDate.getText().trim();
        String startdate = this.tfStartdate.getText().trim();

        String name = this.tfName.getText().trim();

        // Check if name not empty
        if (name.equals("")) {
            this.tfName.setBackground(Color.RED);
            return false;
        }

        // Check if due date is correct
        try {
            format.parse(duedate);
        } catch (ParseException pe) {
            this.tfDate.setBackground(Color.RED);
            return false;
        }

        // If longterm task and start date is specified, check if start date is correct
        try {
            if (this.isLongtermTask() && !startdate.equals(""))
                format.parse(startdate);
        } catch (ParseException pe) {
            this.tfStartdate.setBackground(Color.RED);
            return false;
        }

        return true;
    }

    // Reset fields background color
    private void resetColors() {
        this.tfName.setBackground(null);
        this.tfDate.setBackground(null);
        this.tfStartdate.setBackground(null);
    }

    // Remove text in all fields
    public void clear() {

        // Set default due date as tomorrow
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date tomorrow = calendar.getTime();

        this.tfName.setText("");
        this.tfDate.setText(Task.getDateformat().format(tomorrow));

        // If longterm task is selected set default start date as today
        if (isLongtermTask()) this.tfStartdate.setText(Task.getDateformat().format(new Date()));
        else this.tfStartdate.setText("");

        // Reset selected category
        this.cbCategory.setSelectedIndex(0);
    }

    // Update categories listed in category selector
    public void updateCategories() {

        ArrayList<Category> categories = todo.getCategories();
        String[] categoriesarray = new String[categories.size() + 1];

        categoriesarray[0] = "No category";
        for (int i = 0; i < categories.size(); i++) {
            categoriesarray[i + 1] = categories.get(i).getName();
        }

        cbCategory.setModel(new DefaultComboBoxModel(categoriesarray));
        cbCategory.setSelectedIndex(0);
    }

    public JPanel getPanel() {
        return pnPanel;
    }

    public JButton getBtnadd() {
        return btnAdd;
    }

    public String getTaskName() {
        return tfName.getText();
    }

    public String getDueDate() {
        return tfDate.getText();
    }

    public String getStartDate() {
        return tfStartdate.getText();
    }

    public String getCategory() {
        return (String)this.cbCategory.getSelectedItem();
    }

    public boolean isLongtermTask() {
        return cbType.isSelected();
    }

}
