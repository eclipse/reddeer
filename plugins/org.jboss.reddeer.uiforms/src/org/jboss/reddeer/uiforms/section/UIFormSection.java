package org.jboss.reddeer.uiforms.section;


import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.Section;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.matcher.WithMnemonicTextMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.uiforms.handler.SectionHandler;
import org.jboss.reddeer.uiforms.impl.section.DefaultSection;
import org.jboss.reddeer.uiforms.lookup.SectionLookup;

/**
 * UIFormSection is Section object located in Eclipse Forms
 * 
 * @author jjankovi
 * @deprecated use {@link DefaultSection}
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
	 * Create UIFormSection inside given composite
	 * @param referencedComposite 
	 */
	public UIFormSection(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Create UIFormSection with given index
	 * @param index
	 */
	public UIFormSection(int index) {
		this(index, null);
	}
	
	/**
	 * Create UIFormSection with given index inside given composite
	 * @param referencedComposite
	 * @param index
	 */
	public UIFormSection(ReferencedComposite referencedComposite, int index) {
		this(referencedComposite, index, null);
	}
	
	/**
	 * Create UIFormSection with given toolTipText
	 * @param toolTipText
	 */
	public UIFormSection(String text) {
		this(0, text);
	}
	
	/**
	 * Create UIFormSection with given toolTipText inside given composite
	 * @param referencedComposite
	 * @param toolTipText
	 */
	public UIFormSection(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, 0, text);
	}
	
	/**
	 * Create UIFormSection with given toolTipText and index
	 * @param toolTipText
	 * @param index
	 */
	public UIFormSection(int index, String text) {
		if (text != null && !text.isEmpty()) {
			section = SectionLookup.getInstance().getSection(null, index,
					new WithMnemonicTextMatcher(text));
		}else {
			section = SectionLookup.getInstance().getSection(null, index);
		}
		
		setFocus();
	}
	
	/**
	 * Create UIFormSection with given toolTipText and index inside given composite
	 * @param referencedComposite
	 * @param toolTipText
	 * @param index
	 */
	public UIFormSection(ReferencedComposite referencedComposite, int index, String text) {
		if (text != null && !text.isEmpty()) {
			section = SectionLookup.getInstance().getSection(
					referencedComposite, index,
					new WithMnemonicTextMatcher(text));
		}else {
			section = SectionLookup.getInstance().getSection(referencedComposite, index);
		}
		
		setFocus();
	}
	
	/**
	 * Text of UIFormSection
	 * @return
	 */
	public String getText() {
		return WidgetHandler.getInstance().getText(section);
	}
	
	private void setFocus() {
		WidgetHandler.getInstance().setFocus(section);
	}

	@Override
	public Control getControl() {
		return section;
	}

	public void setExpanded(final boolean expanded) {
		SectionHandler.getInstance().setExpanded(section, expanded);
	}
}
