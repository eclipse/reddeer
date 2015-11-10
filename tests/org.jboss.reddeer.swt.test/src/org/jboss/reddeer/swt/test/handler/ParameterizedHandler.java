package org.jboss.reddeer.swt.test.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class ParameterizedHandler extends AbstractHandler {

	private static boolean toggledA = false;
	private static boolean toggledB = false;
	
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String parameter = event.getParameter("RedDeerParameterID");
		if (parameter.equals("A")){
			toggledA = true;
		}else if(parameter.equals("B")){
			toggledB = true;
		}
		return null;
	}
	
	public static boolean isToggledA() {
		return toggledA;
	}
	
	public static boolean isToggledB() {
		return toggledB;
	}
	

}
