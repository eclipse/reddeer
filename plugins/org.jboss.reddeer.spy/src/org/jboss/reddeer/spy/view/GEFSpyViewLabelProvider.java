package org.jboss.reddeer.spy.view;

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
