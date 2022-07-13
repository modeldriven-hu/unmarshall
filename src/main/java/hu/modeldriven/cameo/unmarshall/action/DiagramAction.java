package hu.modeldriven.cameo.unmarshall.action;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.ui.actions.DefaultDiagramAction;
import com.nomagic.ui.ScalableImageIcon;
import com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.Action;
import com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.InputPin;
import com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.Pin;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import hu.modeldriven.cameo.unmarshall.event.ActionSelectedEvent;
import hu.modeldriven.cameo.unmarshall.usecase.ActionSelectedUseCase;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.usecase.UseCase;

import java.awt.event.ActionEvent;

public class DiagramAction extends DefaultDiagramAction {

    public DiagramAction(String id, String name) {
        super(id, name, null, null);

        var url = getClass().getResource("/icon.png");
        setSmallIcon(new ScalableImageIcon(url));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        EventBus eventBus = new EventBus();

        var initialUseCases = new UseCase[]{
                new ActionSelectedUseCase()
        };

        for (var useCase : initialUseCases) {
            useCase.register(eventBus);
        }

        var presentationElements = getSelected();

        if (presentationElements != null && presentationElements.size() == 1) {
            var presentationElement = presentationElements.get(0);

            var element = presentationElement.getElement();

            if (element instanceof InputPin && element.getOwner() instanceof Action) {
                handleInputPinSelected(eventBus, element);
            }

            if (element instanceof Action) {
                handleActionSelected(eventBus, element);
            }
        }
    }

    private void handleInputPinSelected(EventBus eventBus, Element element) {
        var pin = (Pin) element;
        var action = (Action) pin.getOwner();
        var type = pin.getType();

        if (type instanceof Class) {
            eventBus.publish(new ActionSelectedEvent(action, (Class) type));
        }
    }

    private void handleActionSelected(EventBus eventBus, Element element) {
        var action = (Action) element;
        var project = Application.getInstance().getProject();
        var selectedNode = project.getBrowser().getContainmentTree().getSelectedNode();

        if (selectedNode != null && selectedNode.getUserObject() instanceof Class) {
            var userObject = (Class) selectedNode.getUserObject();
            eventBus.publish(new ActionSelectedEvent(action, userObject));
        }
    }

}
