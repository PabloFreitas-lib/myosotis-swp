package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.LeitnerLearnSystem;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LearnConfig  extends JDialog{

    private Controller controller;
    private JLabel parentLabel;
    private JList boxesList;
    private JLabel indexcardLabel;
    private JButton createButton;
    private JButton cancelButton;
    private JComboBox comboBox;
    private JPanel contentPane;


    public LearnConfig(Controller controller) {
        this.controller = controller;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(createButton);
        setTitle("LearnConfig");
        pack();
        setMinimumSize(getSize());
        setSize(800, 600);
        setComboBoxValues();
        setBoxesList();
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
        // add your code here
        dispose();
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
        String[] sortingValues = new String[]{"Alphabetisch", "Zufällig"};
        comboBox.setModel(new DefaultComboBoxModel(sortingValues));
    }
}
