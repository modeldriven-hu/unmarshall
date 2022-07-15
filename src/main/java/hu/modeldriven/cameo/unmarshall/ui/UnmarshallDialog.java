package hu.modeldriven.cameo.unmarshall.ui;

import hu.modeldriven.cameo.unmarshall.event.CloseDialogRequestedEvent;
import hu.modeldriven.core.eventbus.EventBus;

import javax.swing.*;
import java.awt.*;

public class UnmarshallDialog extends JDialog {

    private final UnmarshallPanel panel;

    public UnmarshallDialog(Frame parent, EventBus eventBus) {
        super(parent, "Unmarshall action", false);

        this.panel = new UnmarshallPanel(eventBus);

        eventBus.subscribe(CloseDialogRequestedEvent.class, this::closeDialogRequested);

        this.setContentPane(panel);
        this.pack();

        this.setLocationRelativeTo(parent);
        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }

    private void closeDialogRequested(CloseDialogRequestedEvent event) {
        UnmarshallDialog.this.setVisible(false);
    }

}
