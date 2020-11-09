package editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;

public class TextEditor extends JFrame {

    private final JButton saveButton;
    private final JButton loadButton;
    private final JButton searchButton;
    private final JButton backArrow;
    private final JButton forwardArrow;
    private final JTextField textField;
    private final JTextArea textArea;

    public TextEditor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 620);
        setTitle("TextArea");
        getContentPane().setBackground(Color.GRAY);

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(800, 70));
        topPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setHgap(20);
        flowLayout.setVgap(20);
        topPanel.setLayout(flowLayout);

        //creates ImageIcon and resizes it to fit JButton
        ImageIcon saveIcon = new ImageIcon("save-icon.png");
        Image imgS = saveIcon.getImage();
        Image newSaveImg = imgS.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        saveIcon = new ImageIcon(newSaveImg);

        saveButton = new JButton(saveIcon);
        saveButton.setName("SaveButton");
        //saveButton.setText("Save");
        saveButton.setPreferredSize(new Dimension(45, 45));
        topPanel.add(saveButton);

        //saveFile();

        //creates ImageIcon and resizes it to fit JButton
        ImageIcon loadIcon = new ImageIcon("open-icon.png");
        Image imgL = loadIcon.getImage();
        Image newLoadImg = imgL.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        loadIcon = new ImageIcon(newLoadImg);

        loadButton = new JButton(loadIcon);
        loadButton.setName("OpenButton");
        //loadButton.setText("Load");
        loadButton.setPreferredSize(new Dimension(45, 45));
        topPanel.add(loadButton);

        //loadFile();

        textField = new JTextField();
        textField.setName("SearchField");
        textField.setPreferredSize(new Dimension(200, 45));
        textField.setFont(new Font("Arial", Font.PLAIN, 20));
        topPanel.add(textField);

        //creates ImageIcon and resizes it to fit JButton
        ImageIcon searchIcon = new ImageIcon("search-icon.png");
        Image imgSr = searchIcon.getImage();
        Image newSearchImg = imgSr.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        searchIcon = new ImageIcon(newSearchImg);

        searchButton = new JButton(searchIcon);
        searchButton.setName("StartSearchButton");
        searchButton.setPreferredSize(new Dimension(45, 45));
        topPanel.add(searchButton);

        backArrow = new JButton();
        backArrow.setName("PreviousMatchButton");
        backArrow.setText("<");
        backArrow.setFont(new Font("Arial", Font.BOLD, 30));
        topPanel.add(backArrow);

        forwardArrow = new JButton();
        forwardArrow.setName("NextMatchButton");
        forwardArrow.setText(">");
        forwardArrow.setFont(new Font("Arial", Font.BOLD, 30));
        topPanel.add(forwardArrow);

        JPanel textAreaPanel = new JPanel();
        FlowLayout flowLayout1 = new FlowLayout();
        flowLayout1.setVgap(30);
        textAreaPanel.setLayout(flowLayout1);
        textAreaPanel.setPreferredSize(new Dimension(800, 530));

        textArea = new JTextArea();
        textArea.setName("TextArea");
        textArea.setFont(new Font("Arial", Font.ITALIC, 20));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setName("ScrollPane");
        scrollPane.setPreferredSize(new Dimension(700, 400));
        scrollPane.setBorder(BorderFactory.createBevelBorder(Color.OPAQUE));
        textAreaPanel.add(scrollPane);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(800, 30));
        menuBar.setBackground(Color.CYAN);
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.setFont(new Font("Arial", Font.BOLD, 25));

        JMenuItem loadMenuItem = new JMenuItem("Open");
        loadMenuItem.setName("MenuOpen");
        loadMenuItem.setFont(new Font("Arial", Font.PLAIN, 20));
        fileMenu.add(loadMenuItem);
        loadMenuItem.addActionListener(actionEvent -> {
            loadButton.doClick();
        });

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        saveMenuItem.setFont(new Font("Arial", Font.PLAIN, 20));
        fileMenu.add(saveMenuItem);
        saveMenuItem.addActionListener(actionEvent -> {
            saveButton.doClick();
        });

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");
        exitMenuItem.setFont(new Font("Arial", Font.BOLD, 20));
        fileMenu.addSeparator(); // adds line separator between save and exit
        fileMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(actionEvent -> {
            System.exit(0);
        });

        JMenu searchMenu = new JMenu("Search");
        searchMenu.setName("MenuSearch");
        searchMenu.setMnemonic(KeyEvent.VK_S);
        searchMenu.setFont(new Font("Arial", Font.BOLD, 25));

        JMenuItem startSearchItem = new JMenuItem("Start search");
        startSearchItem.setName("MenuStartSearch");
        startSearchItem.setFont(new Font("Arial", Font.PLAIN, 20));
        searchMenu.add(startSearchItem);
        startSearchItem.addActionListener(actionEvent -> {
            searchButton.doClick();
        });

        JMenuItem previousSearchItem = new JMenuItem("Previous search");
        previousSearchItem.setName("MenuPreviousMatch");
        previousSearchItem.setFont(new Font("Arial", Font.PLAIN, 20));
        searchMenu.add(previousSearchItem);
        previousSearchItem.addActionListener(actionEvent -> {
            backArrow.doClick();
        });

        JMenuItem nextMatchItem = new JMenuItem("Next match");
        nextMatchItem.setName("MenuNextMatch");
        nextMatchItem.setFont(new Font("Arial", Font.PLAIN, 20));
        searchMenu.add(nextMatchItem);
        nextMatchItem.addActionListener(actionEvent -> {
            forwardArrow.doClick();
        });

        menuBar.add(fileMenu);
        menuBar.add(searchMenu);
        add(topPanel, BorderLayout.NORTH);
        add(textAreaPanel, BorderLayout.CENTER);

        setVisible(true);

    }

    /*private void saveFile() {
        if (textField.getText() == null) {
            throw new IllegalArgumentException("filename cannot be null");
        }
        saveButton.addActionListener(actionEvent -> {
            String fileName = textField.getText();
            if (fileName != null) {
                try (FileWriter fileWriter = new FileWriter(fileName);
                     BufferedWriter bw = new BufferedWriter(fileWriter)) {
                    textArea.write(bw);
                    textArea.requestFocusInWindow();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadFile() {
        if (textField.getText() == null) {
            throw new IllegalArgumentException("filename cannot be null");
        }

        loadButton.addActionListener(actionEvent -> {
            String fileName = textField.getText();
            File file = new File(fileName);

            if (!file.exists()) {
                textArea.setText("");
                return;
            }

            try (FileReader fileReader = new FileReader(fileName);
                 BufferedReader br = new BufferedReader(fileReader)) {
                textArea.read(br, null);
                textArea.requestFocusInWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
*/
}
