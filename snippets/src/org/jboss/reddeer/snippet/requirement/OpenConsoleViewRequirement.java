package org.jboss.reddeer.snippet.requirement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.eclipse.ui.console.ConsoleView;
import org.jboss.reddeer.junit.requirement.Requirement;

import org.jboss.reddeer.snippet.requirement.OpenConsoleViewRequirement.OpenConsoleView;

/**
 * Requirement opening console view.
 */
public class OpenConsoleViewRequirement implements Requirement<OpenConsoleView> {

    private ConsoleView consoleView = new ConsoleView();

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.TYPE})
    public @interface OpenConsoleView {

    }

    public boolean canFulfill() {
        //console view can be always open
        return true;
    }

    public void fulfill() {
        System.out.println("Opening console view.");
        consoleView.open();
    }

    public void setDeclaration(OpenConsoleView declaration) {
        //this annotation has no elements so we leave this method empty 
    }
    
    public void cleanUp() {
    	// nothing to do
    }
}