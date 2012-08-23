package org.jboss.reddeer.eclipse.test.ui.problems;

import static org.junit.Assert.assertEquals;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.ui.ide.NewGeneralProjectWizardDialog;
import org.jboss.reddeer.eclipse.ui.problems.ProblemsView;
import org.jboss.reddeer.swt.condition.AllRunningJobsAreNotActive;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.shell.WorkbenchShell;
import org.jboss.reddeer.swt.util.Bot;
import org.jboss.reddeer.swt.util.Jobs;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Just a draft.
 * 
 * @author rhopp
 *
 */

public class ProblemsViewTest {
	
	private ProblemsView problemsView;
	
	/*@Before
	public void setup(){
		SWTWorkbenchBot bot = Bot.get();
		NewJavaProjectWizardDialog dialog = new NewJavaProjectWizardDialog();
		dialog.open();
		bot.textWithLabel("&Project name:").setText("Test");
		dialog.finish();
		System.out.println("=======================================");
		System.out.println(new DefaultShell().getText());
		System.out.println(Jobs.getAllRunningJobs().toString());
		problemsView = new ProblemsView();
		problemsView.open();
	}
	
	@After
	public void teardown(){
		PackageExplorer pkgExplorer = new PackageExplorer();
		pkgExplorer.open();
		pkgExplorer.deleteItem("Test", true);
		System.out.println(new DefaultShell().getText());
		System.out.println(Jobs.getAllRunningJobs().toString());
	}
	
	@Test
	public void getAllErrorsNoErrorsNoWarnings(){
		assertEquals("Errors node should be empty", problemsView.getAllErrors().size(), 0);
	}
	
	@Test
	public void getAllErrorsNoErrorsWithWarnings(){
		System.out.println("test");
	}*/
	
	

}
