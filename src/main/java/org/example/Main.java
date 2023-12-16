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
    public static  MySQLConnectionManager mySQLConnectionManager;
    public static  MongoDBConnectionManager mongoDBConnectionManager;

    public static void main(String[] args) {
        GUI mainGUI = new GUI();
        mainGUI.display();
        mySQLConnectionManager.closeConnection();
        mongoDBConnectionManager.closeClient();
    }

    public static boolean connectMySQL(String url){
        try{
            mySQLConnectionManager = new MySQLConnectionManager(url);

            if(mySQLConnectionManager.isConnectionValid())
            {
                return true;
            }
            else
            {
                System.out.println("Nem sikerült kapcsolódni a MySQL-re");
            }
        }
        catch (Exception ex){
            System.out.println("nem sikerült létrehozni a MySQL kapcsolatot");
            ex.printStackTrace();
        }
        return  false;
    }

    public static  boolean connectMongoDB(String url){
        try{
            mongoDBConnectionManager  = new MongoDBConnectionManager(url);
            if(mongoDBConnectionManager.isConnectionValid())
            {
                return true;
            }
            else
            {
                System.out.println("Nem sikerült kapcsolódni a MongoDB-re");
            }

        }
        catch (Exception ex){
            System.out.println("nem sikerült létrehozni a MongoDB kapcsolatot");
            ex.printStackTrace();
        }
        return  false;
    }

    public static void createMySQLTables() {
        try
        {
            mySQLConnectionManager.clearTables();
            mySQLConnectionManager.createGazdakTable();
            mySQLConnectionManager.createKutyakTable();
        }

        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public  static void fakeDataMySQL(){
        Gazda[] gazdak = {
                new Gazda("Béla", 30, 1),
                new Gazda("Katalin", 25, 2)
                };

        for (int i = 0; i<gazdak.length; i++){
            mySQLConnectionManager.insertGazda(gazdak[i]);
        }

        Kutya[] kutyak = {
                new Kutya("Buksi", "Német juhász", 1),
                new Kutya("Bogáncs", "Puli", 1),
                new Kutya("Szimat", "Vizsla", 2),
                new Kutya("Tappancs", "Corgi", 2)
        };

        for (int i = 0; i<kutyak.length; i++){
             mySQLConnectionManager.insertKutya(kutyak[i]);
        }
    }

    public  static void fakeDataMongoDB(){
        Gazda[] gazdak = {
                new Gazda("Béla", 30, 1),
                new Gazda("Katalin", 25, 2)
        };

        for (int i = 0; i<gazdak.length; i++){
            mongoDBConnectionManager.insertGazda(gazdak[i]);
        }

        Kutya[] kutyak = {
                new Kutya("Buksi", "Német juhász", 1),
                new Kutya("Bogáncs", "Puli", 1),
                new Kutya("Szimat", "Vizsla", 2),
                new Kutya("Tappancs", "Corgi", 2)
        };

        for (int i = 0; i<kutyak.length; i++){
            mongoDBConnectionManager.insertKutya(kutyak[i]);
        }
    }

    public static void createMongoDBTables() {
        try
        {
            mongoDBConnectionManager.dropCollectionIfExists("gazdak");

            mongoDBConnectionManager.createGazdakCollection();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
