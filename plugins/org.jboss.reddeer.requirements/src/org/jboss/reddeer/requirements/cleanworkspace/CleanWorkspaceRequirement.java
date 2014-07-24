package org.jboss.reddeer.requirements.cleanworkspace;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;

/**
 * Clean workspace requirement<br/><br/>
 * 
 * This {@link Requirement} ensures, that all projects are deleted from workspace
 * (aka. workspace is clean).<br/><br>
 * 
 * Annotate test class with {@link CleanWorkspace} annotation to have clean
 * workspace before the test cases are executed.<br/><br/>
 * 
 * Example:<br/>
 * <pre>
 * {@code @CleanWorkspace
 * public class TestClass {
 *    // workspace will be cleaned before tests execution
 * }
 * }
 * </pre>
 * 
 * @author rhopp
 * 
 */
public class CleanWorkspaceRequirement implements Requirement<CleanWorkspace> {

	/**
	 * Marks test class, which requires clean workspace before test cases are executed.
	 */
	@Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
	@Documented
	public @interface CleanWorkspace {
		
	}
	
	/**
	 * Always returns true because cleaning workspace should be possible every time.
	 * 
	 * @return true
	 */
	@Override
	public boolean canFulfill() {
		return true; 
	}

	/**
	 * Deletes all projects from workspace.
	 */
	@Override
	public void fulfill() {	
		PackageExplorer packageExplorer = new PackageExplorer();
		packageExplorer.open();
		packageExplorer.deleteAllProjects();
	}

	/**
	 * This method is empty because annotation {@link CleanWorkspace} has no elements.
	 * However, it is one of methods of {@link Requirement} interface so it has to be
	 * overridden.
	 */
	@Override
	public void setDeclaration(CleanWorkspace declaration) {
		// nothing to do here
	}

}
