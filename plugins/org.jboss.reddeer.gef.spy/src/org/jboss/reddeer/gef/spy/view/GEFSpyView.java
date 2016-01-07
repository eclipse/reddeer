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
package org.jboss.reddeer.gef.spy.view;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.jboss.reddeer.core.lookup.EditorPartLookup;
import org.jboss.reddeer.gef.handler.EditPartHandler;
import org.jboss.reddeer.gef.lookup.ViewerLookup;
import org.jboss.reddeer.gef.spy.TreeNodeExt;

/**
 * GEFSpy View.
 * 
 * @author apodhrad
 *
 */
public class GEFSpyView extends ViewPart {

	private TreeViewer treeViewer;

	@Override
	public void createPartControl(Composite parent) {
		treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		treeViewer.setContentProvider(new TreeNodeContentProvider());
		treeViewer.setLabelProvider(new GEFSpyViewLabelProvider());

		refresh(treeViewer);
		hookRefreshAction();
		hookSelectAction();
	}

	/**
	 * 
	 * @return
	 */
	public TreeViewer getTreeViewer() {
		return treeViewer;
	}

	/**
	 * Refreshes a given tree viewer with all edit parts (or figures) if a GEF editor is available. Otherwise, it throws
	 * a warning about that.
	 * 
	 * @param treeViewer
	 *            Tree viewer
	 */
	protected void refresh(TreeViewer treeViewer) {
		EditPart editPart = null;

		try {
			IEditorPart editorPart = EditorPartLookup.getInstance().getEditor();
			editPart = ViewerLookup.getInstance().findGraphicalViewer(editorPart).getContents();
		} catch (Exception ex) {
			treeViewer.setInput(new TreeNode[] {});
			MessageDialog.openWarning(treeViewer.getControl().getShell(), "GEF View", "No GEF editor was detected.");
			return;
		}

		treeViewer.setInput(createTree(editPart));
		treeViewer.refresh();
	}

	/**
	 * Creates a tree for a given edit part.
	 * 
	 * @param editPart
	 *            Edit part
	 * @return Root node of created tree
	 */
	protected TreeNode[] createTree(EditPart editPart) {
		TreeNodeExt root = new TreeNodeExt(editPart);
		createTree(root);
		return new TreeNode[] { root };
	}

	/**
	 * Creates a tree for a given tree node according to its value (edit part/figure).
	 * 
	 * @param node
	 *            Tree node
	 */
	protected void createTree(TreeNodeExt node) {
		Object obj = node.getValue();
		if (obj instanceof EditPart) {
			EditPart editPart = (EditPart) obj;
			List<Object> children = editPart.getChildren();
			for (Object child : children) {
				TreeNodeExt newNode = new TreeNodeExt(child);
				createTree(newNode);
				node.addChild(newNode);
			}
			if (editPart instanceof GraphicalEditPart) {
				IFigure figure = ((GraphicalEditPart) editPart).getFigure();
				TreeNodeExt newNode = new TreeNodeExt(figure);
				createTree(newNode);
				node.addChild(newNode);
			}
		}
		if (obj instanceof IFigure) {
			IFigure figure = (IFigure) obj;
			List<Object> children = figure.getChildren();
			for (Object child : children) {
				TreeNodeExt newNode = new TreeNodeExt(child);
				createTree(newNode);
				node.addChild(newNode);
			}
		}
	}

	private void hookRefreshAction() {
		Action refreshAction = new Action() {
			public void run() {
				refresh(treeViewer);
			}
		};
		refreshAction.setText("Refresh");
		refreshAction.setToolTipText("Refresh");
		refreshAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_ELCL_SYNCED));

		IActionBars bars = getViewSite().getActionBars();
		bars.getToolBarManager().add(refreshAction);
		bars.getToolBarManager().add(new Separator());

	}

	private void hookSelectAction() {
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();
				Object obj = ((IStructuredSelection) selection).getFirstElement();
				if (obj instanceof TreeNode) {
					Object treeObject = ((TreeNode) obj).getValue();
					if (treeObject instanceof EditPart && ((EditPart) treeObject).isSelectable()) {
						EditPartHandler.getInstance().select((EditPart) treeObject);
					}
				}
			}
		});
	}

	@Override
	public void setFocus() {
		// we don't need any focus
	}

}
