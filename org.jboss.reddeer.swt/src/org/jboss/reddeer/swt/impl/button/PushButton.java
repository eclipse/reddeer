package org.jboss.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

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
		this(null, 0,text);
	}
	
	/**
	 * Push button with given text inside given composite
	 * @param referencedComposite
	 * @param text
	 */
	public PushButton(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, 0,text);
	}
	/**
	 * Push button with given index
	 * @param index
	 */
	public PushButton(int index) {
		this(null, index,"");
	}
	/**
	 * Push button with given index inside given composite
	 * @param referencedComposite
	 * @param index
	 */
	public PushButton(ReferencedComposite referencedComposite,int index) {
		this(referencedComposite, index,"");
	}
	
	/**
	 * Push Button with given index and text
	 * @param index
	 * @param text
	 */
	public PushButton (int index , String text){
		super(null, index,text,SWT.PUSH);
	}
	
	/**
	 * Push Button with given index and text inside given composite
	 * @param referencedComposite
	 * @param index
	 * @param text
	 */
	public PushButton (ReferencedComposite referencedComposite, int index , String text){
		super(referencedComposite, index,text,SWT.PUSH);
	}
	
}
