package org.jboss.reddeer.workbench.view;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Checks if the widget is contained in the specified view.  
 * 
 * @author Lucia Jelinkova
 *
 */
public class ViewMatcher extends TypeSafeMatcher<Widget> {

	private View view;
	
	public ViewMatcher(View view){
		this.view = view;
	}
	
	@Override
	public boolean matchesSafely(Widget item) {
		if (!(item instanceof Composite)){
			return false;
		}
		
		CTabFolder folder = findTabFolder((Composite) item);
		if (folder == null){
			return false;
		}

		CTabItem tabItem = folder.getSelection();
		if (tabItem.getText().equals(view.getTitle())){
			return true;
		}
		return false;
	}

	private CTabFolder findTabFolder(Composite composite){
		if (composite == null){
			return null;
		}

		if (composite instanceof CTabFolder) {
			return (CTabFolder) composite;
		}

		return findTabFolder(composite.getParent());
	}
	
	@Override
	public void describeTo(Description description) {
		
	}
}
