package editor;

import javax.swing.*;

public class TextEditor extends JFrame {
    public TextEditor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setVisible(true);
        setLayout(null);
        setTitle("The first stage");

        initComponents();
    }

    private void initComponents() {

        JTextArea textArea = new JTextArea();
        textArea.setBounds(20,20, 560,540);
        textArea.setName("TextArea");
        add(textArea);
    }
}
