package org.jboss.reddeer.swt.test.ui.editor;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class ToolbarTestAction extends Action {

	public ToolbarTestAction() {
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		setText("Execute");
		setToolTipText("Execute task");
	}
	
	@Override
	public void run() {
		EditorState.execute();
	}
	
	@Override
	public void runWithEvent(Event event) {
		super.runWithEvent(event);
	}
	
	public void run(IAction action) {
		run();
	}
	
	
}
