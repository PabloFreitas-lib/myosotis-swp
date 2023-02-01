package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Category;
import uni.myosotis.objects.IndexcardBox;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EditIndexcardBox extends JDialog {

    private Controller controller;
    private Language language;
    private JLabel nameLabel;
    private JScrollPane categoryScrollPane;
    private JButton confirmButton;
    private JButton cancelButton;
    private JPanel contentPane;
    private JComboBox indexcardboxNameComboBox;
    private JLabel categoryLabel;

    private JList<String> categoriesNamesList;
    private  Optional<IndexcardBox> selectedIndexcardBox;

    public EditIndexcardBox(Controller controller,Language language){
        this.controller = controller;
        this.language = language;
        setModal(true);
        setTitle(language.getName("editIndexcardBoxTitle"));
        getRootPane().setDefaultButton(confirmButton);
        setContentPane(contentPane);

        indexcardboxNameComboBox.setModel(new DefaultComboBoxModel<>(controller.getAllIndexcardBoxNames().toArray(new String[0])));
        // Set the language
        categoryLabel.setText(language.getName("category"));
        nameLabel.setText(language.getName("name"));
        confirmButton.setText(language.getName("confirm"));
        cancelButton.setText(language.getName("cancel"));

        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        //ActionListener for the ComboBox
        indexcardboxNameComboBox.addActionListener(e -> {
            selectedIndexcardBox = controller.getIndexcardBoxByName((String) indexcardboxNameComboBox.getSelectedItem());
            if(selectedIndexcardBox.isPresent()){
                List<String> selectedIndexcardBoxCategory = List.of(selectedIndexcardBox.get().getCategoryNameList());
                categoriesNamesList = new JList<>(controller.getCategoryNames());
                ArrayList<Integer> indices = new ArrayList<>();
                categoriesNamesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                for(int i = 0; i < selectedIndexcardBoxCategory.size(); i++) {
                    indices.add(List.of(controller.getCategoryNames()).indexOf(selectedIndexcardBoxCategory.get(i)));
                }
                int[] indicesArray = indices.stream().mapToInt(i->i).toArray();
                categoriesNamesList.setSelectedIndices(indicesArray);
                categoryScrollPane.setViewportView(categoriesNamesList);
            }
        });
    }

    public void onOK(){
        if(selectedIndexcardBox.isPresent()) {
                List<Category> selectedCategoryList = new ArrayList<>();
                for (int i = 0; i < categoriesNamesList.getSelectedValuesList().size(); i++){
                    selectedCategoryList.add(controller.getCategoryByName(categoriesNamesList.getSelectedValuesList().get(i)).get());
                }
                controller.editIndexcardBox(selectedIndexcardBox.get().getName(), selectedCategoryList);
            dispose();
        }
    }

    public void onCancel(){
        dispose();
    }
}
