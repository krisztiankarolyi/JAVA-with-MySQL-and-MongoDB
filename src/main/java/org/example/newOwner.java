package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class newOwner {
    private JFrame frame;
    private JTextField nameTextField;
    private JTextField ageTextField;
    private GUI MainGUI;

    public newOwner(GUI MainGUI) {
        this.MainGUI = MainGUI;
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("New Owner Form");
        frame.setBounds(100, 100, 300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);  // Távolságok beállítása

        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        nameTextField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(nameTextField, gbc);

        JLabel ageLabel = new JLabel("Age:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(ageLabel, gbc);

        ageTextField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(ageTextField, gbc);

        JButton addToMySQLButton = new JButton("Add to MySQL");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(addToMySQLButton, gbc);

        JButton addToMongoDBButton = new JButton("Add to MongoDB");
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(addToMongoDBButton, gbc);

        addToMySQLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOwnerToMySQL();
            }
        });

        addToMongoDBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOwnerToMongoDB();
            }
        });

        frame.setVisible(true);
    }

    private void addOwnerToMySQL() {
        String name = nameTextField.getText();
        int age = Integer.parseInt(ageTextField.getText());

        Gazda gazda = new Gazda(name, age); // Az id-t majd a MySQL generálja
        Main.mySQLConnectionManager.insertGazda(gazda);
        JOptionPane.showMessageDialog(frame, "Owner added to MySQL successfully");
        MainGUI.refreshMySQLData();
    }

    private void addOwnerToMongoDB() {
        String name = nameTextField.getText();
        int age = Integer.parseInt(ageTextField.getText());

        Gazda gazda = new Gazda(name, age, 0); // Az id-t majd a MongoDB generálja
        Main.mongoDBConnectionManager.insertGazda(gazda);
        JOptionPane.showMessageDialog(frame, "Owner added to MongoDB successfully");
        MainGUI.refreshMongoData();

    }

}
