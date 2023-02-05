package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.IndexcardBox;
import uni.myosotis.objects.LeitnerLearnSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Objects;

/**
 * The tab for the IndexcardBoxes.
 *
 * @author Johannes Neugebauer
 * @author Pablo Santos
 * @author Kasim Shah
 * @author Aziz Tas
 * @author Omid Valipour
 */
public class IndexcardBoxTab extends JDialog {
    private final Controller controller;
    private final Language language;

    private JPanel contentPane;
    private JButton searchButton;
    private JList<String> indexcardBoxList;
    private JTextField textField1;
    private JButton deleteButton;
    private JButton editButton;
    private JButton createButton;
    private JPanel middlePanel;
    private JButton learnButton;
    private JComboBox<String> learnSystemName;
    private JLabel searchForNameLabel;
    private JLabel indexcardBoxLabel;
    private JLabel learnSystemLabel;
    private JButton removeFilterButton;

    public IndexcardBoxTab(Controller controller, Language language) {
        this.controller = controller;
        this.language = language;
        setContentPane(contentPane);
        setTitle(language.getName("indexcardBoxTitle"));
        pack();
        setMinimumSize(getSize());
        setSize(800, 600);
        updateList(controller.getAllIndexcardBoxes());
        updateComboBox();
        // Set the language
        indexcardBoxLabel.setText(language.getName("indexcardBoxTitle"));
        searchForNameLabel.setText(language.getName("searchForName"));
        learnSystemLabel.setText(language.getName("learnSystem"));
        searchButton.setText(language.getName("search"));
        deleteButton.setText(language.getName("delete"));
        editButton.setText(language.getName("edit"));
        createButton.setText(language.getName("create"));
        learnButton.setText(language.getName("learn"));
        // Set the listeners
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        searchButton.addActionListener(e -> onSearch());
        createButton.addActionListener(e -> onCreate());
        editButton.addActionListener(e -> onEdit());
        deleteButton.addActionListener(e -> onDelete());
        learnButton.addActionListener(e -> onLearn());
    }

    /**
     * Updates the list of indexcards
     */
    private void onRemoveFilter() {
        updateList(controller.getAllIndexcardBoxes());
    }

