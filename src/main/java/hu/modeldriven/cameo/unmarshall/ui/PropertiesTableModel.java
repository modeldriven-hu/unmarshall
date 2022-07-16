package hu.modeldriven.cameo.unmarshall.ui;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class PropertiesTableModel extends DefaultTableModel {

    enum SelectionState {SELECT_ALL, DESELECT_All}

    private SelectionState selectionState = SelectionState.SELECT_ALL;

    public static PropertiesTableModel fromRecords(List<PropertyRecord> records){
        var columnNames = new Vector<>(Arrays.asList(new String[]{"Selection", "Property name"}));

        Vector<Vector> rows = new Vector<>();

        for (var record : records) {
            var row = new Vector<>();
            row.add(Boolean.FALSE);
            row.add(record.getName());
            rows.add(row);
        }

        return new PropertiesTableModel(rows, columnNames);
    }

    public PropertiesTableModel(Vector<Vector> rows, Vector<String> columnNames) {
        super(rows, columnNames);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Boolean.class;
            default:
                return String.class;
        }
    }

    public List<String> getSelectedProperties() {
        var properties = new ArrayList<String>();
        for (var i = 0; i < getRowCount(); i++) {
            var selected = (Boolean) getValueAt(i, 0);

            if (Boolean.TRUE.equals(selected)) {
                properties.add((String) getValueAt(i, 1));
            }
        }
        return properties;
    }

    public void invertSelection() {

        for (var i = 0; i < getRowCount(); i++) {
            setValueAt(selectionState == SelectionState.SELECT_ALL, i, 0);
        }

        // invert selection state
        this.selectionState = this.selectionState == SelectionState.SELECT_ALL ?
                SelectionState.DESELECT_All : SelectionState.SELECT_ALL;
    }

}
