package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.example.Gazda;
import org.example.Kutya;
public class MySQLConnectionManager {
    public static String MYSQL_URL = "jdbc:mysql://localhost:3306/kutyak";
    public static String MYSQL_USER = "root";
    public static String MYSQL_PASSWORD = "";
    private static Connection connection;

    public MySQLConnectionManager() {
        try {
            connection = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isConnectionValid() {
        try {
            // Próbáld meg lekérdezni a MySQL verzióját (ez csak egy példa)
            connection.createStatement().executeQuery("SELECT VERSION()");

            // Ha nem dob kivételt, akkor a kapcsolat érvényes
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Ha bármilyen hiba történik, a kapcsolat érvénytelen
        }
    }
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createGazdakTable() throws SQLException {
        String sqlCreateTable = "CREATE TABLE gazdak ("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "nev VARCHAR(255) NOT NULL,"
                + "kor INT NOT NULL"
                + ")";

        try {
            Statement statement = connection.createStatement();
            statement.execute(sqlCreateTable);
            System.out.println("Table 'gazdak' created successfully.");

            }
        catch (Exception ex){
            System.out.println("Error  occured while creating table 'gazdak' ");
        }

    }

    public  void createKutyakTable() {
        String sqlCreateKutyakTable = "CREATE TABLE kutyak ("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "nev VARCHAR(255) NOT NULL,"
                + "fajta VARCHAR(255) NOT NULL,"
                + "gazda_id INT,"
                + "FOREIGN KEY (gazda_id) REFERENCES gazdak(id)"
                + ")";

        try {
            Statement statement = connection.createStatement();
            statement.execute(sqlCreateKutyakTable);
            System.out.println("Table 'kutyak' created successfully.");

        }
        catch (Exception ex){
            System.out.println("Error  occured while creating table 'kutyak' ");
        }

    }


    public  void clearTables() {
        try  {
            dropTableIfExists(connection, "kutyak");
            dropTableIfExists(connection, "gazdak");

            System.out.println("Tables dropped successfully.");

        } catch (SQLException e) {
            e.printStackTrace(); // Or log the exception using a logging framework
            // Handle the exception or throw a custom exception if needed
        }
    }

    public void dropTableIfExists(Connection conn, String tableName) throws SQLException {
        String sqlDropTable = "DROP TABLE IF EXISTS " + tableName;

        try (PreparedStatement ps = conn.prepareStatement(sqlDropTable)) {
            ps.execute();
        }
    }

    public void insertGazda(Gazda gazda) {
        String sqlInsertGazda = "INSERT INTO gazdak (nev, kor) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertGazda)) {
            preparedStatement.setString(1, gazda.getNev());
            preparedStatement.setInt(2, gazda.getKor());

            preparedStatement.executeUpdate();
            System.out.println("Gazda inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertKutya(Kutya kutya) {
        String sqlInsertKutya= "INSERT INTO kutyak (nev, fajta, gazda_id) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertKutya)) {
            preparedStatement.setString(1, kutya.getNev());
            preparedStatement.setString(2, kutya.getFajta());
            preparedStatement.setInt(3, kutya.getGazda_id());

            preparedStatement.executeUpdate();
            System.out.println("Kutya inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Gazda> getGazdak() {
        ArrayList<Gazda> gazdak = new ArrayList<>();

        try {
            String sqlSelectGazdak = "SELECT * FROM gazdak";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSelectGazdak);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nev = resultSet.getString("nev");
                int kor = resultSet.getInt("kor");

                Gazda gazda = new Gazda(nev, kor, id);
                gazdak.add(gazda);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gazdak;
    }

    public ArrayList<Kutya> getKutyak() {
        ArrayList<Kutya> kutyak = new ArrayList<>();

        try {
            String sqlSelectKutyak = "SELECT * FROM kutyak";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSelectKutyak);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nev = resultSet.getString("nev");
                String fajta = resultSet.getString("fajta");
                int gazdaId = resultSet.getInt("gazda_id");

                Kutya kutya = new Kutya(nev, fajta, gazdaId);
                kutyak.add(kutya);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kutyak;
    }

}
