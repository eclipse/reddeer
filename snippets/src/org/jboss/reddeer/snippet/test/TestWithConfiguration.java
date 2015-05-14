package org.jboss.reddeer.snippet.test;

import org.jboss.reddeer.eclipse.ui.perspectives.JavaBrowsingPerspective;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@CleanWorkspace
@OpenPerspective(JavaBrowsingPerspective.class)
public class TestWithConfiguration {

    @Test
    public void testCase1(){
        //testCase logic
    }
}