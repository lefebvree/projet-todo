package view.category;

import controller.Todo;
import view.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// CategoryList display category creation interface and category list
public class CategoryList {
    private JPanel panel, categorylist;
    private JTextField cname;
    private Todo todo;
    private Window window;

    public CategoryList(Todo t, Window w) {

        this.panel = new JPanel();
        this.panel.setLayout(new GridLayout(0, 1));

        this.todo   = t;
        this.window = w;

        this.panel.add(new JLabel("NEW CATEGORY"));

        this.cname     = new JTextField("", 12);
        JButton addbtn = new JButton("ADD");

        addbtn.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent e) {

                cname.setBackground(null);

                // Is new category name valid
                if (isValid()) {
                    add(cname.getText().trim());
                    cname.setText("");
                } else {
                    cname.setBackground(Color.RED);
                }
            }
        });

        JPanel panelnew = new JPanel();

        panelnew.add(cname, BorderLayout.WEST);
        panelnew.add(addbtn, BorderLayout.EAST);

        this.panel.add(panelnew);

        this.panel.add(new JLabel("CATEGORIES"));

        this.categorylist = new JPanel();
        this.categorylist.setLayout(new GridLayout(0, 1));
        this.panel.add(this.categorylist);

        this.updateAll();
    }

    private void updateAll() {

        this.categorylist.removeAll();

        for (int i = 0; i < this.todo.getCategories().size(); i++) {
            final int index = i;
            CategoryPanel c = new CategoryPanel(this.todo.getCategories().get(i));
            c.getRemoveButton().addMouseListener(new MouseAdapter() {
                public void mouseClicked (MouseEvent e) {
                    remove(index);
                }
            });

            c.getRenameButton().addMouseListener(new MouseAdapter() {
                public void mouseClicked (MouseEvent e) {
                    // Must be unique and not empty
                    String newname = c.getNewname().trim();

                    if (!newname.equals("") && todo.getCategoryByName(newname) == null)
                        rename(index, newname);
                }
            });

            this.categorylist.add(c.getPanel());
        }

        this.categorylist.validate();
        this.categorylist.repaint();
    }

    private void add(String name) {
        this.todo.newCategory(name);
        this.window.updateAll();
        updateAll();
    }

    private void remove(int index) {
        this.todo.removeCategory(index);
        this.window.updateAll();
        updateAll();
    }

    private void rename(int index, String name) {
        this.todo.renameCategory(index, name);
        this.window.updateAll();
        updateAll();
    }

    // Check if new name is not empty and category does not already exist
    private boolean isValid() {

        String name = this.cname.getText().trim();

        // If name is empty
        if (name.equals("")) return false;
        // Does category already exists
        return (this.todo.getCategoryByName(name) == null);
    }

    public JPanel getPanel() {
        return panel;
    }
}
