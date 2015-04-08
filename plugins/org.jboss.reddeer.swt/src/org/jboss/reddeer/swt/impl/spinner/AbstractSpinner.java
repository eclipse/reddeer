package org.jboss.reddeer.swt.impl.spinner;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Spinner;
import org.jboss.reddeer.core.handler.SpinnerHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Abstract class for all Spinner implementations
 * 
 * @author Andrej Podhradsky
 * 
 */
public abstract class AbstractSpinner extends AbstractWidget<org.eclipse.swt.widgets.Spinner> implements Spinner {

	private static final Logger log = Logger.getLogger(AbstractSpinner.class);
	
	protected AbstractSpinner(ReferencedComposite refComposite, int index, Matcher<?>... matchers) {
		super(org.eclipse.swt.widgets.Spinner.class, refComposite, index, matchers);
	}

	@Override
	public int getValue() {
		return SpinnerHandler.getInstance().getValue(getSWTWidget());
	}

	@Override
	public void setValue(int value) {
		log.info("Set spinner value to " + value);
		SpinnerHandler.getInstance().setValue(getSWTWidget(), value);
	}
}
