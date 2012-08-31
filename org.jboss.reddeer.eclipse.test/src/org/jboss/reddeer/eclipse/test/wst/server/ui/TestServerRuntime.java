package org.jboss.reddeer.eclipse.test.wst.server.ui;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.wst.server.core.model.RuntimeDelegate;

public class TestServerRuntime extends RuntimeDelegate {
	
	public static final String ID = "org.jboss.reddeer.eclipse.test.wst.server.testserverruntime";
	
	public static final String NAME = "Test runtime";
	
	public static final String TYPE = "Test runtime";
	
	public IStatus validate() {
		return Status.OK_STATUS;
	}
	
	public void setDefaults(IProgressMonitor monitor) {
		getRuntimeWorkingCopy().setLocation(new Path(""));
	}
}