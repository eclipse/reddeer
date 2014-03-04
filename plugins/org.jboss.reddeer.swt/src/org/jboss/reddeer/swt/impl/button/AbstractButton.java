package org.jboss.reddeer.swt.impl.button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.hamcrest.Matcher;
import org.jboss.reddeer.direct.platform.RunningPlatform;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.condition.WidgetIsEnabled;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.lookup.ButtonLookup;
import org.jboss.reddeer.swt.matcher.StyleMatcher;
import org.jboss.reddeer.swt.matcher.WithMnemonicMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * Basic Button class is abstract class for all Button implementations
 * 
 * @author Jiri Peterka
 * 
 */
public abstract class AbstractButton implements Button {

	protected final Logger log = Logger.getLogger(this.getClass());

	protected org.eclipse.swt.widgets.Button swtButton;
	
	@SuppressWarnings("rawtypes")
	protected AbstractButton (ReferencedComposite refComposite, int index , String text, int style, Matcher... matchers){
		
        log.info("Searching for Button:"
                + "\n  index: " + index
                + "\n  label: " + text
                + "\n  style: " + style);
        
        
		List<Matcher> list= new ArrayList<Matcher>();
		if (text != null && !text.isEmpty()) {
			list.add(new WithMnemonicMatcher(text));
		}
		list.add(new StyleMatcher(style));			
		list.addAll(Arrays.asList(matchers));

		Matcher[] newMatchers = list.toArray(new Matcher[list.size()]);
		
        swtButton = ButtonLookup.getInstance().getButton(refComposite,index,newMatchers);

        if (RunningPlatform.isWindows() &&
                ((WidgetHandler.getInstance().getStyle(swtButton) & SWT.RADIO) != 0)){
                // do not set focus because it also select radio button on Windows
        } else{
        	WidgetHandler.getInstance().setFocus(swtButton);        
        }   
	}

	@Override
	public void click() {
		log.info("Click on the button "
				+ (getText() != null ? getText() : (
						getToolTipText() != null ? getToolTipText()
						: "with no text or tooltip")));
		new WaitUntil(new WidgetIsEnabled(this));
		WidgetHandler.getInstance().click(swtButton);
	}
	
	/**
	 * See {@link Button}
	 */
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtButton);
	}
	
	/**
	 * See {@link Button}
	 */
	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(swtButton);
	}
	
	/**
	 * See {@link Button}
	 */
	@Override
	public String getToolTipText() {
		return WidgetHandler.getInstance().getToolTipText(swtButton);
	}

	public org.eclipse.swt.widgets.Button getSWTWidget(){
		return swtButton;
	}
}
