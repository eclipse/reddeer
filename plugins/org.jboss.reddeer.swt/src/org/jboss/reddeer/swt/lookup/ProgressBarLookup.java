package org.jboss.reddeer.swt.lookup;

import org.eclipse.swt.widgets.ProgressBar;
import org.hamcrest.Matcher;

@Deprecated // will be removed in 1.0.0. Use AbstractWidget instead
public class ProgressBarLookup {
	
	private static ProgressBarLookup instance = null;
	

	public static ProgressBarLookup getInstance(){
		if (instance == null){
			instance = new ProgressBarLookup();
		}
		return instance;
	}
	
	@SuppressWarnings("rawtypes")
	public ProgressBar getProgressBar(int index, Matcher... matchers){
		return (ProgressBar)WidgetLookup.getInstance().activeWidget(null, ProgressBar.class, index, matchers);
	}
}
