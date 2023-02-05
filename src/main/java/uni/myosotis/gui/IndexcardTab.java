package uni.myosotis.gui;
import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Indexcard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * The tab for the Indexcards.
 *
 * @author Johannes Neugebauer
 * @author Pablo Santos
 * @author Kasim Shah
 * @author Aziz Tas
 * @author Omid Valipour
 */
public class IndexcardTab extends JDialog {
    private final Controller controller;
    private final Language language;
    private JPanel contentPane;
    private JButton searchButton;
    private JList<String> indexcardList;
    private JTextField searchField;
    private JButton deleteButton;
    private JButton editButton;
    private JButton createButton;
    private JLabel indexcardLabel;

    public IndexcardTab(Controller controller, Language language) {
        this.controller = controller;
        this.language = language;
        setContentPane(contentPane);
        pack();
        setMinimumSize(getSize());
        updateList(controller.getAllIndexcards());
        // Set the language
        indexcardLabel.setText(language.getName("indexcard"));
        searchButton.setText(language.getName("search"));
        deleteButton.setText(language.getName("delete"));
        editButton.setText(language.getName("edit"));
        createButton.setText(language.getName("create"));
        // Add listeners
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        searchButton.addActionListener(e -> onSearch());
        createButton.addActionListener(e -> onCreate());
        editButton.addActionListener(e -> onEdit());
        deleteButton.addActionListener(e -> onDelete());
        // Display an Indexcard, if it gets double-clicked.
        indexcardList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    if (controller.getIndexcardByName(indexcardList.getSelectedValue()).isPresent()) {
                        controller.displayIndexcard(controller.getIndexcardByName(indexcardList.getSelectedValue()).get());
                    }
                }
            }
        });
    }

    /**
     * This method updates the list of indexcards and displays the Names in the list.
     *
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
     * Checks if the user has selected an indexcard if so it deletes it.
     * If multiple indexcards are selected it deletes all of them.
     * If not it opens a dialog to delete an indexcard.
     */
    private void onDelete() {
        if (indexcardList.getSelectedValue() != null) {
            for (Object indexcardName : indexcardList.getSelectedValuesList()) {
                if (controller.getIndexcardByName(indexcardName.toString()).isPresent()) {
                    controller.deleteIndexcard(controller.getIndexcardByName(indexcardName.toString()).get().getId());
                }
            }
        } else {
            // Pop-out
            controller.deleteIndexcard();
        }
        updateList(controller.getAllIndexcards());
    }

    /**
     * Checks if the user has selected an indexcard if so.
     * it opens the dialog edit function for the selected indexcard.
     * If not it opens a dialog to edit an indexcard.
     */
    private void onEdit() {
        if (indexcardList.getSelectedValue() != null) {
            if (controller.getIndexcardByName(indexcardList.getSelectedValue()).isPresent()) {
                controller.editIndexcard(controller.getIndexcardByName(indexcardList.getSelectedValue()).get());
            }
        } else {
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
    private void onSearch() {
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 2, new Insets(10, 10, 10, 10), -1, -1));
        searchField = new JTextField();
        contentPane.add(searchField, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        indexcardList = new JList();
        panel1.add(indexcardList, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        createButton = new JButton();
        createButton.setText("Erstellen");
        contentPane.add(createButton, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editButton = new JButton();
        editButton.setText("Bearbeiten");
        contentPane.add(editButton, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteButton = new JButton();
        deleteButton.setText("Entfernen");
        contentPane.add(deleteButton, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        indexcardLabel = new JLabel();
        indexcardLabel.setHorizontalAlignment(0);
        indexcardLabel.setHorizontalTextPosition(0);
        indexcardLabel.setText("Karteikarten");
        contentPane.add(indexcardLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchButton = new JButton();
        searchButton.setText("Suchen");
        contentPane.add(searchButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
