package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.IndexcardBox;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class IndexcardBoxTab extends JDialog {
    private final Controller controller;
    private JPanel contentPane;
    private JLabel KarteikästenLabel;
    private JButton searchButton;
    private JList indexcardBoxList;
    private JTextField textField1;
    private JButton deleteButton;
    private JButton editButton;
    private JButton createButton;
    private JPanel middlePanel;
    private JButton removeFilterButton;

    public IndexcardBoxTab(Controller controller) {
        this.controller = controller;
        setContentPane(contentPane);
        setTitle("Karteikästen");
        pack();
        setMinimumSize(getSize());
        setSize(800, 600);
        updateList(controller.getAllIndexcardBoxes());
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSearch();
            }
        });
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCreate();
            }
        });
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onEdit();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onDelete();
            }
        });
    }

    /**
     * Updates the list of indexcards
     */
    private void onRemoveFilter() {
        updateList(controller.getAllIndexcardBoxes());
    }

    /**
     * This method updates the list of indexcards and displays the Names in the list.
     * @param indexcardBoxList the list of indexcards which should be displayed
     */

    private void updateList(List<IndexcardBox> indexcardBoxList) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (IndexcardBox indexcardBox : indexcardBoxList) {
            listModel.addElement(indexcardBox.getName());
        }
        this.indexcardBoxList.setModel(listModel);
    }

    /**
     * Checks if the user has selected an indexcard if so it deletes it
     * If multiple indexcards are selected it deletes all of them
     * If not it opens a dialog to delete an indexcard
     */
    private void onDelete() {
        if (indexcardBoxList.getSelectedValue() != null) {
            for (Object indexcardBoxName : indexcardBoxList.getSelectedValuesList()) {
                controller.deleteIndexcardBox(controller.getIndexcardBoxByName(indexcardBoxName.toString()).get().getName());
            }
        }
        else {
            controller.deleteIndexcardBox();
        }
        updateList(controller.getAllIndexcardBoxes());

    }
    /**
     * Checks if the user has selected an indexcard if so
     * it opens the dialog edit function for the selected indexcard
     * If not it opens a dialog to edit a indexcard
     */
    private void onEdit() {
            controller.updateIndexcardBox();
            updateList(controller.getAllIndexcardBoxes());
    }
    /**
     * Opens the dialog to create a new indexcard
     */
    private void onCreate() {
        controller.createIndexcardBox();
        updateList(controller.getAllIndexcardBoxes());
    }
    /**
     * Checks if the user has entered a search term
     * If so it searches for indexcards with the search term in the name
     * If not it displays all indexcards
     */
    private void onSearch(){
        //updateList(controller.searchIndexcard(textField1.getText()));
    }
    /**
     * returns the contentPane
     */
    public JPanel getIndexcardPane() {
        return contentPane;
    }
    /**
     * closes the dialog
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
