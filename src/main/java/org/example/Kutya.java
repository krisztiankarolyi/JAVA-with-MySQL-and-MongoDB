package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;

public class Kutya {
    private String nev;
    private String fajta;
    private int id;
    @JsonIgnore
    private int gazda_id;


    public Kutya(String nev, String fajta, int gazda_id) {
        this.nev = nev;
        this.fajta = fajta;
        this.gazda_id = gazda_id;
        this.id = 0;
    }

    public Kutya(String nev, String fajta, int gazda_id, int id) {
        this.nev = nev;
        this.fajta = fajta;
        this.gazda_id = gazda_id;
        this.id = id;
    }

    public String toString() {
        return "Kutya{" +
                "nev='" + nev + '\'' +
                ", fajta =" + fajta +
                '}';
    }

    public String toJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getNev() {
        return nev;
    }
    public int getId() {
        return id;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getFajta() {
        return fajta;
    }

    public void setFajta(String fajta) {
        this.fajta = fajta;
    }

    @JsonIgnore
    public int getGazda_id() {
        return gazda_id;
    }

    @JsonIgnore
    public void setGazda_id(int gazda_id) {
        this.gazda_id = gazda_id;
    }
}
