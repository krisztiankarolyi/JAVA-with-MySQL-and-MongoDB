package org.example;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;
import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.*;

// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello and welcome!");

        // MySQL
        createMySQLTables();
        // MongoDB
        createMongoDBTables();
    }
public static void createMySQLTables(){
    clearTables();
    createGazdakTable();
    createKutyakTable();
}

public static void createGazdakTable() {
    String MYSQL = "jdbc:mysql://localhost:3306/kutyak";
    String sqlCreateGazdakTable = "CREATE TABLE gazdak ("
            + "id INT PRIMARY KEY AUTO_INCREMENT,"
            + "name VARCHAR(255) NOT NULL,"
            + "age INT NOT NULL"
            + ")";

    try (Connection conn = getConnection(MYSQL, "root", "");
         PreparedStatement ps = conn.prepareStatement(sqlCreateGazdakTable)) {

        ps.execute();

        System.out.println("Table 'gazdak' created successfully.");

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public static void createKutyakTable() {
    String MYSQL = "jdbc:mysql://localhost:3306/kutyak"; // Update the database name if needed
    String sqlCreateKutyakTable = "CREATE TABLE kutyak ("
            + "id INT PRIMARY KEY AUTO_INCREMENT,"
            + "name VARCHAR(255) NOT NULL,"
            + "breed VARCHAR(255) NOT NULL,"
            + "gazda_id INT,"
            + "FOREIGN KEY (gazda_id) REFERENCES gazdak(id)"
            + ")";

    try (Connection conn = getConnection(MYSQL, "root", "");
         PreparedStatement ps = conn.prepareStatement(sqlCreateKutyakTable)) {

        // Execute the CREATE TABLE query
        ps.execute();

        System.out.println("Table 'kutyak' created successfully.");

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public static void clearTables() {
    String MYSQL = "jdbc:mysql://localhost:3306/kutyak"; // Update the database name if needed

    try (Connection conn = getConnection(MYSQL, "root", "")) {
        dropTableIfExists(conn, "kutyak");
        dropTableIfExists(conn, "gazdak");

        System.out.println("Tables dropped successfully.");

    } catch (SQLException e) {
        e.printStackTrace(); // Or log the exception using a logging framework
        // Handle the exception or throw a custom exception if needed
    }
}

private static void dropTableIfExists(Connection conn, String tableName) throws SQLException {
    String sqlDropTable = "DROP TABLE IF EXISTS " + tableName;

    try (PreparedStatement ps = conn.prepareStatement(sqlDropTable)) {
        // Execute the DROP TABLE query
        ps.execute();
    }
}

    public static void createMongoDBTables() {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            // Drop existing collections
            dropCollectionIfExists(mongoClient, "gazdak");
            // Create 'gazdak' collection
            createGazdakCollection(mongoClient);

        }
    }

    public static void createGazdakCollection(MongoClient mongoClient) {
        try {
            MongoDatabase database = mongoClient.getDatabase("kutyak");
            MongoCollection<Document> gazdakCollection = database.getCollection("gazdak");

            // Create an index on 'id' field
            gazdakCollection.createIndex(new Document("id", 1), new IndexOptions().unique(true));

            // Gazda dokumentumok
            Document gazda1 = new Document("id", 1)
                    .append("name", "Gazda1")
                    .append("age", 30);

            Document gazda2 = new Document("id", 2)
                    .append("name", "Gazda2")
                    .append("age", 25);

            // Kutyák dokumentumok
            ArrayList<Document> kutyakGazda1 = new ArrayList<>();
            kutyakGazda1.add(new Document("name", "Kutya1").append("breed", "Fajta1"));
            kutyakGazda1.add(new Document("name", "Kutya2").append("breed", "Fajta2"));

            ArrayList<Document> kutyakGazda2 = new ArrayList<>();
            kutyakGazda2.add(new Document("name", "Kutya3").append("breed", "Fajta3"));
            kutyakGazda2.add(new Document("name", "Kutya4").append("breed", "Fajta4"));

            // Gazdák dokumentumokat hozzáadása a kollekcióhoz
            gazdakCollection.insertOne(gazda1.append("kutyak", kutyakGazda1));
            gazdakCollection.insertOne(gazda2.append("kutyak", kutyakGazda2));

            System.out.println("Collection 'gazdak' created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void dropCollectionIfExists(MongoClient mongoClient, String collectionName) {
        try{
            MongoDatabase database = mongoClient.getDatabase("kutyak");
            MongoCollection<Document> gazdakCollection = database.getCollection("gazdak");

            if (database.listCollectionNames().into(new ArrayList<>()).contains(collectionName))
            {
                database.getCollection(collectionName).drop();
                System.out.println("Collection '" + collectionName + "' dropped successfully.");
            }
        }
        catch (Exception ex){

        }
    }
}