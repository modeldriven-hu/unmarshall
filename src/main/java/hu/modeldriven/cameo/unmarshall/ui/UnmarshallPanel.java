package hu.modeldriven.cameo.unmarshall.ui;

import hu.modeldriven.cameo.unmarshall.event.CloseDialogRequestedEvent;
import hu.modeldriven.cameo.unmarshall.usecase.SetSelectionDataUseCase;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.usecase.UseCase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class UnmarshallPanel extends BaseUnmarshallPanel {

    enum SelectionState {SELECT_ALL, DESELECT_All}

    private SelectionState selectionState = SelectionState.SELECT_ALL;

    private final EventBus eventBus;
    private final UseCase[] useCases;

    public UnmarshallPanel(EventBus eventBus) {
        super();

        this.eventBus = eventBus;

        this.useCases = new UseCase[]{
                new SetSelectionDataUseCase(eventBus, this)
        };

        this.updateComponents();
    }

    private void updateComponents() {

        var pinTypeModel = new DefaultComboBoxModel<String>();
        pinTypeModel.addElement("Input");
        pinTypeModel.addElement("Output");

        this.pinTypeCombobox.setModel(pinTypeModel);
        this.pinTypeCombobox.setSelectedIndex(0);

        var orientationModel = new DefaultComboBoxModel<String>();
        orientationModel.addElement("Top");
        orientationModel.addElement("Left");
        orientationModel.addElement("Bottom");
        orientationModel.addElement("Right");
        this.orientationComboBox.setModel(orientationModel);
        this.orientationComboBox.setSelectedIndex(0);

        this.propertiesTable.setGridColor(new Color(240, 240, 240));

        this.selectDeselectButton.addActionListener(this::selectDeselectCommand);
        this.cancelButton.addActionListener(this::closeDialogCommand);
    }

    public void setPropertyRecords(List<PropertyRecord> records) {
        SwingUtilities.invokeLater(() -> {

            var columnNames = new Vector<>(Arrays.asList(new String[]{"Selection", "Property name"}));

            Vector<Vector> rows = new Vector<>();

            for (var record : records) {
                var row = new Vector<>();
                row.add(Boolean.FALSE);
                row.add(record.getName());
                rows.add(row);
            }

            var model = new DefaultTableModel(rows, columnNames) {

                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    switch (columnIndex) {
                        case 0:
                            return Boolean.class;
                        default:
                            return String.class;
                    }
                }
            };

            propertiesTable.setModel(model);

            var column = propertiesTable.getColumnModel().getColumn(0);

            column.setMinWidth(100);
            column.setMaxWidth(100);
            column.setPreferredWidth(100);

            propertiesTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        });

    }

    private void selectDeselectCommand(ActionEvent e) {

        var model = (DefaultTableModel) propertiesTable.getModel();

        for (var i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(selectionState == SelectionState.SELECT_ALL, i, 0);
        }

        // invert selection state
        this.selectionState = this.selectionState == SelectionState.SELECT_ALL ? SelectionState.DESELECT_All : SelectionState.SELECT_ALL;
    }

    private void closeDialogCommand(ActionEvent e) {
        eventBus.publish(new CloseDialogRequestedEvent());
    }

}
