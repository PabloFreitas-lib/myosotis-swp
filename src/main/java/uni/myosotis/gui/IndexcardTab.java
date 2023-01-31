package uni.myosotis.gui;
import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Indexcard;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class IndexcardTab extends JDialog{
    private final Controller controller;
    private JPanel contentPane;
    private JButton searchButton;
    private JList indexcardList;
    private JTextField searchField;
    private JButton deleteButton;
    private JButton editButton;
    private JButton createButton;

    public IndexcardTab(Controller controller) {
        this.controller = controller;
        setContentPane(contentPane);
        setTitle("Karteikarten");
        pack();
        setMinimumSize(getSize());
        setSize(800, 600);
        updateList(controller.getAllIndexcards());
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
        // Display an Indexcard, if it gets double-clicked.
        indexcardList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    if (controller.getIndexcardByName((String) indexcardList.getSelectedValue()).isPresent()) {
                        controller.displayIndexcard(controller.getIndexcardByName((String) indexcardList.getSelectedValue()).get());
                    }
                }
            }
        });
    }

    /**
     * This method updates the list of indexcards and displays the Names in the list.
     * @param indexcardList the list of indexcards which should be displayed
     */
    private void updateList(List<Indexcard> indexcardList) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Indexcard indexcard : indexcardList) {
            listModel.addElement(indexcard.getName());
        }
        this.indexcardList.setModel(listModel);
    }

    /**
     * Checks if the user has selected an indexcard if so it deletes it
     * If multiple indexcards are selected it deletes all of them
     * If not it opens a dialog to delete a indexcard
     */
    private void onDelete() {
        if (indexcardList.getSelectedValue() != null) {
            for (Object indexcardName : indexcardList.getSelectedValuesList()) {
                controller.deleteIndexcard(controller.getIndexcardByName(indexcardName.toString()).get().getId());
            }
        }
        else {
            // Pop-out
            controller.deleteIndexcard();
        }
        updateList(controller.getAllIndexcards());
    }

    /**
     * Checks if the user has selected an indexcard if so
     * it opens the dialog edit function for the selected indexcard
     * If not it opens a dialog to edit a indexcard
     */
    private void onEdit() {
        if (indexcardList.getSelectedValue() != null) {
            controller.editIndexcard(controller.getIndexcardByName(indexcardList.getSelectedValue().toString()).get());
        }
        else {
            controller.editIndexcard();
        }
        updateList(controller.getAllIndexcards());
    }
    /**
     * Opens the dialog to create a new indexcard
     */
    private void onCreate() {
        controller.createIndexcard();
        updateList(controller.getAllIndexcards());
    }
    /**
     * Checks if the user has entered a search term
     * If so it searches for indexcards with the search term in the name
     * If not it displays all indexcards
     */
    private void onSearch(){
        updateList(controller.searchIndexcard(searchField.getText()));
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
