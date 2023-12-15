package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class newDog {
    private JFrame frame;
    private JTextField nameTextField;
    private JTextField breedTextField;
    private JTextField ownerIdTextField;
    private GUI MainGUI;

    public newDog(GUI MainGUI) {
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

        JLabel nameLabel = new JLabel("Név:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        nameTextField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(nameTextField, gbc);

        JLabel breedLabel = new JLabel("Fajta:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(breedLabel, gbc);

        breedTextField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(breedTextField, gbc);

        JLabel ownerIdLabel = new JLabel("Gazda ID:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(ownerIdLabel, gbc);

        ownerIdTextField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(ownerIdTextField, gbc);

        JButton addToMySQLButton = new JButton("Add to MySQL");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(addToMySQLButton, gbc);

        JButton addToMongoDBButton = new JButton("Add to MongoDB");
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(addToMongoDBButton, gbc);

        addToMySQLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDogtoMySQL();
            }
        });

        addToMongoDBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDogToMongoDB();
            }
        });

        frame.setVisible(true);
    }

    private void addDogtoMySQL() {
        String name = nameTextField.getText();
        String breed = breedTextField.getText();
        int owner_id = Integer.parseInt(ownerIdTextField.getText());

        Kutya kutya = new Kutya(name, breed, owner_id); // Az id-t majd a MySQL generálja
        Main.mySQLConnectionManager.insertKutya(kutya);
        JOptionPane.showMessageDialog(frame, "Dog added to MySQL successfully");
        MainGUI.refreshMySQLData();
    }

    private void addDogToMongoDB() {
        String name = nameTextField.getText();
        String breed = breedTextField.getText();
        int owner_id = Integer.parseInt(ownerIdTextField.getText());
        Kutya kutya = new Kutya(name, breed, owner_id);

        Main.mongoDBConnectionManager.insertKutya(kutya);
        JOptionPane.showMessageDialog(frame, "Dog added to MongoDB successfully");
        MainGUI.refreshMongoData();

    }

}
