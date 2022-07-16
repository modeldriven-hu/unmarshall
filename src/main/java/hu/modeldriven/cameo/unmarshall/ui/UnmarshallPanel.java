package hu.modeldriven.cameo.unmarshall.ui;

import hu.modeldriven.cameo.unmarshall.common.Orientation;
import hu.modeldriven.cameo.unmarshall.common.PinType;
import hu.modeldriven.cameo.unmarshall.event.CloseDialogRequestedEvent;
import hu.modeldriven.cameo.unmarshall.event.UnmarshallRequestedEvent;
import hu.modeldriven.cameo.unmarshall.usecase.SetSelectionDataUseCase;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.usecase.UseCase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.EnumSet;
import java.util.List;

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
        calculateComboboxData(pinTypeCombobox, PinType.class);
        calculateComboboxData(orientationComboBox, Orientation.class);

        this.propertiesTable.setGridColor(new Color(240, 240, 240));

        this.selectDeselectButton.addActionListener(this::onSelectDeselect);
        this.unmarshallButton.addActionListener(this::onUnmarshall);
        this.cancelButton.addActionListener(this::onCloseDialog);
    }

    private <E extends Enum<E>> void calculateComboboxData(JComboBox comboBox, Class<E> clazz) {
        var model = new DefaultComboBoxModel<E>();

        EnumSet.allOf(clazz).forEach(model::addElement);

        comboBox.setModel(model);
        comboBox.setSelectedIndex(0);
    }

    public void setPropertyRecords(List<PropertyRecord> records) {
        SwingUtilities.invokeLater(() -> {

            var model = PropertiesTableModel.fromRecords(records);
            propertiesTable.setModel(model);

            var column = propertiesTable.getColumnModel().getColumn(0);

            column.setMinWidth(100);
            column.setMaxWidth(100);
            column.setPreferredWidth(100);

            propertiesTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        });
    }

    private void onSelectDeselect(ActionEvent e) {
        getTableModel().invertSelection();
    }

    private void onUnmarshall(ActionEvent e) {
        var pinType = (PinType) pinTypeCombobox.getSelectedItem();
        var orientation = (Orientation) orientationComboBox.getSelectedItem();
        var properties = getTableModel().getSelectedProperties();

        eventBus.publish(new UnmarshallRequestedEvent(pinType, orientation, properties));
    }

    private PropertiesTableModel getTableModel() {
        return (PropertiesTableModel) propertiesTable.getModel();
    }

    private void onCloseDialog(ActionEvent e) {
        eventBus.publish(new CloseDialogRequestedEvent());
    }

}
