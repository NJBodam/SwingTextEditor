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
        JLabel nameLabel = new JLabel("Your Name");
        nameLabel.setBounds(40,20, 100,30);
        add(nameLabel);

        JTextField nameTextField = new JTextField();
        nameTextField.setBounds(140,20, 120,30);
        add(nameTextField);
    }
}
