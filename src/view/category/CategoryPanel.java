package view.category;

import model.Category;

import javax.swing.*;

class CategoryPanel {

    private JPanel panel;
    private JButton remove, rename;
    private JTextField newname;

    // CategoryPanel display rename and delete option for each Category
    CategoryPanel(Category c) {
        this.panel = new JPanel();

        String category = c.getName();

        JLabel categoryName = new JLabel(category);

        this.remove  = new JButton("REMOVE");
        this.newname = new JTextField("", 8);
        this.rename  = new JButton("RENAME");

        this.panel.add(categoryName);
        this.panel.add(this.remove);
        this.panel.add(this.newname);
        this.panel.add(this.rename);
    }

    JPanel getPanel() {
        return panel;
    }

    JButton getRemoveButton() {
        return remove;
    }

    // Name must be unique
    String getNewname() {
        return newname.getText();
    }

    JButton getRenameButton() {
        return rename;
    }
}
