/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.jface.dialogs;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.handler.CompositeHandler;
import org.eclipse.reddeer.jface.window.AbstractWindow;
import org.eclipse.reddeer.jface.window.Openable;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.impl.label.DefaultLabel;
import org.eclipse.reddeer.swt.impl.text.DefaultText;
import static org.eclipse.jface.dialogs.Dialog.DLG_IMG_MESSAGE_ERROR;
import static org.eclipse.jface.dialogs.Dialog.DLG_IMG_MESSAGE_WARNING;
import static org.eclipse.jface.dialogs.Dialog.DLG_IMG_MESSAGE_INFO;;

/**
 * Represents Eclipse TitleAreaDialog - Shell with title and page description
 * @author rawagner
 *
 */
public class TitleAreaDialog extends AbstractWindow{
	
	private CompositeHandler handler = CompositeHandler.getInstance();
	
	/**
	 * Implementations are responsible for making sure given shell is Eclipse TitleAreaDialog.
	 * @param shell instance of Eclipse TitleAreaDialog
	 */
	public TitleAreaDialog(Shell shell) {
		super(shell);
	}
	
	/**
	 * Finds TitleAreaDialog matching given matchers. Found shell must be instance of Eclipse TitleAreaDialog
	 * @param matchers to match TitleAreaDialog
	 */
	public TitleAreaDialog(Matcher<?>... matchers) {
		super(matchers);
	}
	
	/**
	 * Finds TitleAreaDialog with given text. Found shell must be instance of Eclipse TitleAreaDialog
	 * @param text TitleAreaDialog text
	 */
	public TitleAreaDialog(String text){
		super(text);
	}
	
	public TitleAreaDialog() {
		super();
	}
	
	/**
	 * Returns current dialog page title.
	 *
	 * @return the page title
	 */
	public String getTitle() {
		checkShell();
		// Page Title is 3rd Label within first Composite of TitleAreaDialog and is inactive
		Control labelControl = handler.getChildren(getShellComposite())[2];
		return new DefaultLabel((Label)labelControl).getText();
	}
	
	/**
	 * Returns current dialog page message. Message contains currently visible description or error/warning message.
	 *
	 * @return the page description
	 */
	public String getMessage() {
		checkShell();
		String message = getMessageLabel().getText();
		if(getMessageImage() != null){ //if image is shown TitleAreaDialog adds whitespace before message
			message = message.substring(1);
		}
		return message;
	}
	
	/**
	 * Return current dialog page message. 
	 * @return message type
	 */
	public MessageTypeEnum getMessageType(){
		checkShell();
		Image messageImage = getMessageImage();
		if(messageImage == null){
			return MessageTypeEnum.NONE;
		} else if(messageImage.equals(JFaceResources.getImage(DLG_IMG_MESSAGE_ERROR))){
			return MessageTypeEnum.ERROR;
		} else if (messageImage.equals(JFaceResources.getImage(DLG_IMG_MESSAGE_INFO))){
			return MessageTypeEnum.INFO;
		} else if (messageImage.equals(JFaceResources.getImage(DLG_IMG_MESSAGE_WARNING))){
			return MessageTypeEnum.WARNING;
		}
		return MessageTypeEnum.UNKNOWN;
	}
	
	/**
	 * Returns current dialog page message image.
	 * 
	 * @return message image
	 */
	public Image getMessageImage(){
		checkShell();
		// Message Image is 4th Label within first Composite of TitleAreaDialog and is inactive
		Control imageControl = handler.getChildren(getShellComposite())[3];
		return new DefaultLabel((Label)imageControl).getImage();
	}
	
	/**
	 * Returns title image
	 * @return title image
	 */
	public Image getTitleImage(){
		checkShell();
		// Title Image is 1st Label within first Composite of TitleAreaDialog and is inactive
		Control imageControl = handler.getChildren(getShellComposite())[1];
		return new DefaultLabel((Label)imageControl).getImage();
	}
	
	protected org.eclipse.reddeer.swt.api.Text getMessageLabel(){
		// Page Message is 5th Text within first Composite of TitleAreaDialog and is inactive 
		Control textControl = handler.getChildren(getShellComposite())[4];
		return new DefaultText((Text)textControl);
	}
	
	protected Composite getShellComposite(){
		return (Composite)handler.getChildren(getShell().getSWTWidget())[0];
	}
	
	@Override
	public Class<? extends org.eclipse.jface.window.Window> getEclipseClass(){
		return org.eclipse.jface.dialogs.TitleAreaDialog.class;
	}

	@Override
	public Openable getDefaultOpenAction() {
		return null;
	}
	
	

}
