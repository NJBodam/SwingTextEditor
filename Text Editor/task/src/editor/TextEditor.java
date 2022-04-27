package editor;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class TextEditor extends JFrame {
;
    final int WIDTH = 600;
    final int HEIGHT = 400;
    JTextArea textArea;
    Container appCont;
    JTextField filenameField;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem menuItem;

    public TextEditor() {
        super("Text Editor");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        appCont = getContentPane();
        textArea = new JTextArea();
        textArea.setName("TextArea");

        JScrollPane scrollableTextArea = new JScrollPane(textArea);
        scrollableTextArea.setName("ScrollPane");
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        appCont.add(scrollableTextArea, BorderLayout.CENTER);
        appCont.add(new JLabel(" "), BorderLayout.SOUTH);
        appCont.add(new JLabel("    "), BorderLayout.WEST);
        appCont.add(new JLabel("    "), BorderLayout.EAST);

        JPanel topBar = new JPanel();
        topBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        filenameField = new JTextField(30);
        filenameField.setName("FilenameField");

        JButton saveButton = new JButton("Save");
        saveButton.setName("SaveButton");

        JButton loadButton = new JButton("Load");
        loadButton.setName("LoadButton");

        topBar.add(filenameField);
        topBar.add(saveButton);
        topBar.add(loadButton);
        appCont.add(topBar, BorderLayout.NORTH);

        setVisible(true);

        saveButton.addActionListener(e -> {
            File file = new File(filenameField.getText());
            fileWriter(file, textArea.getText());
            System.err.println("File saved successfully");
        });

        loadButton.addActionListener(e -> {
            try {
                textArea.setText(readFileAsString(filenameField.getText()));
                System.err.println("File loaded successfully");
            } catch (IOException ex) {
                textArea.setText(null);
            }
        });

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        menu = new JMenu("File");
        menu.setName("MenuFile");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        menuItem = new JMenuItem("Load", KeyEvent.VK_L);
        menuItem.setName("MenuLoad");
        menuItem.addActionListener(loadButton.getActionListeners()[0]);
        menu.add(menuItem);

        menuItem = new JMenuItem("Save", KeyEvent.VK_S);
        menuItem.setName("MenuSave");
        menuItem.addActionListener(saveButton.getActionListeners()[0]);
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Exit", KeyEvent.VK_E);
        menuItem.setName("MenuExit");
        menuItem.addActionListener(actionEvent -> dispose());
        menu.add(menuItem);

        menuBar.add(menu);

    }

    private void fileWriter(File file, String textArea) {
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.print(textArea);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String readFileAsString(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    public static void setMargin(JComponent aComponent, int aTop,
                                 int aRight, int aBottom, int aLeft) {

        Border border = aComponent.getBorder();

        Border marginBorder = new EmptyBorder(new Insets(aTop, aLeft,
                aBottom, aRight));
        aComponent.setBorder(border == null ? marginBorder
                : new CompoundBorder(marginBorder, border));
    }
}


/*

package editor;

        import javax.swing.*;
        import javax.swing.border.Border;
        import javax.swing.border.CompoundBorder;
        import javax.swing.text.JTextComponent;
        import java.awt.*;
        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.IOException;
        import java.io.PrintWriter;
        import java.nio.file.Files;
        import java.nio.file.Paths;
        import java.util.Scanner;

public class TextEditor extends JFrame {

    private static final Color SELECTION_COLOR = new Color(0x726825);

    File filenameField = new File("textEditorFile.txt"); // some file
    FlowLayout flowLayout = new FlowLayout();
    JButton saveButton = new JButton("SaveButton");
    JButton loadButton = new JButton("LoadButton");
    JTextField title = new JTextField("doc.txt", 12);
    JTextArea text = new JTextArea(20, 30);

    private static JTextComponent textArea;

    public TextEditor() {
        super("Text Editor");
    }

    private void fileWriter(File file, String textArea) {
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.print(textArea);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void fileReader(File file) {
        try (Scanner sc = new Scanner(file)) {
            if(sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String readFileAsString(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    static void createShowGUI() {
        // Create and set up the window.

        TextEditor frame = new TextEditor();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.
        frame.initComponents(frame.getContentPane());
        //Display the window.
        frame.setSize(500, 500);
        frame.pack();
        frame.setVisible(true);

        textArea.setName("TextArea");
        textArea.setOpaque(false);
        Insets margin = textArea.getMargin();
        if (margin == null) {
            margin = new Insets(5, 5, 5, 5);
        }
        Border marginBorder = BorderFactory.createEmptyBorder(
                margin.top + 3, margin.left, margin.bottom + 3,
                margin.right);
        CompoundBorder border = new CompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE), marginBorder);

        textArea.setBorder(border);
        textArea.setBackground(Color.BLUE);
        textArea.setForeground(Color.WHITE);
        textArea.setSelectionColor(SELECTION_COLOR);
        // textArea.add(frame);

    }

    private void initComponents(final Container pane) {
        final JPanel comps = new JPanel();
        comps.setLayout(new FlowLayout());

        JPanel controls = new JPanel();
        controls.setLayout(new BorderLayout());

        JPanel txtPanel = new JPanel();
        txtPanel.setLayout(new FlowLayout());

        comps.add(saveButton, BorderLayout.EAST);
        comps.add(loadButton, BorderLayout.WEST);
        comps.setLayout(new FlowLayout());

        controls.add(title, BorderLayout.LINE_START);
        controls.add(comps, BorderLayout.LINE_END);

        JScrollPane scrollPane = new JScrollPane(text);
        add(scrollPane);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        pane.add(controls, BorderLayout.CENTER);
        pane.add(scrollPane, BorderLayout.SOUTH);



        saveButton.addActionListener(e -> {
            text.setName(title.getText());
            String textBody = text.getText();
            if (title.getText() != null && textBody.trim().length() > 0) {
                System.err.println("it got here");
                fileWriter(filenameField, textBody);
                System.err.println("File saved successfully");
            }
        });

        loadButton.addActionListener(e -> {
            String body;
            try {
                body = readFileAsString(filenameField.getPath());
                title.setText(filenameField.getName());
                text.setText(body);
                System.err.println("File loaded successfully");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }
}
*/
