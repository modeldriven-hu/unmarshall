package hu.modeldriven.cameo.unmarshall.ui;

import hu.modeldriven.cameo.unmarshall.event.CloseDialogRequestedEvent;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.usecase.UseCase;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UnmarshallPanel extends BaseUnmarshallPanel{

    private final EventBus eventBus;
    private final UseCase[] useCases;

    public UnmarshallPanel(EventBus eventBus){
        super();

        this.eventBus = eventBus;

        this.useCases = new UseCase[]{
          // FIXME add usecases
        };

        this.updateComponents();
    }

    private void updateComponents(){

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

    private void closeDialogCommand(ActionEvent e) {
        eventBus.publish(new CloseDialogRequestedEvent());
    }

}
