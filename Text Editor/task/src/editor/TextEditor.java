package editor;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextEditor extends JFrame {


    final String MAIN_DIR = "/Users/mac/Desktop/BetterReads/Text Editor/Text Editor/task/src/images/";
    final String SAVE_ICON = "Save-icon.png";
    final String LOAD_ICON = "load1.png";
    final String SEARCH_ICON = "search-icon.png";
    final String PREV_ICON = "Button-Previous-icon.png";
    final String NEXT_ICON = "Button-Next-icon.png";
    private int iterator = 0;

    final int WIDTH = 620;
    final int HEIGHT = 430;

    private volatile boolean flag;
    private final JTextArea textArea;
    private JTextField filenameField;
    private JButton saveButton;
    private JButton loadButton;
    private JButton searchButton;
    private JButton prevButton;
    private JButton nextButton;
    private JCheckBox regex;
    private final JFileChooser jfc;
    private LinkedList<Integer[]> matchIndex;
    private String filePath;



    public TextEditor() {
        super("Text Editor");
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Container appCont = getContentPane();
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
        appCont.add(topBar(), BorderLayout.NORTH);

        setVisible(true);

        actionListener(saveButton, loadButton, searchButton, prevButton, nextButton, regex);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setName("FileChooser");
        jfc.setDialogTitle("Select directory to save file: ");
        add(jfc);


        JMenu menu = new JMenu("File");
        menu.setName("MenuFile");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        JMenuItem menuItem = new JMenuItem("Open", KeyEvent.VK_O);
        menuItem.setName("MenuOpen");
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

        menu = new JMenu("Search");
        menu.setName("MenuSearch");
        menuBar.add(menu);

        menuItem = new JMenuItem("Start search", KeyEvent.VK_L);
        menuItem.setName("MenuStartSearch");
        menuItem.addActionListener(searchButton.getActionListeners()[0]);
        menu.add(menuItem);

        menuItem = new JMenuItem("Previous search", KeyEvent.VK_P);
        menuItem.setName("MenuPreviousMatch");
        menuItem.addActionListener(prevButton.getActionListeners()[0]);
        menu.add(menuItem);

        menuItem = new JMenuItem("Next match");
        menuItem.setName("MenuNextMatch");
        menuItem.addActionListener(nextButton.getActionListeners()[0]);
        menu.add(menuItem);

        menuItem = new JMenuItem("Use regular expression", KeyEvent.VK_R);
        menuItem.setName("MenuUseRegExp");
        menuItem.addActionListener(searchButton.getActionListeners()[0]);
        menuItem.addActionListener(e -> regex.setSelected(true));
        menu.add(menuItem);

    }

    public JPanel topBar() {
        JPanel topBar = new JPanel();
        topBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        filenameField = new JTextField(15);
        filenameField.setName("SearchField");

        ImageIcon saveIcon = resizeImage(MAIN_DIR + SAVE_ICON);
        saveButton = new JButton(saveIcon);
        saveButton.setName("SaveButton");

        ImageIcon loadIcon = resizeImage(MAIN_DIR + LOAD_ICON);
        loadButton = new JButton(loadIcon);
        loadButton.setName("OpenButton");

        ImageIcon searchIcon = resizeImage(MAIN_DIR + SEARCH_ICON);
        searchButton = new JButton(searchIcon);
        searchButton.setName("StartSearchButton");

        ImageIcon prevIcon = resizeImage(MAIN_DIR + NEXT_ICON);
        prevButton = new JButton(prevIcon);
        prevButton.setName("PreviousMatchButton");

        ImageIcon nextIcon = resizeImage(MAIN_DIR + PREV_ICON);
        nextButton = new JButton(nextIcon);
        nextButton.setName("NextMatchButton");

        regex = new JCheckBox("Use regular expression");
        regex.setName("UseRegExCheckbox");


        topBar.add(loadButton);
        topBar.add(saveButton);
        topBar.add(filenameField);
        topBar.add(searchButton);
        topBar.add(prevButton);
        topBar.add(nextButton);
        topBar.add(prevButton);
        topBar.add(regex);
        return topBar;
    }

    private void actionListener(JButton saveButton, JButton loadButton, JButton searchButton,
                                JButton prevButton, JButton nextButton, JCheckBox regex) {

        saveButton.addActionListener(e -> {
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                if(jfc.getSelectedFile().isDirectory()) {
                    if(filenameField.getText().equals("")) {
                        filePath = jfc.getSelectedFile().getAbsolutePath() + "/newFile.txt";
                    } else {
                        filePath = jfc.getSelectedFile().getAbsolutePath() + filenameField.getText() + ".txt";
                    }
                }
            }
            File file = new File(filePath);
            fileWriter(file, textArea.getText());
        });

        searchButton.addActionListener(e -> new Thread(() -> {
            flag = false;
            searchText(regex);
        }).start());

        loadButton.addActionListener(e -> {
            try {
                int returnValue = jfc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    textArea.setText(readFileAsString(jfc.getSelectedFile().getAbsolutePath()));
                    textArea.requestFocus();
                }
            } catch (IOException ex) {
                textArea.setText(null);
                ex.printStackTrace();
            }
        });

        nextButton.addActionListener(e -> {
            Integer[] index = next();
            if (index != null) {
                textArea.setCaretPosition(index[1]);
                textArea.select(index[0], index[1]);
                textArea.grabFocus();
            }
        });

        prevButton.addActionListener(e -> {
            Integer[] index = prev();
            if (index != null) {
                textArea.setCaretPosition(index[1]);
                textArea.select(index[0], index[1]);
                textArea.grabFocus();
            }
        });

    }

    public Integer[] next() {
        return matchIndex.size() == 0 ? null : matchIndex.get(iterator = (iterator + 1) % matchIndex.size());
    }

    public Integer[] prev() {
        return matchIndex.size() == 0 ? null : matchIndex.get(iterator = (matchIndex.size() + iterator - 1) % matchIndex.size());
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

    public void searchText(JCheckBox regex) {
        new Thread(() -> {
            String searchText = textArea.getText();
            String query = filenameField.getText();
            int index = 0;
            matchIndex = new LinkedList<>();

            if(regex.isSelected()) {
                Pattern pattern = Pattern.compile(query);
                Matcher matcher = pattern.matcher(searchText);
                while (matcher.find()) {
                    matchIndex.add(new Integer[]{matcher.start(), matcher.end()});
                    flag = true;
                }
            } else {
                if(searchText.contains(query)) {
                    while (searchText.indexOf(query, index) >= 0) {
                        index = searchText.indexOf(query, index);
                        matchIndex.add(new Integer[]{index, index + query.length()});
                        index = index + query.length();
                        flag = true;
                    }
                }
            }
        }).start();

        new Thread(() -> {
            while (!flag) Thread.onSpinWait();
            iterator = 0;
            if (matchIndex.size() > 0) {
                Integer[] tempIndex = matchIndex.get(iterator);
                textArea.setCaretPosition(tempIndex[1]);
                textArea.select(tempIndex[0], tempIndex[1]);
                textArea.grabFocus();
            }
        }).start();
    }

//    public static void setMargin(JComponent aComponent, int aTop,
//                                 int aRight, int aBottom, int aLeft) {
//
//        Border border = aComponent.getBorder();
//
//        Border marginBorder = new EmptyBorder(new Insets(aTop, aLeft,
//                aBottom, aRight));
//        aComponent.setBorder(border == null ? marginBorder
//                : new CompoundBorder(marginBorder, border));
//    }

    private ImageIcon resizeImage(String file) {
        return new ImageIcon(new ImageIcon(file).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));

    }
}
