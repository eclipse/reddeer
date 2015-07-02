package org.jboss.reddeer.requirements.closeeditors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.ui.PlatformUI;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.junit.requirement.RequirementException;
import org.jboss.reddeer.requirements.closeeditors.CloseAllEditorsRequirement.CloseAllEditors;

/**
 * Closes all open editors
 * 
 * @author Lucia Jelinkova
 *
 */
public class CloseAllEditorsRequirement implements Requirement<CloseAllEditors> {

	@Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
	public @interface CloseAllEditors {
		boolean save() default true;
	}
	
	private CloseAllEditors declaration;
	
	@Override
	public boolean canFulfill() {
		return true;
	}

	@Override
	public void fulfill() {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				boolean result = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().closeAllEditors(declaration.save());
				
				if (!result){
					throw new RequirementException("Some editors remained open");
				}
			}
		});
	}

	@Override
	public void setDeclaration(CloseAllEditors declaration) {
		this.declaration = declaration;
	}
	
	@Override
	public void cleanUp() {
		// nothing to do
	}
}
