package org.example;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;

import java.io.Serializable;

public class Gazda{
    private String nev;
    private int kor;
    int _id;

    public Gazda(String nev, int kor, int _id) {
        this.nev = nev;
        this.kor = kor;
        this._id = _id;
    }

    public Gazda(String nev, int kor) {
        this.nev = nev;
        this.kor = kor;
        this._id = 0;
    }

    // toString() metódus példa
    @Override
    public String toString() {
        return "Gazda{" +
                "nev='" + nev + '\'' +
                ", kor=" + kor +
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

    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getKor() {
        return kor;
    }

    public void setKor(int kor) {
        this.kor = kor;
    }

    public int get_id() {
        return _id;
    }
}