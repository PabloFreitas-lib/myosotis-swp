package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Indexcard;
import uni.myosotis.objects.Link;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.*;
import java.util.List;
import java.util.Objects;

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
     * Creates a new Window to display an Indexcard.
     *
     * @param controller The Controller.
     * @param indexcard The Indexcard that should be displayed.
     * @param language The selected language.
     */
    public DisplayIndexcard(Controller controller, Indexcard indexcard, Language language) {
        this.controller = controller;
        this.indexcard = indexcard;
        this.language = language;
        setTitle(indexcard.getName());
        setContentPane(contentPane);
        linkedListLabel.setText(language.getName("linkedIndexcardsList"));
        Font font = new Font("Arial", Font.PLAIN, 20);
        Font highlight = new Font("Serif", Font.PLAIN, 14);
        questionArea.setFont(font);
        answerArea.setFont(font);
        this.questionArea.setText(indexcard.getQuestion());

        questionArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int offset = questionArea.viewToModel2D(e.getPoint());
                try {
                    int start = Utilities.getWordStart(questionArea, offset);
                    int end = Utilities.getWordEnd(questionArea, offset);
                    String word = questionArea.getText().substring(start, end);
                    onWordClicked(word);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        });
        this.answerArea.setText(indexcard.getAnswer());

        answerArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int offset = answerArea.viewToModel2D(e.getPoint());
                try {
                    int start = Utilities.getWordStart(answerArea, offset);
                    int end = Utilities.getWordEnd(answerArea, offset);
                    String word = answerArea.getText().substring(start, end);
                    onWordClicked(word);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        });
        // Set Model
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

    private void onWordClicked(String word) {
        DefaultListModel<String> model = (DefaultListModel<String>) linkedIndexcardsList.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            String item = model.getElementAt(i);
            if (controller.getIndexcardByName(item).isPresent()) {
                Indexcard linkedCard = controller.getIndexcardByName(item).get();
                for (Link link : indexcard.getLinks()) {
                    if (link.getTerm().equals(word) && Objects.equals(link.getIndexcard().getId(), linkedCard.getId())) {
                        DisplayIndexcard displayIndexcard = new DisplayIndexcard(controller, linkedCard, language);
                        displayIndexcard.setSize(600, 400);
                        displayIndexcard.setMinimumSize(displayIndexcard.getSize());
                        displayIndexcard.setLocationRelativeTo(this);
                        displayIndexcard.setVisible(true);
                    }
                }
            }
        }
    }

    public void underlineWords(JTextArea textArea, List<String> wordsToUnderline) {
        DefaultStyledDocument doc = new DefaultStyledDocument(new StyleContext());
        try {
            doc.insertString(0, textArea.getText(), null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        for (String word : wordsToUnderline) {
            int start = 0;
            int end = textArea.getText().indexOf(word, start);
            while (end != -1) {
                underlineWord(doc, end, end + word.length());
                start = end + word.length();
                end = textArea.getText().indexOf(word, start);
            }
        }
        textArea.setDocument(doc);
    }

    private static void underlineWord(DefaultStyledDocument doc, int start, int end) {
        AttributeSet attrs = StyleContext.getDefaultStyleContext().addAttribute(
                SimpleAttributeSet.EMPTY, StyleConstants.Underline, true);
        doc.setCharacterAttributes(start, end - start, attrs, false);
    }

/**
 * Close the Window.
 */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}