package org.jboss.reddeer.graphiti.impl.contextbutton;

import java.util.List;

import org.eclipse.graphiti.tb.IContextButtonEntry;
import org.jboss.reddeer.graphiti.api.ContextButton;
import org.jboss.reddeer.graphiti.handler.ContextButtonHandler;

/**
 * Abstract class for ContextButton implementation.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public abstract class AbstractContextButton implements ContextButton {

	protected IContextButtonEntry contextButtonEntry;

	public AbstractContextButton(IContextButtonEntry contextButtonEntry) {
		this.contextButtonEntry = contextButtonEntry;
	}

	@Override
	public void click() {
		ContextButtonHandler.getInstance().click(contextButtonEntry);
	}

	@Override
	public String getText() {
		return ContextButtonHandler.getInstance().getText(contextButtonEntry);
	}
	
	@Override
	public List<ContextButton> getContextButtons() {
		return ContextButtonHandler.getInstance().getContextButtons(contextButtonEntry);
	}

}
