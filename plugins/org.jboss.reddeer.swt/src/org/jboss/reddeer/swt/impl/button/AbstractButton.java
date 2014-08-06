package org.jboss.reddeer.swt.impl.button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.platform.RunningPlatform;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.condition.WidgetIsEnabled;
import org.jboss.reddeer.swt.handler.ButtonHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.lookup.ButtonLookup;
import org.jboss.reddeer.swt.matcher.WithMnemonicTextMatcher;
import org.jboss.reddeer.swt.matcher.WithStyleMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * Basic Button class is abstract class for all Button implementations
 * 
 * @author Jiri Peterka
 * 
 */
public abstract class AbstractButton implements Button {

	private static final Logger log = Logger.getLogger(AbstractButton.class);

	protected org.eclipse.swt.widgets.Button swtButton;
	
	@SuppressWarnings("rawtypes")
	protected AbstractButton (ReferencedComposite refComposite, int index , String text, int style, Matcher... matchers){
		
        log.debug("Searching for Button:"
                + "\n  index: " + index
                + "\n  label: " + text
                + "\n  style: " + style);
        
        
		List<Matcher> list= new ArrayList<Matcher>();
		if (text != null && !text.isEmpty()) {
			list.add(new WithMnemonicTextMatcher(text));
		}
		list.add(new WithStyleMatcher(style));			
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
		log.info("Click button " + getDescriptiveText());
		new WaitUntil(new WidgetIsEnabled(this));
		ButtonHandler.getInstance().click(swtButton);
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
	/**
	* Returns some text identification of button - 
	* either text, tooltip text or info that no text is available. 
	* Used in logging. 
	*/
	protected String getDescriptiveText() {
		return getText() != null ? getText() : (
				getToolTipText() != null ? getToolTipText()
				: "with no text or tooltip");
	}
}
