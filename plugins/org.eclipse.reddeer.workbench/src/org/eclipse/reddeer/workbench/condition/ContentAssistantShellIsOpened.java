/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.workbench.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.handler.ShellHandler;
import org.eclipse.reddeer.core.lookup.ShellLookup;
import org.eclipse.reddeer.swt.api.Table;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;

/**
 * Checks if content assistant shell is open.
 * @author rawagner
 *
 */
public class ContentAssistantShellIsOpened extends AbstractWaitCondition {

    private List<Shell> previousShells;
    private Table table = null;
    private Shell resultShell;

    /**
     * Default constructor.
     * @param previousShells shells which have been opened
     * before calling action to open content assistant
     */
    public ContentAssistantShellIsOpened(final Shell[] previousShells) {
        this.previousShells = new ArrayList<Shell>(Arrays.asList(previousShells));
    }

    /* (non-Javadoc)
     * @see org.eclipse.reddeer.common.condition.WaitCondition#test()
     */
    @Override
    public final boolean test() {
        List<Shell> s2List = new ArrayList<Shell>(Arrays.asList(ShellLookup
                .getInstance().getShells()));
        s2List.removeAll(previousShells);
        // shell with javadoc can be displayed also
        if (s2List.size() == 1 || s2List.size() == 2) {
            for (Shell s : s2List) {
                ShellHandler.getInstance().setFocus(s);
                try {
                    table = new DefaultTable();
                    this.resultShell = s;
                    return true;
                } catch (CoreLayerException ex) {
                    continue;
                }
            }
        }
        return false;
    }

    /**
     * Returns content assistant table.
     * @return content assistant table
     */
    public final Table getContentAssistTable() {
        return table;
    }

    /* (non-Javadoc)
     * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#description()
     */
    @Override
    public final String description() {
        return "ContentAssistant shell is opened";
    }
    
	@SuppressWarnings("unchecked")
	@Override 
	public Shell getResult() {
		return this.resultShell;
	}

}
