package uni.myosotis.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import uni.myosotis.controller.Controller;
import uni.myosotis.objects.IndexcardBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The dialog to configure the Learnsystem.
 *
 * @author Johannes Neugebauer
 * @author Pablo Santos
 * @author Kasim Shah
 * @author Aziz Tas
 * @author Omid Valipour
 */
public class LearnConfig extends JDialog {

    private final Controller controller;
    private final Language language;
    private JLabel sortLabel;
    private JList boxesList;
    private JLabel boxLabel;
    private JButton confirmButton;
    private JButton cancelButton;
    private JComboBox<String> sortComboBox;
    private JPanel contentPane;
    private JScrollPane boxesScrollPane;

    private final String selectedLearnSystemName;

    private final IndexcardBox indexcardBoxSelected;

    private final int numberOfBoxes;
    private String selectedBox;
    private String selectedSort;


    public LearnConfig(Controller controller, Language language, String selectedLearnSystemName, IndexcardBox indexcardBoxSelected, int numberOfBoxes) {
        this.controller = controller;
        this.language = language;
        this.selectedLearnSystemName = selectedLearnSystemName;
        this.indexcardBoxSelected = indexcardBoxSelected;
        this.numberOfBoxes = numberOfBoxes;
        this.selectedSort = language.getName("firstBox");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(confirmButton);
        setTitle(language.getName("learningConfiguration"));
        pack();
        setMinimumSize(getSize());
        setSize(800, 600);
        setComboBoxValues();
        setBoxesList();
        configOff();
        //Set Language
        sortLabel.setText(language.getName("sort"));
        boxLabel.setText(language.getName("box"));
        confirmButton.setText(language.getName("confirm"));
        cancelButton.setText(language.getName("cancel"));
        //Listeners
        confirmButton.addActionListener(e -> onOk());
        cancelButton.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
    }

    private void onOk() {
        // Verify if anything is selected and if so, start the learning
        if (sortComboBox.getSelectedIndex() != -1) {
            selectedSort = (String) sortComboBox.getSelectedItem();
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, language.getName("selectSortError"));
        }
        if (boxesList.getSelectedIndex() != -1) {
            selectedBox = boxesList.getSelectedValue().toString();
        } else {
            JOptionPane.showMessageDialog(null, language.getName("selectBoxError"));
        }
        if (sortComboBox.getSelectedIndex() != -1 && boxesList.getSelectedIndex() != -1) {
            String selectedSort = getSelectedSort();
            String selectedBox = getSelectedBox();
            controller.learnLeitnerSystem(selectedLearnSystemName, indexcardBoxSelected, numberOfBoxes, selectedSort, selectedBox);
            dispose();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void setBoxesList() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (int i = 1; i < 6; i++)
            listModel.addElement("Box %d".formatted(i));
        boxesList.setModel(listModel);
    }

    public void setComboBoxValues() {
        String[] sortingValues = new String[]{language.getName("alphabetical"), language.getName("random")};
        sortComboBox.setModel(new DefaultComboBoxModel(sortingValues));
    }


    /**
     * This function makes the button and the visible list visible
     */

    public void configAll() {
        sortComboBox.setVisible(true);
        sortLabel.setVisible(true);
        boxesList.setVisible(true);
        boxLabel.setVisible(true);
        boxesScrollPane.setVisible(true);
    }

    public void configSort() {
        sortComboBox.setVisible(true);
        sortLabel.setVisible(true);
        boxesList.setVisible(false);
        boxLabel.setVisible(false);
        boxesScrollPane.setVisible(false);
    }

    public void configBoxes() {
        sortComboBox.setVisible(false);
        sortLabel.setVisible(false);
        boxesList.setVisible(true);
        boxLabel.setVisible(true);
        boxesScrollPane.setVisible(true);
    }

    public void configOff() {
        sortComboBox.setVisible(false);
        sortLabel.setVisible(false);
        boxesList.setVisible(false);
        boxLabel.setVisible(false);
        boxesScrollPane.setVisible(false);
    }

    public String getSelectedBox() {
        return selectedBox;
    }

    public void setSelectedBox(String selectedBox) {
        this.selectedBox = selectedBox;
    }

    public String getSelectedSort() {
        return selectedSort;
    }

    public void setSelectedSort(String selectedSort) {
        this.selectedSort = selectedSort;
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
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        sortLabel = new JLabel();
        sortLabel.setText("Sortierung");
        panel1.add(sortLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        boxesScrollPane = new JScrollPane();
        panel1.add(boxesScrollPane, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        boxesList = new JList();
        boxesList.setSelectionMode(0);
        boxesScrollPane.setViewportView(boxesList);
        boxLabel = new JLabel();
        boxLabel.setText("Boxen");
        panel1.add(boxLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sortComboBox = new JComboBox();
        panel1.add(sortComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        confirmButton = new JButton();
        confirmButton.setText("Ok");
        panel2.add(confirmButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelButton = new JButton();
        cancelButton.setText("Abbrechen");
        panel2.add(cancelButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
