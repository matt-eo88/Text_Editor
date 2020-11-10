package editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextEditor extends JFrame {

    private final JButton saveButton;
    private final JButton loadButton;
    private final JButton searchButton;
    private final JButton backArrow;
    private final JButton regex;
    private final JButton forwardArrow;
    private final JTextField textField;
    private final JTextArea textArea;
    private final JFileChooser fileChooser;
    private final ExecutorService executorService;
    private boolean isChecked = false;
    private ArrayList<Integer> indexFound;
    private ArrayList<Integer> lengthFound;
    private int counter = 0;
    private int nextCounter = 0;

    public TextEditor() {
        fileChooser = new JFileChooser();
        executorService = Executors.newSingleThreadExecutor();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 620);
        setTitle("TextArea");
        getContentPane().setBackground(Color.GRAY);

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(800, 70));
        topPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setHgap(15);
        flowLayout.setVgap(20);
        topPanel.setLayout(flowLayout);

        //creates ImageIcon and resizes it to fit JButton
        ImageIcon saveIcon = new ImageIcon("save-icon.png");
        Image imgS = saveIcon.getImage();
        Image newSaveImg = imgS.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        saveIcon = new ImageIcon(newSaveImg);

        saveButton = new JButton(saveIcon);
        saveButton.setName("SaveButton");
        saveButton.setPreferredSize(new Dimension(45, 45));
        topPanel.add(saveButton);

        //creates ImageIcon and resizes it to fit JButton
        ImageIcon loadIcon = new ImageIcon("open-icon.png");
        Image imgL = loadIcon.getImage();
        Image newLoadImg = imgL.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        loadIcon = new ImageIcon(newLoadImg);

        loadButton = new JButton(loadIcon);
        loadButton.setName("OpenButton");
        loadButton.setPreferredSize(new Dimension(45, 45));
        topPanel.add(loadButton);

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

        regex = new JButton("REGEX");
        regex.setFont(new Font("Arial", Font.BOLD, 30));
        topPanel.add(regex);

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

        JMenuItem regexItem = new JMenuItem("Use regular expressions");
        regexItem.setName("MenuUseRegExp");
        regexItem.setFont(new Font("Arial", Font.PLAIN, 20));
        searchMenu.add(regexItem);
        regexItem.addActionListener(actionEvent -> {
            regex.doClick();
        });

        menuBar.add(fileMenu);
        menuBar.add(searchMenu);
        add(topPanel, BorderLayout.NORTH);
        add(textAreaPanel, BorderLayout.CENTER);

        createListeners();

        setVisible(true);
    }

    private void createListeners() {
        chooserSaveFile();
        chooserLoadFile();
        searchEngine();
        prevSearch();
        nextSearch();
        isRegex();
    }

    private void chooserSaveFile() {
        saveButton.addActionListener(actionEvent -> {
            Runnable save = () -> {
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int response = fileChooser.showSaveDialog(null);

                if (response == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    try (FileWriter fileWriter = new FileWriter(file);
                         BufferedWriter bw = new BufferedWriter(fileWriter)) {
                        textArea.write(bw);
                        textArea.requestFocusInWindow();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            };
            executorService.submit(save);
        });
    }

    private void chooserLoadFile() {
        loadButton.addActionListener(actionEvent -> {
            Runnable load = () -> {
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int response = fileChooser.showOpenDialog(null);

                if (response == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    if (!file.exists()) {
                        textArea.setText("");
                        return;
                    }
                    if (file.isFile()) {
                        try (FileReader fileReader = new FileReader(file);
                             BufferedReader br = new BufferedReader(fileReader)) {
                            textArea.read(br, null);
                            textArea.requestFocusInWindow();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            executorService.submit(load);
        });
    }

    private void isRegex() {
        regex.addActionListener(actionEvent -> {
            isChecked = !isChecked;
            if (isChecked) {
                regex.setBackground(Color.GREEN);
            } else {
                regex.setBackground(Color.LIGHT_GRAY);
            }
        });
    }

    private void searchEngine() {
        searchButton.addActionListener(actionEvent -> {
            Runnable task = () -> {
                indexFound = new ArrayList<>();
                lengthFound = new ArrayList<>();
                String findText = textField.getText();
                String allText = textArea.getText();
                int index = -1;
                int lengthFind = findText.length();

                if (isChecked) {

                    Pattern pattern = Pattern.compile(findText);
                    Matcher matcher = pattern.matcher(allText);
                    while (matcher.find()) {
                        index = matcher.start();
                        lengthFind = matcher.end() - index;
                        indexFound.add(index);
                        lengthFound.add(lengthFind);
                    }

                } else {

                    while (true) {
                        index = allText.indexOf(findText, index + 1);
                        if (index == -1) {
                            break;
                        }
                        indexFound.add(index);
                        lengthFound.add(lengthFind);
                        //System.out.println("index=" + index);
                        //System.out.println("length=" + lengthFind);
                    }
                }
            };
            executorService.submit(task);
        });
    }

    private void prevSearch() {
        backArrow.addActionListener(actionEvent -> {
            Runnable task = () -> {
                if (counter > 0) {
                    if (nextCounter != 0) {
                        nextCounter--;
                    } else {
                        nextCounter = counter - 1;
                    }
                    textArea.setCaretPosition(indexFound.get(nextCounter) + lengthFound.get(nextCounter));
                    textArea.select(indexFound.get(nextCounter), indexFound.get(nextCounter) + lengthFound.get(nextCounter));
                    textArea.grabFocus();
                }
            };
            executorService.submit(task);
        });
    }

    private void nextSearch() {
        forwardArrow.addActionListener(actionEvent -> {
            Runnable task = () -> {
                if (counter > 0) {
                    if (counter - 1 > nextCounter) {
                        nextCounter++;
                    } else {
                        nextCounter = 0;
                    }
                    textArea.setCaretPosition(indexFound.get(nextCounter) + lengthFound.get(nextCounter));
                    textArea.select(indexFound.get(nextCounter), indexFound.get(nextCounter) + lengthFound.get(nextCounter));
                    textArea.grabFocus();
                }
            };
            executorService.submit(task);
        });
    }

}
