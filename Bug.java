package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class Bug {
    private JTextField idData;
    private JTable table1;
    private JButton ADDRECORDButton;
    private JButton UPDATERECORDButton;
    private JPanel bugPanel;
    private JTextArea description;
    private JComboBox<String> product;
    private JComboBox<String> environment;
    private JComboBox<String> type;
    private JComboBox<String> status;
    JFrame bugF = new JFrame();

    // Constructor
    public Bug() {
        // Initialize components
        idData = new JTextField(20);
        description = new JTextArea(5, 20);
        product = new JComboBox<>(new String[]{"Website Portal", "Mobile App", "Desktop Application"});
        environment = new JComboBox<>(new String[]{"Windows 10", "macOS Ventura", "Ubuntu 22.04"});
        type = new JComboBox<>(new String[]{"Bug", "Feature Request", "Performance Issue", "Security Vulnerability"});
        status = new JComboBox<>(new String[]{"Open", "Closed", "In Progress"});

        ADDRECORDButton = new JButton("Add Record");
        UPDATERECORDButton = new JButton("Update Record");
        table1 = new JTable();

        // Initialize the bugPanel and set custom layout, size, and style
        bugPanel = new JPanel(new GridBagLayout()); // Center components using GridBagLayout
        bugPanel.setPreferredSize(new Dimension(400, 400)); // Set preferred size (medium)

        // Set background color and border
        bugPanel.setBackground(new Color(220, 240, 255)); // Light blue background
        bugPanel.setBorder(BorderFactory.createTitledBorder("Bug Tracking System")); // Add a titled border

        // Set GridBagConstraints for component positioning
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; // To fill horizontally
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding around components

        // Add components to the panel in centered positions

JLabel bugIdLabel = new JLabel("Bug ID:");
bugIdLabel.setFont(new Font("Arial", Font.BOLD, 12));  // Set font to bold and size 12
bugIdLabel.setForeground(Color.RED);  // Set the text color to red

gbc.gridx = 0;
gbc.gridy = 0;
bugPanel.add(bugIdLabel, gbc);  // Add the modified JLabel


gbc.gridx = 1;
bugPanel.add(idData, gbc);

// For Product Label
JLabel productLabel = new JLabel("Product:");
productLabel.setFont(new Font("Arial", Font.BOLD, 12));  // Bold, 12-point font
productLabel.setForeground(Color.RED);  // Set text color to red
gbc.gridx = 0;
gbc.gridy = 1;
bugPanel.add(productLabel, gbc);

gbc.gridx = 1;
bugPanel.add(product, gbc);

// For Environment Label
JLabel environmentLabel = new JLabel("Environment:");
environmentLabel.setFont(new Font("Arial", Font.BOLD, 12));  // Bold, 12-point font
environmentLabel.setForeground(Color.RED);  // Set text color to red
gbc.gridx = 0;
gbc.gridy = 2;
bugPanel.add(environmentLabel, gbc);

gbc.gridx = 1;
bugPanel.add(environment, gbc);

// For Type Label
JLabel typeLabel = new JLabel("Type:");
typeLabel.setFont(new Font("Arial", Font.BOLD, 12));  // Bold, 12-point font
typeLabel.setForeground(Color.RED);  // Set text color to red
gbc.gridx = 0;
gbc.gridy = 3;
bugPanel.add(typeLabel, gbc);

gbc.gridx = 1;
bugPanel.add(type, gbc);

// For Description Label
JLabel descriptionLabel = new JLabel("Description:");
descriptionLabel.setFont(new Font("Arial", Font.BOLD, 12));  // Bold, 12-point font
descriptionLabel.setForeground(Color.RED);  // Set text color to red
gbc.gridx = 0;
gbc.gridy = 4;
bugPanel.add(descriptionLabel, gbc);

// For Description JTextArea (modified to a larger size)
description = new JTextArea(20, 50);  // Larger size
description.setLineWrap(true);  // Enable line wrap
description.setWrapStyleWord(true);  // Wrap at word boundaries
gbc.gridx = 1;
gbc.gridy = 4;
bugPanel.add(new JScrollPane(description), gbc);  // Add JScrollPane for the JTextArea

// For Status Label
JLabel statusLabel = new JLabel("Status:");
statusLabel.setFont(new Font("Arial", Font.BOLD, 12));  // Bold, 12-point font
statusLabel.setForeground(Color.RED);  // Set text color to red
gbc.gridx = 0;
gbc.gridy = 5;
bugPanel.add(statusLabel, gbc);

// For Status ComboBox
gbc.gridx = 1;
bugPanel.add(status, gbc);

        // Add buttons in the next row
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2; // Make the buttons span across two columns
        gbc.anchor = GridBagConstraints.CENTER; // Center the buttons

        JPanel buttonPanel = new JPanel(); // Panel to hold the buttons
        buttonPanel.add(ADDRECORDButton);
        buttonPanel.add(UPDATERECORDButton);
        bugPanel.add(buttonPanel, gbc);

        // Set up frame
        bugF.setContentPane(bugPanel);
        bugF.pack();
        bugF.setLocationRelativeTo(null);
        bugF.setVisible(true);

        // Load table data
        tableData();

        // Add action listeners
        ADDRECORDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idData.getText().equals("") || description.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please Fill All Fields to add Bug.");
                } else {
                    try {
                        String sql = "INSERT INTO bug (Bug_ID, Product, Environment, Type, Description, Status) VALUES (?,?,?,?,?,?)";
                        Class.forName("org.postgresql.Driver");
                        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Associate", "postgres", "9550392007");
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setInt(1, Integer.parseInt(idData.getText()));
                        statement.setString(2, product.getSelectedItem().toString());
                        statement.setString(3, environment.getSelectedItem().toString());
                        statement.setString(4, type.getSelectedItem().toString());
                        statement.setString(5, description.getText());
                        statement.setString(6, status.getSelectedItem().toString());
                        statement.executeUpdate();
                        JOptionPane.showMessageDialog(null, "ITEM ADDED SUCCESSFULLY");
                        idData.setText("");
                        description.setText("");
                        connection.close();  // Close connection after use
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                    tableData();
                }
            }
        });

        UPDATERECORDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql = "UPDATE bug SET Product = ?, Environment = ?, Description = ?, Status = ? WHERE Bug_ID = ?";
                    Class.forName("org.postgresql.Driver");
                    Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Associate", "postgres", "9550392007");
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, product.getSelectedItem().toString());
                    statement.setString(2, environment.getSelectedItem().toString());
                    statement.setString(3, description.getText());
                    statement.setString(4, status.getSelectedItem().toString());
                    statement.setInt(5, Integer.parseInt(idData.getText()));
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Updated successfully");
                    connection.close();  // Close connection after use
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, e2.getMessage());
                }
                tableData();
            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultTableModel dm = (DefaultTableModel) table1.getModel();
                int selectedRow = table1.getSelectedRow();
                idData.setText(dm.getValueAt(selectedRow, 0).toString());
                description.setText(dm.getValueAt(selectedRow, 4).toString());
            }
        });
    }

    public void tableData() {
        try {
            String a = "SELECT * FROM bug";
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Associate", "postgres", "9550392007");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(a);
            table1.setModel(buildTableModel(rs));
            connection.close();  // Close connection after use
        } catch (Exception ex1) {
            JOptionPane.showMessageDialog(null, ex1.getMessage());
        }
    }

    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }
}
