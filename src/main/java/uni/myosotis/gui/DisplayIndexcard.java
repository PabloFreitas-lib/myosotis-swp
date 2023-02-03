package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Indexcard;
import uni.myosotis.objects.Link;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DisplayIndexcard extends JFrame {

    /**
     * The controller.
     */
    private final Controller controller;

    private final Indexcard indexcard;
    private JPanel contentPane;

    private Language language;
    private JTextArea questionArea;
    private JTextArea answerArea;
    private JList<String> linkedIndexcardsList;
    private JLabel linkedListLabel;

    /**
     * Creates a new Window to display an Indexcard
     */
    public DisplayIndexcard(Controller controller, Indexcard indexcard, Language language) {
        this.controller = controller;
        this.indexcard = indexcard;
        this.language = language;
        setTitle(indexcard.getName());
        setContentPane(contentPane);
        linkedListLabel.setText(language.getName("linkedIndexcardsList")); // TODO
        Font font = new Font("Arial", Font.PLAIN, 20);
        questionArea.setFont(font);
        answerArea.setFont(font);
        this.questionArea.setText(indexcard.getQuestion());
        this.answerArea.setText(indexcard.getAnswer());

        DefaultListModel<String> linkedListModel = new DefaultListModel<>();
        linkedListModel.addAll(indexcard.getLinks().stream().map(Link::getIndexcard).map(Indexcard::getName).toList());
        linkedIndexcardsList.setModel(linkedListModel);

        linkedIndexcardsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    if (controller.getIndexcardByName(linkedIndexcardsList.getSelectedValue()).isPresent()) {
                        controller.displayIndexcard(controller.getIndexcardByName(linkedIndexcardsList.getSelectedValue()).get());
                    }
                }
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * Close the Window.
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
