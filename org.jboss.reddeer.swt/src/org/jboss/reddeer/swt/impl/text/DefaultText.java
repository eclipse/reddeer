package org.jboss.reddeer.swt.impl.text;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.allOf;

import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.util.Bot;

/**
 * Text with label implementation
 * @author Jiri Peterka
 *
 */
public class DefaultText extends AbstractText implements Text {

	/**
	 * Default text given by it's text
	 * @param label
	 */
	public DefaultText(String text) {
		botText = Bot.get().text(text);
	}
	
	/**
	 * Text with given index in given Group
	 * @param index of text
	 * @param inGroup in group
	 */
	public DefaultText(String inGroup, int index){
		botText = Bot.get().textInGroup(inGroup, index);
	}
	/**
	 * Text with given text in given Group
	 * @param text of text
	 * @param inGroup in group
	 */
	public DefaultText(String inGroup, String text){
		botText = Bot.get().textInGroup(text, inGroup);
	}
	
	/**
	 * Default text given by it's index
	 * @param index
	 */
	public DefaultText(int index) {
		botText = Bot.get().text(index);
	}
	
	public DefaultText(Matcher<Widget>... matchers){
		botText = new SWTBotText((org.eclipse.swt.widgets.Text) Bot.get().widget(allOf(matchers)));
	}
}
