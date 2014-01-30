package org.jboss.reddeer.swt.test.ui.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.part.EditorPart;

public class ToolBarEditor extends EditorPart implements IEditorInput{

	private Text contents;
    private ToolBarManager tbm;

	public void createPartControl(Composite parent) {
		
		parent.setLayout(new GridLayout(1,false));
		
		// Create toolbar
	     ToolBar bar = new ToolBar( parent, SWT.HORIZONTAL );
         bar.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

         tbm = new ToolBarManager( bar );
         
         ToolbarTestAction a = new ToolbarTestAction();
         
         ActionContributionItem item = new ActionContributionItem( a );
         tbm.add( item );
         tbm.add(new Separator());

         tbm.update(true);         
         
         //Other content
         contents = new Text(parent, SWT.NONE);
         contents.setText("Editor with toolbar example");

         
	}

	public void init(IEditorSite site, IEditorInput input) {

		setPartName("editor-with-toolbar");
		setSite(site);
		setInput(this);
	}

	public void setFocus() {
		contents.setFocus();
	}

	@Override
	public void doSave(IProgressMonitor arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		  if ( tbm != null )
              tbm.dispose();
	}


	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "editor-with-toolbar";
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToolTipText() {
		return "editor-with-toolbar";
	}


}