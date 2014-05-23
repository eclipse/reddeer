package org.jboss.reddeer.eclipse.wst.server.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.ui.IServerModule;
import org.jboss.reddeer.eclipse.condition.ServerExists;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.wst.server.ui.editor.ServerEditor;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerPublishState;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerState;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.ModifyModulesDialog;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;

/**
 * Represents a server on {@link ServersView}. Contains both, the server data
 * (name, state, status) and operations that can be invoked on server (Start,
 * Stop, Delete etc.). For operations that can be invoked on project added to
 * server see {@link ServerModule}
 * 
 * @author Lucia Jelinkova
 * 
 */
public class Server {

	private static final TimePeriod TIMEOUT = TimePeriod.VERY_LONG;

	private static final String ADD_AND_REMOVE = "Add and Remove...";

	private static final Logger log = Logger.getLogger(Server.class);

	protected TreeItem treeItem;

	public Server(TreeItem treeItem) {
		this.treeItem = treeItem;
	}

	/**
	 * Returns a server label.
	 * 
	 * @return Server label
	 */
	public ServerLabel getLabel(){
		return new ServerLabel(treeItem);
	}

	/**
	 * Returns list of modules.
	 * 
	 * @return List of modules
	 */
	public List<ServerModule> getModules() {
		final List<ServerModule> modules = new ArrayList<ServerModule>();

		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				for (TreeItem item : treeItem.getItems()){
					org.eclipse.swt.widgets.TreeItem swtItem = item.getSWTWidget();
					Object data = swtItem.getData();
					if (data instanceof IModule || data instanceof IServerModule){
						modules.add(createServerModule(item));
					}
				}				
			}
		});

		return modules;
	}

	/**
	 * Return a module with a given name.
	 * 
	 * @param name Module name
	 * @return Module
	 */
	public ServerModule getModule(String name) {
		for (ServerModule module : getModules()){
			if (module.getLabel().getName().equals(name)){
				return module;
			}
		}
		throw new EclipseLayerException("There is no module with name " + name + " on server " + getLabel().getName());
	}

	/**
	 * Opens a dialog for adding/removing modules.
	 * 
	 * @return Dialog for adding/removing modules
	 */
	public ModifyModulesDialog addAndRemoveModules() {
		select();
		new ContextMenu(ADD_AND_REMOVE).select();
		return new ModifyModulesDialog();
	}
	
	/**
	 * Opens the server editor.
	 * 
	 * @return Server editor
	 */
	public ServerEditor open() {
		select();
		new ContextMenu("Open").select();
		return createServerEditor(getLabel().getName());
	}

	/**
	 * Starts the server.
	 */
	public void start() {
		log.info("Starting server " + getLabel().getName());
		if (!ServerState.STOPPED.equals(getLabel().getState())){
			throw new ServersViewException("Cannot start server because it is not stopped");
		}
		operateServerState("Start", ServerState.STARTED);
	}

	/**
	 * Debugs the server.
	 */
	public void debug() {
		log.info("Starting server in debug" + getLabel().getName());
		if (!ServerState.STOPPED.equals(getLabel().getState())){
			throw new ServersViewException("Cannot debug server because it is not stopped");
		}
		operateServerState("Debug", ServerState.DEBUGGING);
	}

	/**
	 * Profiles the server.
	 */
	public void profile() {
		log.info("Starting server in profiling mode" + getLabel().getName());
		if (!ServerState.STOPPED.equals(getLabel().getState())){
			throw new ServersViewException("Cannot profile server because it is not stopped");
		}
		operateServerState("Profile", ServerState.PROFILING);
	}

	/**
	 * Restarts the server.
	 */
	public void restart() {
		log.info("Restarting server " + getLabel().getName());
		if (!getLabel().getState().isRunningState()){
			throw new ServersViewException("Cannot restart server because it is not running");
		}
		operateServerState("Restart", ServerState.STARTED);
	}

	/**
	 * Restarts the server in debug.
	 */
	public void restartInDebug() {
		log.info("Restarting server in debug" + getLabel().getName());
		if (!getLabel().getState().isRunningState()){
			throw new ServersViewException("Cannot restart server in debug because it is not running");
		}
		operateServerState("Restart in Debug", ServerState.DEBUGGING);
	}

	/**
	 * Restarts the server in profile.
	 */
	public void restartInProfile() {
		log.info("Restarting server in profile" + getLabel().getName());
		if (!getLabel().getState().isRunningState()){
			throw new ServersViewException("Cannot restart server in profile because it is not running");
		}
		operateServerState("Restart in Profile", ServerState.PROFILING);
	}

	/**
	 * Stops the server.
	 */
	public void stop() {
		log.info("Stopping server " + getLabel().getName());
		ServerState state = getLabel().getState();
		if (!ServerState.STARTING.equals(state) && !state.isRunningState()){
			throw new ServersViewException("Cannot stop server because it not running");
		}
		operateServerState("Stop", ServerState.STOPPED);
	}

	/**
	 * Publishes the server.
	 */
	public void publish() {
		log.info("Publishing server " + getLabel().getName());
		select();
		new ContextMenu("Publish").select();
		waitForPublish();
	}

	/**
	 * Cleans the server.
	 */
	public void clean() {
		log.info("Cleaning server " + getLabel().getName());
		select();
		new ContextMenu("Clean...").select();
		new DefaultShell("Server");
		new PushButton("OK").click();
		waitForPublish();
	}

	/**
	 * Deletes the server. The server is not stop first.
	 */
	public void delete() {
		delete(false);
	}

	/**
	 * Deletes the server. You can specify whether the server is stop first.
	 * 
	 * @param stopFirst Indicates whether the server is stop first.
	 */
	public void delete(boolean stopFirst) {
		final String name = getLabel().getName();
		log.info("Deleting server " + name + ". Stopping server first: " + stopFirst);
		select();
		ServerState state = getLabel().getState();

		new ContextMenu("Delete").select();	
		new WaitUntil(new ShellWithTextIsActive("Delete Server"),TimePeriod.NORMAL);
		if (!ServerState.STOPPED.equals(state) && !ServerState.NONE.equals(state)){
			new CheckBox().toggle(stopFirst);
		}
		new PushButton("OK").click();
		new WaitWhile(new ServerExists(name), TIMEOUT);
		new WaitWhile(new JobIsRunning(), TIMEOUT);
	}

	/**
	 * Selects the server.
	 */
	protected void select() {
		treeItem.select();
	}

	protected void operateServerState(String menuItem, ServerState resultState){
		select();
		new ContextMenu(menuItem).select();
		new WaitUntil(new JobIsRunning(), TIMEOUT);
		new WaitUntil(new ServerStateCondition(resultState), TIMEOUT);
		new WaitWhile(new JobIsRunning(), TIMEOUT);

		//check if the server has expected state after jobs are done
		new WaitUntil(new ServerStateCondition(resultState), TIMEOUT);
	}

	protected void waitForPublish(){
		new WaitUntil(new JobIsRunning(), TIMEOUT);
		new WaitWhile(new ServerPublishStateCondition(ServerPublishState.PUBLISHING), TIMEOUT);
		new WaitUntil(new ServerPublishStateCondition(ServerPublishState.SYNCHRONIZED), TIMEOUT);
	}
	
	protected ServerModule createServerModule(TreeItem item){
		return new ServerModule(item);
	}
	
	protected ServerEditor createServerEditor(String title){
		return new ServerEditor(title);
	}

	private class ServerStateCondition implements WaitCondition {

		private ServerState expectedState;

		private ServerStateCondition(ServerState expectedState) {
			this.expectedState = expectedState;
		}

		@Override
		public boolean test() {
			return expectedState.equals(getLabel().getState());
		}

		@Override
		public String description() {
			return "server's state is: " + expectedState.getText();
		}
	}

	private class ServerPublishStateCondition implements WaitCondition {

		private ServerPublishState expectedState;

		private ServerPublishStateCondition(ServerPublishState expectedState) {
			this.expectedState = expectedState;
		}

		@Override
		public boolean test() {
			return expectedState.equals(getLabel().getPublishState());
		}

		@Override
		public String description() {
			return "server's publish state is " + expectedState.getText();
		}
	}

}

