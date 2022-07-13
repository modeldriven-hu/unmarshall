package hu.modeldriven.cameo;

import com.nomagic.magicdraw.actions.ActionsConfiguratorsManager;
import com.nomagic.magicdraw.plugins.Plugin;
import hu.modeldriven.cameo.action.*;

public class UnmarshallPlugin extends Plugin {

    @Override
    public void init() {
        var action = new DiagramAction("hu.modeldriven.unmarshallAction", "Unmarshall");
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
