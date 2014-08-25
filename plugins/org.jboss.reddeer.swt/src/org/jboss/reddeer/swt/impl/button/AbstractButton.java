package org.jboss.reddeer.swt.impl.button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.platform.RunningPlatform;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.condition.WidgetIsEnabled;
import org.jboss.reddeer.swt.handler.ButtonHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.matcher.WithMnemonicTextMatcher;
import org.jboss.reddeer.swt.matcher.WithStyleMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Basic Button class is abstract class for all Button implementations
 * 
 * @author Jiri Peterka
 * 
 */
public abstract class AbstractButton extends AbstractWidget<org.eclipse.swt.widgets.Button> implements Button {

	private static final Logger log = Logger.getLogger(AbstractButton.class);

	/**
	 * @deprecated Since 1.0.0. This is not a standard widget constructor.
	 * @param refComposite
	 * @param index
	 * @param text
	 * @param style
	 * @param matchers
	 */
	@SuppressWarnings("rawtypes")
	protected AbstractButton (ReferencedComposite refComposite, int index , String text, int style, Matcher... matchers){
        super(org.eclipse.swt.widgets.Button.class, refComposite, index, createMatchers(text, style, matchers));

        if (RunningPlatform.isWindows() &&
                ((WidgetHandler.getInstance().getStyle(swtWidget) & SWT.RADIO) != 0)){
                // do not set focus because it also select radio button on Windows
        } else{
        	WidgetHandler.getInstance().setFocus(swtWidget);        
        }   
	}

	protected AbstractButton (ReferencedComposite refComposite, int index, int style, Matcher<?>... matchers){
        super(org.eclipse.swt.widgets.Button.class, refComposite, index, createMatchers(style, matchers));

        if (RunningPlatform.isWindows() &&
                ((WidgetHandler.getInstance().getStyle(swtWidget) & SWT.RADIO) != 0)){
                // do not set focus because it also select radio button on Windows
        } else{
        	WidgetHandler.getInstance().setFocus(swtWidget);        
        }   
	}
	
	private static Matcher<?>[] createMatchers(String text, int style, Matcher<?>... matchers) {
		List<Matcher<?>> list= new ArrayList<Matcher<?>>();
		if (text != null && !text.isEmpty()) {
			list.add(new WithMnemonicTextMatcher(text));
		}
		list.add(new WithStyleMatcher(style));			
		list.addAll(Arrays.asList(matchers));

		return list.toArray(new Matcher[list.size()]);
	}
	
	private static Matcher<?>[] createMatchers(int style, Matcher<?>... matchers) {
		List<Matcher<?>> list= new ArrayList<Matcher<?>>();

		list.add(new WithStyleMatcher(style));			
		list.addAll(Arrays.asList(matchers));
		return list.toArray(new Matcher[list.size()]);
	}
	
	@Override
	public void click() {
		log.info("Click button " + getDescriptiveText());
		new WaitUntil(new WidgetIsEnabled(this));
		ButtonHandler.getInstance().click(swtWidget);
	}
	
	/**
	 * See {@link Button}
	 */
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtWidget);
	}
	
	/**
	 * See {@link Button}
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
