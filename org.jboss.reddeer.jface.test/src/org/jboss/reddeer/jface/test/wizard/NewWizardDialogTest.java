package org.jboss.reddeer.jface.test.wizard;

import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.jface.wizard.NewWizardDialog;
import org.jboss.reddeer.swt.impl.shell.ActiveShell;
import org.jboss.reddeer.swt.impl.shell.WorkbenchShell;
import org.junit.Test;

public class NewWizardDialogTest {

  @Test
  public void open() {
    NewWizardDialogImpl newWizardDialog = new NewWizardDialogImpl();
    // Open New wizard dialog
    newWizardDialog.open();
    final String wizardDialogText = new ActiveShell().getText();
    assertTrue("Active Shell has to have text: " + NewWizardDialog.DIALOG_TITLE
        + "\nbut is\n" + wizardDialogText,
        wizardDialogText.equals(NewWizardDialog.DIALOG_TITLE));
    // close New wizard dialog
    newWizardDialog.cancel();
    final String activeShellText = new ActiveShell().getText();
    final String workbenchTitle = new WorkbenchShell().getText();
    assertTrue("Active Shell must be Workbench shell and has to have text: "
        + workbenchTitle + "\nbut is\n" + activeShellText,
        workbenchTitle.equals(activeShellText));
  }

}
