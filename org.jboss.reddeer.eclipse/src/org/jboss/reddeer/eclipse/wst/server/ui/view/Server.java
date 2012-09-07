package org.jboss.reddeer.eclipse.wst.server.ui.view;

import java.util.List;

import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerPublishState;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerState;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.AllRunningJobsAreNotActive;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.wait.Timeout;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;

/**
 * Represents a server on {@link ServersView}. Contains both, the server data
 * (name, state, status) and operations that can be invoked on server (Start,
 * Stop, Delete etc.). For operations that can be invoked on project added to
 * server see {@link ServerProject}
 * 
 * @author Lucia Jelinkova
 * 
 */
public class Server {

	private static final Timeout TIMEOUT = Timeout.VERY_LONG;

	private TreeItem treeItem;

	public Server(TreeItem treeItem) {
		this.treeItem = treeItem;
	}

	public ServerLabel getLabel(){
		return new ServerLabel(treeItem.getText());
	}

	public List<ServerProject> getProjects() {
		throw new UnsupportedOperationException();
	}

	public ServerProject getProject() {
		throw new UnsupportedOperationException();
	}

	public void open() {
		throw new UnsupportedOperationException();
	}

	public void start() {
		if (!ServerState.STOPPED.equals(getLabel().getState())){
			throw new ServersViewException("Cannot start server because it is not stopped");
		}
		operateServerState("Start", ServerState.STARTED);
	}

	public void debug() {
		if (!ServerState.STOPPED.equals(getLabel().getState())){
			throw new ServersViewException("Cannot debug server because it is not stopped");
		}
		operateServerState("Debug", ServerState.DEBUGGING);
	}

	public void profile() {
		if (!ServerState.STOPPED.equals(getLabel().getState())){
			throw new ServersViewException("Cannot profile server because it is not stopped");
		}
		operateServerState("Profile", ServerState.PROFILING);
	}

	public void restart() {
		if (!getLabel().getState().isRunningState()){
			throw new ServersViewException("Cannot restart server because it is not running");
		}
		operateServerState("Restart", ServerState.STARTED);
	}

	public void restartInDebug() {
		if (!getLabel().getState().isRunningState()){
			throw new ServersViewException("Cannot restart server in debug because it is not running");
		}
		operateServerState("Restart in Debug", ServerState.DEBUGGING);
	}
	
	public void restartInProfile() {
		if (!getLabel().getState().isRunningState()){
			throw new ServersViewException("Cannot restart server in profile because it is not running");
		}
		operateServerState("Restart in Profile", ServerState.PROFILING);
	}

	public void stop() {
		ServerState state = getLabel().getState();
		if (!ServerState.STARTING.equals(state) && !state.isRunningState()){
			throw new ServersViewException("Cannot stop server because it not running");
		}
		operateServerState("Stop", ServerState.STOPPED);
	}

	public void publish() {
		select();
		DefaultShell activeShell = new DefaultShell();
		new ContextMenu("Publish").select();
		waitForPublish(activeShell);
	}

	public void clean() {
		select();
		DefaultShell activeShell = new DefaultShell();
		new ContextMenu("Clean...").select();
		new PushButton("OK").click();
		waitForPublish(activeShell);
	}

	public void delete() {
		delete(false);
	}

	public void delete(boolean stopFirst) {
		select();
		ServerState state = getLabel().getState();
		
		new ContextMenu("Delete").select();
		new DefaultShell("Delete Server");
		if (!ServerState.STOPPED.equals(state) && !ServerState.NONE.equals(state)){
			new CheckBox().toggle(stopFirst);
		}
		new PushButton("OK").click();
		new WaitUntil(new TreeItemIsDisposed(treeItem), TIMEOUT);
		new WaitUntil(new AllRunningJobsAreNotActive(), TIMEOUT);
	}

	public void addAndRemoveProject() {
		throw new UnsupportedOperationException();
	}

	protected void select() {
		treeItem.select();
	}

	protected void operateServerState(String menuItem, ServerState resultState){
		select();
		new ContextMenu(menuItem).select();
		new WaitUntil(new NonSystemJobRunsCondition(), TIMEOUT);
		new WaitUntil(new ServerStateCondition(resultState), TIMEOUT);
		new WaitWhile(new NonSystemJobRunsCondition(), TIMEOUT);
	}
	
	protected void waitForPublish(Shell activeShell){
		new WaitWhile(new ShellWithTextIsActive(activeShell.getText()), TIMEOUT);
		new WaitUntil(new ShellWithTextIsActive(activeShell.getText()), TIMEOUT);
		new WaitWhile(new ServerPublishStateCondition(ServerPublishState.PUBLISHING), TIMEOUT);
		new WaitWhile(new NonSystemJobRunsCondition(), TIMEOUT);
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
		public String getFailureMessage() {
			return null;
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
		public String getFailureMessage() {
			return null;
		}
	}

	private class TreeItemIsDisposed implements WaitCondition {

		private TreeItem treeItem;

		public TreeItemIsDisposed(TreeItem treeItem) {
			this.treeItem = treeItem;
		}

		@Override
		public boolean test() {
			return treeItem.isDisposed();
		}

		@Override
		public String getFailureMessage() {
			return null;
		}
	}
}
