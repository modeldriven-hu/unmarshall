package hu.modeldriven.cameo.unmarshall;

import com.nomagic.magicdraw.actions.ActionsConfiguratorsManager;
import com.nomagic.magicdraw.plugins.Plugin;
import hu.modeldriven.cameo.unmarshall.action.DiagramAction;
import hu.modeldriven.cameo.unmarshall.action.DiagramConfiguration;

public class UnmarshallPlugin extends Plugin {

    @Override
    public void init() {
        var action = new DiagramAction("hu.modeldriven.unmarshall.action", "Unmarshall");
        var configurator = new DiagramConfiguration(action);
        ActionsConfiguratorsManager.getInstance().addAnyDiagramCommandBarConfigurator(configurator);
    }

    @Override
    public boolean close() {
        return true;
    }

    @Override
    public boolean isSupported() {
        return true;
    }
}
