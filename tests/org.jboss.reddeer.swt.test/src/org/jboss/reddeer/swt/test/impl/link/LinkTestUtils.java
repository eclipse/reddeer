/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.swt.test.impl.link;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;

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
