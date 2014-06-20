package org.jboss.reddeer.eclipse.ui.perspectives;

import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.PlatformUI;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.matcher.RegexMatcher;
import org.jboss.reddeer.swt.matcher.WithTextMatchers;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Abstract parent for each Perspective implementation
 * 
 * @author vlado pakan
 * 
 */
public abstract class AbstractPerspective {
  
  protected final Logger log = Logger.getLogger(this.getClass());
  
  private String perspectiveLabel;

  /**
   * Constructs the perspective with a given label.
   * 
   * @param perspectiveLabel Perspective label
   */
  public AbstractPerspective(String perspectiveLabel) {
    super();
    this.perspectiveLabel = perspectiveLabel;
    if (!isPerspectiveAvailable()){
    	throw new EclipseLayerException("Perspective "+perspectiveLabel+" isn't available");
    }
  }

  /**
   * Opens the perspective
   */
  public void open() {
    log.info("Open perspective: " + getPerspectiveLabel());
    if (isOpened()){
      log.debug("Perspective " + getPerspectiveLabel() + " is already opened.");
    }
    else{
      log.debug("Trying to open perspective: " + getPerspectiveLabel());
      WithTextMatchers m = new WithTextMatchers(new RegexMatcher[] {
					new RegexMatcher("Window.*"),
					new RegexMatcher("Open Perspective.*"),
					new RegexMatcher("Other...*") });
      Menu menu = new ShellMenu(m.getMatchers());
      menu.select();
      new DefaultShell("Open Perspective");
      DefaultTable table = new DefaultTable();
      try{
        // Try to select perspective label within available perspectives
        table.select(getPerspectiveLabel());
      } catch (SWTLayerException swtLayerException){
        // Try to select perspective label within available perspectives with "(default)" suffix
        table.select(getPerspectiveLabel() + " (default)");
      }
      new PushButton("OK").click();
    }
  }

  /**
   * Returns perspective label.
   * 
   * @return Perspective label
   */
  public String getPerspectiveLabel() {
    return perspectiveLabel;
  }
  
  /**
   * Returns whether the perspective is open.
   * 
   * @return Whether the perspective is open
   */
  	 public boolean isOpened(){
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return getActivePerspective().getLabel().equals(perspectiveLabel);
			}
		});
	  }
	
	private boolean isPerspectiveAvailable(){
		IPerspectiveDescriptor perspective = PlatformUI.getWorkbench().getPerspectiveRegistry().findPerspectiveWithLabel(perspectiveLabel);
		if (perspective == null){
			return false;
		}
		return true;
}
	
	private IPerspectiveDescriptor getActivePerspective(){
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getPerspective();
	}
}
