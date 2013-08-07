package org.jboss.reddeer.eclipse.test.wst.server.ui.view;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.wst.server.core.IServer;
import org.eclipse.wst.server.core.model.ServerBehaviourDelegate;
import org.jboss.reddeer.swt.wait.AbstractWait;

public class TestServerBehaviour extends ServerBehaviourDelegate {

	public TestServerBehaviour() {
		// TODO Auto-generated constructor stub
	}

	protected void setupLaunch(ILaunch launch, String launchMode, IProgressMonitor monitor) throws CoreException {

		setServerState(IServer.STATE_STARTING);
		AbstractWait.sleep(5000);
		setServerRestartState(false);
		setServerState(IServer.STATE_STARTED);
		setMode(launchMode);
	}

	@Override
	public void stop(boolean arg0) {
		setServerState(IServer.STATE_STOPPING);
		AbstractWait.sleep(5000);
		setServerState(IServer.STATE_STOPPED);
	}

	protected void publishServer(int kind, org.eclipse.core.runtime.IProgressMonitor monitor) throws org.eclipse.core.runtime.CoreException {
		AbstractWait.sleep(5000);
		setServerPublishState(IServer.PUBLISH_STATE_NONE);
	}
}
