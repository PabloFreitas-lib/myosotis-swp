package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.IndexcardBox;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LearnConfig  extends JDialog{

    private Controller controller;
    private Language language;
    private JLabel sortLabel;
    private JList boxesList;
    private JLabel boxesLabel;
    private JButton createButton;
    private JButton cancelButton;
    private JComboBox<String> sortComboBox;
    private JPanel contentPane;
    private JScrollPane boxesScrollPane;

    private String selectedLearnSystemName;

    private IndexcardBox indexcardBoxSelected;

    private int numberOfBoxes;
    private String selectedBox = "Box 1";
    private String selectedSort;


    public LearnConfig(Controller controller, Language language, String selectedLearnSystemName, IndexcardBox indexcardBoxSelected, int numberOfBoxes) {
        this.controller = controller;
        this.language = language;
        this.selectedLearnSystemName = selectedLearnSystemName;
        this.indexcardBoxSelected = indexcardBoxSelected;
        this.numberOfBoxes = numberOfBoxes;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(createButton);
        setTitle(language.getName("learningConfiguration"));
        pack();
        setMinimumSize(getSize());
        setSize(800, 600);
        setComboBoxValues();
        setBoxesList();
        configOff();

        createButton.addActionListener(e -> onOk());
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
            if(sortComboBox.getSelectedIndex() != -1){
                selectedSort = (String) sortComboBox.getSelectedItem();
                dispose();
            }
            else {
                JOptionPane.showMessageDialog(null, language.getName("selectSortError"));
            }
            if(boxesList.getSelectedIndex() != -1){
                selectedBox = boxesList.getSelectedValue().toString();
            }
            else {
                JOptionPane.showMessageDialog(null, language.getName("selectBoxError"));
            }
            if(sortComboBox.getSelectedIndex() != -1 && boxesList.getSelectedIndex() != -1){
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
        for(int i = 1; i < 6; i ++)
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

    public void configAll(){
        sortComboBox.setVisible(true);
        sortLabel.setVisible(true);
        boxesList.setVisible(true);
        boxesLabel.setVisible(true);
        boxesScrollPane.setVisible(true);
    }
    public void configSort(){
        sortComboBox.setVisible(true);
        sortLabel.setVisible(true);
        boxesList.setVisible(false);
        boxesLabel.setVisible(false);
        boxesScrollPane.setVisible(false);
    }

    public void configBoxes(){
        sortComboBox.setVisible(false);
        sortLabel.setVisible(false);
        boxesList.setVisible(true);
        boxesLabel.setVisible(true);
        boxesScrollPane.setVisible(true);
    }

    public void configOff(){
        sortComboBox.setVisible(false);
        sortLabel.setVisible(false);
        boxesList.setVisible(false);
        boxesLabel.setVisible(false);
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

}
