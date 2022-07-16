package hu.modeldriven.cameo.unmarshall.common;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.openapi.uml.PresentationElementsManager;
import com.nomagic.magicdraw.openapi.uml.ReadOnlyElementException;
import com.nomagic.magicdraw.properties.BooleanProperty;
import com.nomagic.magicdraw.properties.PropertyID;
import com.nomagic.magicdraw.uml.symbols.PresentationElement;

public class PresentationElementHelper {

    private PresentationElementHelper(){
    }

    public static void showNameAndType(PresentationElement presentationElement) {
        try {
            setProperty(presentationElement, PropertyID.SHOW_TYPE, true);
            setProperty(presentationElement, PropertyID.SHOW_NAME, true);
        } catch (ReadOnlyElementException e) {
            Application.getInstance().getGUILog().showError("Element is read only!");
        }
    }

    public static void setProperty(PresentationElement element, String id, boolean value) throws ReadOnlyElementException {
        var propertyManager = element.getPropertyManager().clone();
        var property = (BooleanProperty) propertyManager.getProperty(id);
        if (property != null) {
            property.setValue(value);
            PresentationElementsManager.getInstance().setPresentationElementProperties(element, propertyManager);
        }
    }

}
