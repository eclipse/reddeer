package org.jboss.reddeer.swt.impl.ctab;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.api.CTabFolder;
import org.jboss.reddeer.swt.api.CTabItem;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Abstract class for all {@link CTabFolder} implementations
 * 
 * @author Lucia Jelinkova
 *
 */
public class AbstractCTabFolder extends AbstractWidget<org.eclipse.swt.custom.CTabFolder> implements CTabFolder {

	protected AbstractCTabFolder(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(org.eclipse.swt.custom.CTabFolder.class, referencedComposite, index, matchers);
	}
	
	protected AbstractCTabFolder(org.eclipse.swt.custom.CTabFolder swtWidget){
		super(swtWidget);
	}
	
	@Override
	public CTabItem getSelection() {
		return new DefaultCTabItem(swtWidget.getSelection());
	}
}
