package org.jboss.reddeer.eclipse.test.jst.ejb.ui;

import static org.junit.Assert.*;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.jst.ejb.ui.*;
import org.jboss.reddeer.eclipse.jst.servlet.ui.WebProjectFirstPage;

@RunWith(RedDeerSuite.class)
public class EjbWizardTest {
    
    @Test
    public void createEJBProject(){
        EjbProjectWizard ejb = new EjbProjectWizard();
        ejb.open();
        WebProjectFirstPage firstPage = (WebProjectFirstPage)ejb.getWizardPage(0);
        firstPage.setProjectName("ejbProject");
        ejb.finish();
        PackageExplorer pe = new PackageExplorer();
        pe.open();
        assertTrue(pe.containsProject("ejbProject"));
    }

}
