package org.jboss.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;
import org.jboss.reddeer.swt.api.Button;

/**
 * PushButton is simple button implementation that can be pushed
 * @author Jiri Peterka
 *
 */
public class PushButton extends AbstractButton implements Button {

	/**
	 * Push button with given text
	 * @param text
	 */
	public PushButton(String text) {
		this(0,text);
	}
	/**
	 * Push button with given index
	 * @param index
	 */
	public PushButton(int index) {
		this(index,"");
	}
	/**
	 * Push Button with given index and text
	 * @param index
	 * @param text
	 */
	public PushButton (int index , String text){
		super(index,text,SWT.PUSH);
	}
	
}
