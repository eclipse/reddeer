package org.jboss.reddeer.swt.test.handler;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.actions.ActionDelegate;

public class ViewActionWithId extends ActionDelegate implements IViewActionDelegate {

	

	private static boolean toggle = false;
	
	public void init(IViewPart view) {
	}

	@Override
	public void run(IAction action) {
		toggle=true;
	}
	
	public static boolean isToggled() {
		return toggle;
	}

}
