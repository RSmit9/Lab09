import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TextFileSearchApp extends JFrame 
    {
        private JTextArea textArea;
        private JTextField searchField;

public TextFileSearchApp() 
        {
            super("Key Word Searching Tool");
    
            // Create components
            textArea = new JTextArea(50, 150);
            JScrollPane scrollPane = new JScrollPane(textArea);
            searchField = new JTextField(20);
            JButton searchButton = new JButton("Search For word or phrase");
            JButton browseButton = new JButton("Select File");
            
            browseButton.addActionListener(e -> browseFile());
            searchButton.addActionListener(e -> searchFile());

            JPanel topPanel = new JPanel();
            topPanel.add(new JLabel("Search String:"));
            topPanel.add(searchField);
            topPanel.add(browseButton);
            topPanel.add(searchButton);
    
            Container contentPane = getContentPane();
            contentPane.setLayout(new BorderLayout());
            contentPane.add(topPanel, BorderLayout.NORTH);
            contentPane.add(scrollPane, BorderLayout.CENTER);
            
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pack();
            setLocationRelativeTo(null); // Center the window
            setVisible(true);
        }

private void browseFile() 
    {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) 
            {
                File selectedFile = fileChooser.getSelectedFile();
                if (selectedFile.isFile()) 
                    {
                        try 
                            {
                                    BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                                    StringBuilder fileContent = new StringBuilder();
                                    String line;
                                    while ((line = reader.readLine()) != null) 
                                {
                                    fileContent.append(line).append("\n");
                                }
                            reader.close();
                            textArea.setText(fileContent.toString());
                    } catch (IOException e) 
                        {
                        JOptionPane.showMessageDialog(this, "Encountered problem reading file: " + e.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else 
                {
                    JOptionPane.showMessageDialog(this, "Invalid File Please Select A New One", "Error", JOptionPane.ERROR_MESSAGE);
                }
        }
    }

    private void searchFile() 
        {
            String searchText = searchField.getText();
            String text = textArea.getText();
            List<String> matchedLines = text.lines()
                    .filter(line -> line.contains(searchText))
                    .collect(Collectors.toList());
        if (!matchedLines.isEmpty()) 
            {
            textArea.setText(String.join("\n", matchedLines));
        } else {
            JOptionPane.showMessageDialog(this, "No matches found", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TextFileSearchApp::new);
    }
}
