package hu.modeldriven.cameo.unmarshall.action;

import com.nomagic.magicdraw.ui.actions.DefaultDiagramAction;
import com.nomagic.ui.ScalableImageIcon;
import hu.modeldriven.cameo.unmarshall.event.PresentationElementSelectedEvent;
import hu.modeldriven.cameo.unmarshall.usecase.*;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.usecase.UseCase;

import java.awt.event.ActionEvent;

public class DiagramAction extends DefaultDiagramAction {

    private final EventBus eventBus;
    private final UseCase[] initialUseCases;

    public DiagramAction(String id, String name) {
        super(id, name, null, null);

        this.eventBus = new EventBus();

        this.initialUseCases = new UseCase[]{
                new ActionSelectedUseCase(eventBus),
                new CreateSelectionFromActionUseCase(eventBus),
                new CreateSelectionFromInputPinUseCase(eventBus),
                new UnmarshallOnActionUseCase(eventBus),
                new DisplayNameAndTypeUseCase(eventBus)
        };

        var url = getClass().getResource("/icon.png");
        setSmallIcon(new ScalableImageIcon(url));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        var presentationElements = getSelected();

        if (presentationElements != null && presentationElements.size() == 1) {
            var presentationElement = presentationElements.get(0);
            this.eventBus.publish(new PresentationElementSelectedEvent(presentationElement));
        }
    }
}
