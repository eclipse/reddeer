package org.jboss.reddeer.requirements.openperspective;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.ui.perspectives.AbstractPerspective;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.requirements.exception.RequirementsLayerException;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;

/**
 * This requirement ensures, that given perspective is active before actual test
 * execution.
 * 
 * @author rhopp
 * 
 */

public class OpenPerspectiveRequirement implements Requirement<OpenPerspective> {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface OpenPerspective {

		Class<? extends AbstractPerspective> value();
	}

	private OpenPerspective openPerspective;
	private final Logger log = Logger.getLogger(this.getClass());

	@Override
	public boolean canFulfill() {
		try {
			getPerspectiveInstance();
		} catch (InstantiationException ex) {
			throw new RequirementsLayerException(
					"There was problem initializing perspective", ex);
		} catch (IllegalAccessException e) {
			throw new RequirementsLayerException(
					"There was problem initializing perspective", e);
		} catch (EclipseLayerException ex) {
			return false; // this exception means, that perspective couldn't be
							// found
		}
		return true;
	}

	@Override
	public void fulfill() {
		try {
			getPerspectiveInstance().open();
		} catch (Exception e) {
			throw new RequirementsLayerException(
					"Unable to fullffill requirement 'Open Perspective'", e);
		}
	}

	@Override
	public void setDeclaration(OpenPerspective openPerspective) {
		this.openPerspective = openPerspective;

	}

	private AbstractPerspective getPerspectiveInstance()
			throws InstantiationException, IllegalAccessException {
		AbstractPerspective perspectiveInstance = null;
		perspectiveInstance = openPerspective.value().newInstance();
		return perspectiveInstance;
	}

}
