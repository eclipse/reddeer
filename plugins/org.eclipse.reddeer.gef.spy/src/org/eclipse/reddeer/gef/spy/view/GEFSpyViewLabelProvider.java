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
package org.eclipse.reddeer.gef.spy.view;

import org.eclipse.draw2d.IFigure;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * Label provider for GEFSpy View.
 * 
 * @author apodhrad
 *
 */
public class GEFSpyViewLabelProvider extends LabelProvider {

	@Override
	public String getText(Object obj) {
		if (obj instanceof TreeNode) {
			return ((TreeNode) obj).getValue().getClass().toString();
		}
		return obj.toString();
	}

	@Override
	public Image getImage(Object obj) {
		String imageKey = ISharedImages.IMG_OBJ_FOLDER;
		if (obj instanceof TreeNode) {
			Object treeObject = ((TreeNode) obj).getValue();
			if (treeObject instanceof IFigure) {
				imageKey = ISharedImages.IMG_OBJ_FILE;
			}
		}
		return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	}
}
