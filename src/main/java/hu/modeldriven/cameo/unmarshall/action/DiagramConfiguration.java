package hu.modeldriven.cameo.unmarshall.action;

import com.nomagic.actions.AMConfigurator;
import com.nomagic.actions.ActionsManager;
import com.nomagic.magicdraw.actions.MDActionsCategory;

public class DiagramConfiguration implements AMConfigurator {

    private final DiagramAction diagramAction;

    public DiagramConfiguration(DiagramAction diagramAction) {
        this.diagramAction = diagramAction;
    }

    @Override
    public void configure(ActionsManager actionsManager) {
        var category = new MDActionsCategory("hu.modeldriven.diagramtoolbar", "Modeldriven Toolbar");
        category.addAction(diagramAction);
        actionsManager.addCategory(category);
    }

    @Override
    public int getPriority() {
        return MEDIUM_PRIORITY;
    }
}