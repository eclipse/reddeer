package org.jboss.reddeer.swt.lookup.impl;

import org.hamcrest.Matcher;
import org.eclipse.swt.widgets.ProgressBar;

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
		return (ProgressBar)WidgetLookup.getInstance().activeWidget(ProgressBar.class, index, matchers);
	}
}