    /**
     * This method updates the list of indexcards and displays the Names in the list.
     *
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
     * Display the lean window and should show the first Indexcard from the categories inside the
     * selected IndexcardBox.
     * For now just one Box can be learned at the time.
     */
    private void onLearn() {
        if (indexcardBoxList.getModel().getSize() == 0) {
            JOptionPane.showMessageDialog(this, language.getName("noIndexcardBoxExistsError"),
                    language.getName("noIndexcardBoxExists"), JOptionPane.INFORMATION_MESSAGE);
        } else if (indexcardBoxList.getSelectedValuesList().size() != 1) {
            JOptionPane.showMessageDialog(this, language.getName("selectOneIndexcardBoxMessage")
                    , language.getName("selectOneIndexcardBox"), JOptionPane.INFORMATION_MESSAGE);
        } else if (Objects.equals(Objects.requireNonNull(learnSystemName.getSelectedItem()).toString(), language.getName("random"))) {
            LearnConfig learnConfig = null;
            controller.learnRandomLearnSystem(learnSystemName.getSelectedItem().toString(), controller.getIndexcardBoxByName(indexcardBoxList.getSelectedValue()).get(), 1);
        } else if (Objects.equals(learnSystemName.getSelectedItem().toString(), language.getName("leitner"))) {
            String selectedLearnSystemName = learnSystemName.getSelectedItem().toString();
            IndexcardBox indexcardBoxSelected = controller.getIndexcardBoxByName(indexcardBoxList.getSelectedValue()).get();
            int numberOfBoxes = 5;
            LearnConfig learnConfig = new LearnConfig(controller, language, selectedLearnSystemName, indexcardBoxSelected, numberOfBoxes);
            if (controller.getLeitnerLearnSystemByName(indexcardBoxSelected.getName() + selectedLearnSystemName) == null) {
                learnConfig.configAll();
                learnConfig.setVisible(true);
            } else if (!controller.getLeitnerLearnSystemByName(indexcardBoxSelected.getName() + selectedLearnSystemName).getStarted()) {
                //else if(!controller.existsLearnsystem(controller.getIndexcardBoxByName(indexcardBoxList.getSelectedValue()).get().getName()+ learnSystemName.getSelectedItem().toString())){
                // Show just the boxes which can be selected to learn
                learnConfig.configAll();
                learnConfig.setVisible(true);
            } else if (controller.getLeitnerLearnSystemByName(indexcardBoxSelected.getName() + selectedLearnSystemName).getStarted()) {
                LeitnerLearnSystem learnSystem = controller.getLeitnerLearnSystemByName(indexcardBoxSelected.getName() + selectedLearnSystemName);
                controller.learnLeitnerSystem(selectedLearnSystemName, indexcardBoxSelected, numberOfBoxes, learnSystem.getSortType(), learnConfig.getSelectedBox());
            }

        } else {
            JOptionPane.showMessageDialog(this, language.getName("selectLearnSystemMessage"),
                    language.getName("selectLearnSystem"), JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Checks if the user has selected an indexcard if so it deletes it
     * If multiple indexcards are selected it deletes all of them
     * If not it opens a dialog to delete an indexcard
     */
    private void onDelete() {
        if (indexcardBoxList.getSelectedValue() != null) {
            for (Object indexcardBoxName : indexcardBoxList.getSelectedValuesList()) {
                if (controller.getIndexcardBoxByName(indexcardBoxName.toString()).isPresent()) {
                    controller.deleteIndexcardBox(controller.getIndexcardBoxByName(indexcardBoxName.toString()).get().getName());
                }
            }
        } else {
            controller.deleteIndexcardBox();
        }
        updateList(controller.getAllIndexcardBoxes());

    }

    /**
     * Checks if the user has selected an indexcard if so
     * it opens the dialog edit function for the selected Indexcard.
     * If not it opens a dialog to edit an Indexcard.
     */
    private void onEdit() {
        if (indexcardBoxList.getSelectedValue() != null) {
            if (indexcardBoxList.getSelectedValuesList().size() == 1) {
                String indexcardBoxName = indexcardBoxList.getSelectedValue();
                if (controller.getIndexcardBoxByName(indexcardBoxName).isPresent()) {
                    controller.editIndexcardBox(controller.getIndexcardBoxByName(indexcardBoxName).get().getName());
                }
            }
        } else {
            controller.editIndexcardBox();
        }
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
    private void onSearch() {
        updateList(controller.searchIndexcardBox(textField1.getText()));
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

    /**
     * Updates the ComboBox.
     */
    public void updateComboBox() {
        // Array of all Indexcardnames
        String[] learnSystemList = {language.getName("leitner"), language.getName("random")};
        learnSystemName.setModel(new DefaultComboBoxModel<>(learnSystemList));
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
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane = new JPanel();
        contentPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 3, new Insets(10, 10, 10, 10), -1, -1));
        panel1.add(contentPane, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        middlePanel = new JPanel();
        middlePanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(middlePanel, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 4, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        indexcardBoxList = new JList();
        middlePanel.add(indexcardBoxList, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        indexcardBoxLabel = new JLabel();
        indexcardBoxLabel.setText("Karteikästen");
        contentPane.add(indexcardBoxLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 4, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchButton = new JButton();
        searchButton.setText("Suchen");
        contentPane.add(searchButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        textField1.setText("");
        contentPane.add(textField1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        deleteButton = new JButton();
        deleteButton.setText("Entfernen");
        contentPane.add(deleteButton, new com.intellij.uiDesigner.core.GridConstraints(4, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editButton = new JButton();
        editButton.setText("Bearbeiten");
        contentPane.add(editButton, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        createButton = new JButton();
        createButton.setText("Erstellen");
        contentPane.add(createButton, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchForNameLabel = new JLabel();
        searchForNameLabel.setText("Suche nach Namen");
        contentPane.add(searchForNameLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        learnButton = new JButton();
        learnButton.setText("Lernen");
        contentPane.add(learnButton, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        learnSystemLabel = new JLabel();
        learnSystemLabel.setText("LearnSystem");
        contentPane.add(learnSystemLabel, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        learnSystemName = new JComboBox();
        contentPane.add(learnSystemName, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }
}
