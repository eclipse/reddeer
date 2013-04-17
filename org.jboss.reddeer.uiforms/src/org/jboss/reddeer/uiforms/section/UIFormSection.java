package org.jboss.reddeer.uiforms.section;

import org.eclipse.ui.forms.widgets.Section;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.matcher.TextMatcher;
import org.jboss.reddeer.swt.reference.ReferenceComposite;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.uiforms.lookup.UIFormSectionLookup;

/**
 * UIFormSection is Section object located in Eclipse Forms
 * 
 * @author jjankovi
 *
 */
public class UIFormSection implements ReferencedComposite {

	private Section section;
	
	/**
	 * Create UIFormSection 
	 */
	public UIFormSection() {
		this(0);
	}
	
	/**
	 * Create UIFormSection with given index
	 * @param index
	 */
	public UIFormSection(int index) {
		this(index, null);
	}
	
	/**
	 * Create UIFormSection with given toolTipText
	 * @param toolTipText
	 */
	public UIFormSection(String text) {
		this(0, text);
	}
	
	/**
	 * Create UIFormSection with given toolTipText and index
	 * @param toolTipText
	 * @param index
	 */
	public UIFormSection(int index, String text) {
		if (text != null && !text.isEmpty()) {
			section = UIFormSectionLookup.getInstance().getSection(index, new TextMatcher(text));
		}else {
			section = UIFormSectionLookup.getInstance().getSection(index);
		}
		
		setFocus();
		setAsReference();
	}
	
	/**
	 * Text of UIFormSection
	 * @return
	 */
	public String getText() {
		return WidgetHandler.getInstance().getText(section);
	}

	@Override
	public void setAsReference() {
		ReferenceComposite.setComposite(section);
	}
	
	private void setFocus() {
		WidgetHandler.getInstance().setFocus(section);
	}
	
}
