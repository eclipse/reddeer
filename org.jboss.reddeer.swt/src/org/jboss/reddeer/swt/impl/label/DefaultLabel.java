package org.jboss.reddeer.swt.impl.label;

import org.jboss.reddeer.swt.util.Bot;

public class DefaultLabel extends AbstractLabel {

	public DefaultLabel() {
		System.out.println("0" + Bot.get().label(0).getText());
		System.out.println(Bot.get().label(1).getText());
		System.out.println(Bot.get().label(2).getText());
		label = Bot.get().label();
	}
}
