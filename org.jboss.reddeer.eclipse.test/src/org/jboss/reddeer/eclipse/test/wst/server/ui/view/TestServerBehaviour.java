package org.jboss.reddeer.eclipse.test.wst.server.ui.view;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.wst.server.core.IServer;
import org.eclipse.wst.server.core.model.ServerBehaviourDelegate;

public class TestServerBehaviour extends ServerBehaviourDelegate {

	public TestServerBehaviour() {
		// TODO Auto-generated constructor stub
	}

	protected void setupLaunch(ILaunch launch, String launchMode, IProgressMonitor monitor) throws CoreException {

		setServerState(IServer.STATE_STARTING);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		setServerRestartState(false);
		setServerState(IServer.STATE_STARTED);
		setMode(launchMode);
	}

	@Override
	public void stop(boolean arg0) {
		setServerState(IServer.STATE_STOPPING);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		setServerState(IServer.STATE_STOPPED);
	}

	protected void publishServer(int kind, org.eclipse.core.runtime.IProgressMonitor monitor) throws org.eclipse.core.runtime.CoreException {
		setServerPublishState(IServer.PUBLISH_STATE_NONE);
	}
}
