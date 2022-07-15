package hu.modeldriven.cameo.unmarshall.ui;

import hu.modeldriven.cameo.unmarshall.event.CloseDialogRequestedEvent;
import hu.modeldriven.core.eventbus.EventBus;

import javax.swing.*;
import java.awt.*;

public class UnmarshallDialog extends JDialog {

    public UnmarshallDialog(Frame parent, EventBus eventBus) {
        super(parent, "Unmarshall action", false);

        eventBus.subscribe(CloseDialogRequestedEvent.class, this::closeDialogRequested);

        this.setContentPane(new UnmarshallPanel(eventBus));
        this.pack();

        this.setLocationRelativeTo(parent);
        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }

    private void closeDialogRequested(CloseDialogRequestedEvent event) {
        UnmarshallDialog.this.setVisible(false);
    }

}
