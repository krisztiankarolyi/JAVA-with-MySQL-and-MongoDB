package org.example.tableModel;

import org.example.Kutya;
import javax.swing.table.DefaultTableModel;
import java.util.List;
public class KutyaTableModel extends DefaultTableModel {
    private List<Kutya> kutyak;
    private String[] columnNames = {"Név", "Fajta", "Gazda ID"};

    public KutyaTableModel(List<Kutya> kutyak) {
        this.kutyak = kutyak;
        setDataVector(getData(), columnNames);
    }

    private Object[][] getData() {
        Object[][] data = new Object[kutyak.size()][4];

        for (int i = 0; i < kutyak.size(); i++) {
            Kutya kutya = kutyak.get(i);
            data[i][0] = kutya.getNev();
            data[i][1] = kutya.getFajta();
            data[i][2] = kutya.getGazda_id();
        }

        return data;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // A táblázatot nem szerkeszthetjük
    }
}
