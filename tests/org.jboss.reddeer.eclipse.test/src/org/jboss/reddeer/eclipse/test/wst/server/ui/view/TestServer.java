package org.jboss.reddeer.eclipse.test.wst.server.ui.view;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.core.model.ServerDelegate;
import org.jboss.reddeer.eclipse.test.Activator;

public class TestServer extends ServerDelegate {

	public static final String NAME = "Test server";
	
	@Override
	public IStatus canModifyModules(IModule[] arg0, IModule[] arg1) {
		return new Status(IStatus.OK, Activator.PLUGIN_ID, "Test server message");
	}

	@Override
	public IModule[] getChildModules(IModule[] arg0) {
		return new IModule[0];
	}

	@Override
	public IModule[] getRootModules(IModule module) throws CoreException {
		return new IModule[] { module };
	}

	@Override
	public void modifyModules(IModule[] arg0, IModule[] arg1,
			IProgressMonitor arg2) throws CoreException {
	}
}
