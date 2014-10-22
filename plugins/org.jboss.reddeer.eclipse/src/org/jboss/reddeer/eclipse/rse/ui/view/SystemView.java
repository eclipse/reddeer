package org.jboss.reddeer.eclipse.rse.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.rse.ui.wizard.NewConnectionWizardDialog;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;

/**
 * This class represents the Remote Systems view.
 * @author Pavol Srna
 *
 */
public class SystemView extends WorkbenchView {

	public static final String TITLE = "Remote Systems";
	
	private static final Logger log = Logger.getLogger(SystemView.class);
	
	/**
	 * Constructs the view with {@value # TITLE}.
	 */
	public SystemView() {
		super(TITLE);
	}
	
	/**
	 * Open and return New Connection wizard dialog
	 * @return NewConnectionWizardDialog
	 */
	public NewConnectionWizardDialog newConnection(){
		log.info("Creating new connection");
		activate();
		getSystem("Local").select();
		new ContextMenu("New","Connection...").select();
		new DefaultShell("New Connection");
		
		return new NewConnectionWizardDialog();
	}

	/**
	 * Get list of all Remote Systems in tree
	 * @return List<System>
	 */
	public List<System> getSystems(){
		List<System> systems = new ArrayList<System>();

		Tree tree;
		try {
			tree = getSystemTree();
		} catch (SWTLayerException e){
			return new ArrayList<System>();
		}
		for (TreeItem item : tree.getItems()){
			systems.add(createSystem(item));
		}
		return systems;
	}

	/**
	 * Get Remote System specified by it's name
	 * @param name of the system to be returned
	 * @return System
	 * @throws EclipseLayerException if no remote system found
	 */
	public System getSystem(String name){
		for (System system : getSystems()){
			if (system.getLabel().equals(name)){
				return system;
			}
		}
		throw new EclipseLayerException("There is no remote system with name " + name);
	}

	protected Tree getSystemTree(){
		activate();
		return new DefaultTree();
	}

	protected System createSystem(TreeItem item){
		return new System(item);
	}
}
