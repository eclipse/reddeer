package org.jboss.reddeer.swt.impl.button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.platform.RunningPlatform;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.handler.ButtonHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.matcher.WithStyleMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.condition.WidgetIsEnabled;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Basic Button class is abstract class for all Button implementations
 * 
 * @author Jiri Peterka
 * 
 */
public abstract class AbstractButton extends AbstractWidget<org.eclipse.swt.widgets.Button> implements Button {

	private static final Logger log = Logger.getLogger(AbstractButton.class);

	protected AbstractButton (ReferencedComposite refComposite, int index, int style, Matcher<?>... matchers){
        super(org.eclipse.swt.widgets.Button.class, refComposite, index, createMatchers(style, matchers));

        if (RunningPlatform.isWindows() &&
                ((WidgetHandler.getInstance().getStyle(swtWidget) & SWT.RADIO) != 0)){
                // do not set focus because it also select radio button on Windows
        } else{
        	WidgetHandler.getInstance().setFocus(swtWidget);        
        }   
	}
	
	private static Matcher<?>[] createMatchers(int style, Matcher<?>... matchers) {
		List<Matcher<?>> list= new ArrayList<Matcher<?>>();

		list.add(new WithStyleMatcher(style));			
		list.addAll(Arrays.asList(matchers));
		return list.toArray(new Matcher[list.size()]);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Button#click()
	 */
	@Override
	public void click() {
		log.info("Click button " + getDescriptiveText());
		new WaitUntil(new WidgetIsEnabled(this));
		ButtonHandler.getInstance().click(swtWidget);
	}
	
	/**
	 * See {@link Button}.
	 *
	 * @return the text
	 */
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtWidget);
	}
	
	/**
	 * See {@link Button}.
	 *
	 * @return the tool tip text
	 */
	@Override
	public String getToolTipText() {
		return WidgetHandler.getInstance().getToolTipText(swtWidget);
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
