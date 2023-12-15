package org.example;


import org.example.Gazda;
import org.example.Kutya;
import org.example.tableModel.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class GUI {
    private JFrame frame;
    private JButton testMySQLButton;
    private JButton testMongoDBButton;
    private  JButton clearMongoDBButton;
    private  JButton clearMySQLButton;
    private JButton fakeDataMySQLButton;
    private JButton fakeDataMongoDBButton;
    private JButton newGazdabtn;
    private JButton newKutyaBtn;
    private JTable mysqlGazdaTable;
    private JTable mysqlKutyaTable;
    private JTextArea mongoDBDocumentArea;

    private GUI self;

    public GUI() {
        initialize();
        this.self = this;
    }


    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Adatbázis projekt Károlyi Krisztián");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);  // Távolságok beállítása

        testMySQLButton = new JButton("Test MySQL Connection");
        panel.add(testMySQLButton, gbc);

        testMongoDBButton = new JButton("Test MongoDB Connection");
        gbc.gridx = 1;  // Két oszlopos elrendezés esetén a második oszlopba tesszük
        panel.add(testMongoDBButton, gbc);



        JTextArea mySQLConnectionStateText = new JTextArea("MySQL connection state: not tested yet");
        JTextArea mongoDBConnectionStateText = new JTextArea("MongoDB connection state: not tested yet");
        mySQLConnectionStateText.setEditable(false);
        mongoDBConnectionStateText.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(mySQLConnectionStateText, gbc);

        gbc.gridx = 1;
        panel.add(mongoDBConnectionStateText, gbc);

        clearMongoDBButton = new JButton("Clear mongoDB documents");
        gbc.gridy = 3;
        gbc.gridx = 1;
        clearMongoDBButton.setEnabled(false);
        panel.add(clearMongoDBButton, gbc);

        clearMySQLButton = new JButton("Clear mySQL tables");
        gbc.gridy = 3;
        gbc.gridx = 0;
        clearMySQLButton.setEnabled(false);
        panel.add(clearMySQLButton, gbc);

        fakeDataMySQLButton = new JButton("Insert Test Data to MySQL");
        fakeDataMySQLButton.setEnabled(false);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(fakeDataMySQLButton, gbc);

        fakeDataMongoDBButton = new JButton("Insert Test Data to MongoDB");
        fakeDataMongoDBButton.setEnabled(false);
        gbc.gridx = 1;
        panel.add(fakeDataMongoDBButton, gbc);

        mysqlGazdaTable = new JTable();
        JScrollPane mysqlGazdaScrollPane = new JScrollPane(mysqlGazdaTable);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;  // 1 helyet foglal el
        gbc.fill = GridBagConstraints.BOTH;  // Kitölti az elrendezési cellát mindkét irányban
        gbc.weightx = 1.0;  // Engedi a komponenst a vízszintes tengely mentén növekedni
        gbc.weighty = 1.0;  // Engedi a komponenst a függőleges tengely mentén növekedni
        panel.add(mysqlGazdaScrollPane, gbc);

        mongoDBDocumentArea = new JTextArea();
        mongoDBDocumentArea.setFont(new Font("Monospace", Font.PLAIN, 15));
        mongoDBDocumentArea.setBackground(Color.DARK_GRAY);
        mongoDBDocumentArea.setForeground(Color.WHITE);
        mongoDBDocumentArea.setEditable(false);

        JScrollPane mongoDBDocumentScrollPane = new JScrollPane(mongoDBDocumentArea);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridheight = 2;
        panel.add(mongoDBDocumentScrollPane, gbc);

        mysqlKutyaTable = new JTable();
        JScrollPane mysqlKutyaScrollPane = new JScrollPane(mysqlKutyaTable);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridheight = 1;
        panel.add(mysqlKutyaScrollPane, gbc);

        mongoDBDocumentArea.setText("Mongo db dokumentumok:");

        newGazdabtn = new JButton("new Owner");
        newGazdabtn.setEnabled(false);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.08;
        panel.add(newGazdabtn, gbc);

        newKutyaBtn = new JButton("new dog");
        newKutyaBtn.setEnabled(false);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.08;
        panel.add(newKutyaBtn, gbc);


        testMySQLButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(Main.connectMySQL())
                {
                    mySQLConnectionStateText.setForeground(Color.green);
                    mySQLConnectionStateText.setText("MySQL connection: OK");
                    fakeDataMySQLButton.setEnabled(true);
                    clearMySQLButton.setEnabled(true);
                    newGazdabtn.setEnabled(true);
                    newKutyaBtn.setEnabled(true);
                    refreshMySQLData();
                }
                else{
                    mySQLConnectionStateText.setForeground(Color.red);
                    mySQLConnectionStateText.setText("MySQL connection: error");
                }

            }
        });

        testMongoDBButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(Main.connectMongoDB())
                {
                    mongoDBConnectionStateText.setForeground(Color.green);
                    mongoDBConnectionStateText.setText("MongoDB connection: OK");
                    fakeDataMongoDBButton.setEnabled(true);
                    clearMongoDBButton.setEnabled(true);
                    newGazdabtn.setEnabled(true);
                    newKutyaBtn.setEnabled(true);
                    refreshMongoData();
                }
                else{
                    mongoDBConnectionStateText.setForeground(Color.red);
                    mongoDBConnectionStateText.setText("MongoDB connection: error");
                }
            }
        });

        fakeDataMySQLButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.fakeDataMySQL();
                refreshMySQLData();
            }
        });

        fakeDataMongoDBButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               Main.fakeDataMongoDB();
               refreshMongoData();
            }
        });

        newGazdabtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newOwner urlap = new newOwner(self);

            }
        });

        newKutyaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newDog urlap = new newDog(self);

            }
        });

        clearMongoDBButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.mongoDBConnectionManager.clearDocuments();
                refreshMongoData();
            }
        });

        clearMySQLButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.mySQLConnectionManager.clearTables();
                try {
                    Main.mySQLConnectionManager.createGazdakTable();
                    Main.mySQLConnectionManager.createKutyakTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                refreshMySQLData();
            }
        });
    }

    public void refreshMongoData(){
        String gazdak = Main.mongoDBConnectionManager.getGazdakAsString();
        this.mongoDBDocumentArea.setText(gazdak);

    }

    public void refreshMySQLData(){
        ArrayList<Gazda> gazdak = Main.mySQLConnectionManager.getGazdak();
        ArrayList<Kutya> kutyak = Main.mySQLConnectionManager.getKutyak();

        mysqlGazdaTable.setModel(new GazdaTableModel(gazdak));
        mysqlKutyaTable.setModel(new KutyaTableModel(kutyak));
    }

    public void display() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
