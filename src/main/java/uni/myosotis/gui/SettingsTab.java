package uni.myosotis.gui;

import uni.myosotis.controller.Controller;

import javax.swing.*;
import java.awt.event.*;

public class SettingsTab extends JDialog {
    private final Controller controller;
    private final Language language;
    private JPanel contentPane;
    private JLabel languageLabel;
    private JComboBox languageComboBox;
    private JButton confirmButton;

    public SettingsTab(Controller controller, Language language) {
        this.controller = controller;
        this.language = language;
        setContentPane(contentPane);
        pack();
        setMinimumSize(getSize());
        languageComboBox.setModel(new DefaultComboBoxModel(language.getLanguages()));
        // Set the language
        languageLabel.setText(language.getName("language"));
        confirmButton.setText(language.getName("confirm"));
        // Add listeners
        languageComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onLanguageChange();
            }
        });
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onConfirm();
            }
        });

    }

    private void onConfirm() {
        controller.setLanguage(languageComboBox.getSelectedItem().toString());
    }

    private void onLanguageChange() {

    }
    public JPanel getSettingsPane(){
        return contentPane;
    }

}
