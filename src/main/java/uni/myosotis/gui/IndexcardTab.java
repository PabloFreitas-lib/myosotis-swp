package uni.myosotis.gui;
import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Indexcard;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class IndexcardTab extends JDialog{
    private final Controller controller;
    private JPanel contentPane;
    private JLabel KarteikartenLabel;
    private JButton searchButton;
    private JList indexcardList;
    private JTextField textField1;
    private JButton deleteButton;
    private JButton editButton;
    private JButton createButton;
    private JPanel middlePanel;
    private JButton removeFilterButton;

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
        removeFilterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onRemoveFilter();
            }
        });
    }

    private void onRemoveFilter() {
        updateList(controller.getAllIndexcards());
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
            updateList(controller.getAllIndexcards());
        }
        else {
            controller.deleteIndexcard();
        }
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
    }

    private void onCreate() {
        controller.createIndexcard();
        updateList(controller.getAllIndexcards());
    }

    private void onSearch(){
        updateList(controller.searchIndexcard(textField1.getText()));
    }

    public JPanel getIndexcardPane() {
        return contentPane;
    }
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
