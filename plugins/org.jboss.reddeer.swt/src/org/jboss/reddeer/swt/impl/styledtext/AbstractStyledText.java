/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.swt.impl.styledtext;

import org.eclipse.swt.graphics.Point;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.StyledText;
import org.jboss.reddeer.core.handler.StyledTextHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Abstract Styled Text contains common rutines for styled text widget.
 * Concrete impementations of styledtext should extend this class.
 * @author rawagner
 */
public abstract class AbstractStyledText extends AbstractWidget<org.eclipse.swt.custom.StyledText> implements StyledText {
    
    /**
     * Logger.
     */
    private static final Logger log = Logger.getLogger(AbstractStyledText.class);

    protected AbstractStyledText(ReferencedComposite refComposite, int index, Matcher<?>... matchers) {
		super(org.eclipse.swt.custom.StyledText.class, refComposite, index, matchers);
	}
    
    /**
     * Returns styledtext text.
     * @return text of this StyledText
     */
    @Override
    public String getText() {
        String text = WidgetHandler.getInstance().getText(swtWidget);
        return text;
    }

    /**
     * Sets text of this StyledText.
     * @param text to set
     */
    @Override
    public void setText(final String text) {
        log.info("Styled Text set to: " + text);
        WidgetHandler.getInstance().setText(swtWidget, text);
    }

    /**
     * Returns tooltip text.
     * @return Tooltip text of this StyledText
     */
    @Override
    public String getToolTipText() {
        String tooltipText = WidgetHandler.getInstance().getToolTipText(swtWidget);
        return tooltipText;
    }

    /**
     * Insert text into styled text content.
     *
     * @param line line to insert text
     * @param column column to insert text
     * @param text the text
     * @Param text to insert
     */
    @Override
    public void insertText(final int line, final int column, final String text) {
    	log.info("Insert text into styled text on line " + line + ", column " + column + ": " + text);
        StyledTextHandler.getInstance().insertText(swtWidget, line, column, text);
    }

    /**
     * Insert text.
     *
     * @param text the text
     * @see org.jboss.reddeer.swt.api.StyledText#insertText(String)
     */
    @Override
    public void insertText(final String text) {
    	log.info("Insert text into styled text: " + text);
        StyledTextHandler.getInstance().insertText(swtWidget, text);
    }

    /**
     * Gets the position of text.
     *
     * @param text the text
     * @return the position of text
     * @see org.jboss.reddeer.swt.api.StyledText#getPositionOfText(String)
     */
    @Override
    public int getPositionOfText(final String text) {
        return StyledTextHandler.getInstance().getPositionOfText(swtWidget,text);
    }

    /**
     * Select text.
     *
     * @param text the text
     * @see org.jboss.reddeer.swt.api.StyledText#selectText(String)
     */
    @Override
    public void selectText(final String text) {
    	log.info("Select text " + text + " in styled text");
        StyledTextHandler.getInstance().selectText(swtWidget, text);
    }
    
    /* (non-Javadoc)
     * @see org.jboss.reddeer.swt.api.StyledText#setSelection(int, int)
     */
    @Override
    public void setSelection(final int start, final int end) {
    	log.info("Select position start: "+start+" end: "+end+" in styled text");
        StyledTextHandler.getInstance().setSelection(swtWidget, start,end);
    }

    /**
     * Select position.
     *
     * @param position the position
     * @see org.jboss.reddeer.swt.api.StyledText#selectPosition(int)
     */
    @Override
    public void selectPosition(final int position) {
    	log.info("Select position " + position + " in styled text");
        StyledTextHandler.getInstance().selectPosition(swtWidget, position);
    }

    /**
     * Gets the selection text.
     *
     * @return the selection text
     * @see org.jboss.reddeer.swt.api.StyledText#getSelectionText()
     */
    @Override
    public String getSelectionText() {
        return StyledTextHandler.getInstance().getSelectionText(swtWidget);
    }
    
    /**
     * Gets the cursor position.
     *
     * @return the cursor position
     * @see org.jboss.reddeer.swt.api.StyledText#getSelectionText()
     */
    @Override	
	public Point getCursorPosition(){
		return StyledTextHandler.getInstance().getCursorPosition(swtWidget);
	}
}
