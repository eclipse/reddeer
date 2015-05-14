package org.jboss.reddeer.snippet.test;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@CleanWorkspace
public class TestWithCleanWorkspaceRequirement {

	@Test
	public void testCase() {
		// here goes logic
	}
}