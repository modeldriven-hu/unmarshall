package hu.modeldriven.cameo.unmarshall.ui;

import hu.modeldriven.cameo.unmarshall.event.CloseDialogRequestedEvent;
import hu.modeldriven.cameo.unmarshall.usecase.SetSelectionDataUseCase;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.usecase.UseCase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class UnmarshallPanel extends BaseUnmarshallPanel {

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

        this.cancelButton.addActionListener(this::closeDialogCommand);
    }

    public void setPropertyRecords(List<PropertyRecord> records) {
        SwingUtilities.invokeLater(() -> {

            var columnNames = new Vector<>(Arrays.asList(new String[]{"Selection", "Name"}));

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
        });

    }

    private void closeDialogCommand(ActionEvent e) {
        eventBus.publish(new CloseDialogRequestedEvent());
    }

}
