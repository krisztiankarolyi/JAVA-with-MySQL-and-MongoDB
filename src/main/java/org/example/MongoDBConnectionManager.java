package org.example;
import ch.qos.logback.classic.LoggerContext;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;
import org.example.Gazda;
import org.example.Kutya;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MongoDBConnectionManager {
    private MongoClient mongoClient;
    private   MongoDatabase database;
    MongoCollection<Document> gazdakCollection;


    public MongoDBConnectionManager() {
        this.mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("dbProjekt");
    }

    public MongoDBConnectionManager(String url) {
        String URL = url.split("/")[0];
        String db = url.split("/")[1];
        this.mongoClient = MongoClients.create("mongodb://" + URL);
        database = mongoClient.getDatabase(db);
    }


    public boolean isConnectionValid() {
        try
        {
            Document ping = database.runCommand(new Document("ping", 1));
            return ping.containsKey("ok") && (Double) ping.get("ok") == 1.0;
        }

        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public MongoClient getClient() {
        return mongoClient;
    }

    public void closeClient()
    {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    public void clearDocuments() {
        try {
            gazdakCollection = database.getCollection("gazdak");

            Bson filter = new Document();
            gazdakCollection.deleteMany(filter);

            System.out.println("All documents in 'gazdak' collection deleted successfully.");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void createGazdakCollection() {
        try {
            MongoDatabase database = mongoClient.getDatabase("dbProjekt");
            MongoCollection<Document> gazdakCollection = database.getCollection("gazdak");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dropCollectionIfExists(String collectionName) {
        try {
            gazdakCollection = database.getCollection("gazdak");

            if (database.listCollectionNames().into(new ArrayList<>()).contains(collectionName)) {
                database.getCollection(collectionName).drop();
                System.out.println("Collection '" + collectionName + "' dropped successfully.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int insertGazda(Gazda gazda) {
        try {
            gazdakCollection = database.getCollection("gazdak");
            int _id = gazda.get_id();

            // Ellenőrizd, hogy létezik-e már dokumentum az adott _id-vel
            if(gazda.get_id() == 0){
                _id = getMaxId() + 1;
            }

            Bson filter = Filters.eq("_id", _id);
            Document existingDocument = gazdakCollection.find(filter).first();

            if (existingDocument != null) {
                System.out.println("Gazda already exists for id: " + _id);
            } else {
                // Ha nem létezik, akkor hozz létre egy új dokumentumot
                Document gazdaDocument = new Document("_id", _id)
                        .append("nev", gazda.getNev())
                        .append("kor", gazda.getKor());

                // Beszúrás az adatbázisba
                gazdakCollection.insertOne(gazdaDocument);
                System.out.println("Gazda inserted successfully.");
                return _id;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public int getMaxId() {
        try {
            gazdakCollection = database.getCollection("gazdak");

            // Legnagyobb _id érték lekérdezése
            Bson sortByDescId = Sorts.descending("_id");
            Document maxIdDocument = gazdakCollection.find().sort(sortByDescId).limit(1).first();

            if (maxIdDocument != null) {
                return maxIdDocument.getInteger("_id");
            } else {
                return 0; // Visszaadunk 0-t, ha nincs egyetlen dokumentum sem
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Visszaadunk -1-et, ha hiba történt
        }
    }


    public void insertKutya(Kutya kutya) {
        try {
            gazdakCollection = database.getCollection("gazdak");

            // Ellenőrizd, hogy létezik-e a megadott gazda_id-vel rendelkező gazda
            Bson gazdaFilter = Filters.eq("_id", kutya.getGazda_id());
            Document gazdaDocument = gazdakCollection.find(gazdaFilter).first();

            if (gazdaDocument != null) {
                // Ha létezik a gazda, akkor beszúrjuk a kutya dokumentumot
                Document kutyaDocument = new Document("nev", kutya.getNev())
                        .append("fajta", kutya.getFajta());

                // Hozzáadjuk a kutya dokumentumot a kutyak tömbhöz a gazda dokumentumban
                Bson update = Updates.push("kutyak", kutyaDocument);
                gazdakCollection.updateOne(gazdaFilter, update);

                System.out.println("Kutya inserted successfully for gazda_id: " + kutya.getGazda_id());
            } else {
                System.out.println("Gazda not found for id: " + kutya.getGazda_id());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getGazdakAsList() {
        try {
            gazdakCollection = database.getCollection("gazdak");

            FindIterable<Document> gazdakDocuments = gazdakCollection.find();
            MongoCursor<Document> cursor = gazdakDocuments.iterator();

            List<String> resultList = new ArrayList<>();
            JsonWriterSettings prettyPrint = JsonWriterSettings.builder().indent(true).build();

            while (cursor.hasNext()) {
                Document gazdaDocument = cursor.next();
                String jsonAsString = gazdaDocument.toJson(prettyPrint);
                resultList.add(jsonAsString);
            }

            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
