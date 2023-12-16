package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class newOwner {
    private JFrame frame;
    private JTextField nameTextField;
    private JTextField ageTextField;
    private  JButton addToMongoDBButton;
    private  JButton addToMySQLButton;





    private  JTextArea commandArea;
    private GUI MainGUI;

    public newOwner(GUI MainGUI) {
        this.MainGUI = MainGUI;
        initialize();
        addToMySQLButton.setEnabled(MainGUI.mySQLConnected);
        addToMongoDBButton.setEnabled(MainGUI.mongodbConnected);
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

        addToMySQLButton = new JButton("Add to MySQL");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(addToMySQLButton, gbc);

        addToMongoDBButton = new JButton("Add to MongoDB");
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(addToMongoDBButton, gbc);

        commandArea = new JTextArea("");
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridy = 3;
        commandArea.setEditable(false);
        panel.add(commandArea, gbc);

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
        commandArea.setText(generateInsertSQL(gazda));
        MainGUI.refreshMySQLData();
    }

    private void addOwnerToMongoDB() {
        String name = nameTextField.getText();
        int age = Integer.parseInt(ageTextField.getText());

        Gazda gazda = new Gazda(name, age, 0); // Az id-t majd a MongoDB generálja
        int id = Main.mongoDBConnectionManager.insertGazda(gazda);
        JOptionPane.showMessageDialog(frame, "Owner added to MongoDB successfully");
        commandArea.setText(generateInsertJSON(gazda, id));
        MainGUI.refreshMongoData();

    }

    public String generateInsertJSON(Gazda gazda, int id) {
        return String.format("db.gazdak.insert({ " +
                        "\"_id\": %d, " +
                        "\"nev\": \"%s\", " +
                        "\"kor\": %d });",
                id, gazda.getNev(), gazda.getKor());
    }
    public String generateInsertSQL(Gazda gazda) {
        return String.format("INSERT INTO gazdak (nev, kor) values (%s, %d)",
               gazda.getNev(), gazda.getKor());
    }

}
