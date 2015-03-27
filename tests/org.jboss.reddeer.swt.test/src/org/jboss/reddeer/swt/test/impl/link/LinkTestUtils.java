package org.jboss.reddeer.swt.test.impl.link;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;

public class LinkTestUtils {

	public static Link createLink(final Shell parent, final String text){
		return Display.syncExec(new ResultRunnable<Link>() {
			@Override
			public Link run() {
				Link link = new Link(parent, SWT.NONE);
				link.setSize(100, 30);
				link.setText(text);
				return link;
			}
		});
	}
}
