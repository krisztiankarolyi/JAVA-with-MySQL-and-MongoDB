package org.example.tableModel;

import org.example.Gazda;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class GazdaTableModel extends DefaultTableModel {
    private List<Gazda> gazdak;
    private String[] columnNames = {"ID", "Név", "Kor"};

    public GazdaTableModel(List<Gazda> gazdak) {
        this.gazdak = gazdak;
        setDataVector(getData(), columnNames);
    }

    private Object[][] getData() {
        Object[][] data = new Object[gazdak.size()][3];

        for (int i = 0; i < gazdak.size(); i++) {
            Gazda gazda = gazdak.get(i);
            data[i][0] = gazda.get_id();
            data[i][1] = gazda.getNev();
            data[i][2] = gazda.getKor();
        }

        return data;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // A táblázatot nem szerkeszthetjük
    }
}
