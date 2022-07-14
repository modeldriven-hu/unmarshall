package hu.modeldriven.cameo.unmarshall.usecase;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.Action;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import hu.modeldriven.cameo.unmarshall.event.ActionSelectedEvent;
import hu.modeldriven.cameo.unmarshall.event.PresentationElementSelectedEvent;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.usecase.AbstractUseCase;

public class CreateSelectionFromActionUseCase extends AbstractUseCase {

    public CreateSelectionFromActionUseCase(EventBus eventBus) {
        super(eventBus);
        eventBus.subscribe(PresentationElementSelectedEvent.class, this::onDiagramItemSelected);
    }

    public void onDiagramItemSelected(PresentationElementSelectedEvent event) {
        var element = event.getElement();
        if (element instanceof Action) {
            var action = (Action) element;
            var project = Application.getInstance().getProject();
            var selectedNode = project.getBrowser().getContainmentTree().getSelectedNode();

            if (selectedNode != null && selectedNode.getUserObject() instanceof Class) {
                var userObject = (Class) selectedNode.getUserObject();
                eventBus.publish(new ActionSelectedEvent(action, userObject));
            }
        }
    }

}
