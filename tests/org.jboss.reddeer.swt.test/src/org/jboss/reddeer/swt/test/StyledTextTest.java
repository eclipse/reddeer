/**
 *
 *
 * @author rhopp 
 */
package org.jboss.reddeer.swt.test;

import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.swt.api.StyledText;
import org.jboss.reddeer.swt.impl.styledtext.DefaultStyledText;
import org.jboss.reddeer.workbench.view.impl.WorkbenchView;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author rhopp
 *
 */
public class StyledTextTest extends RedDeerTest {
	
	@BeforeClass
	public static void openView(){
		new WorkbenchView("RedDeer SWT Controls").open();
	}
	
	@Test
	public void defaultStyledTextTest(){
		assertTrue(new DefaultStyledText().getText().equals("Styled text"));
		StyledText styledText= new DefaultStyledText("Styled text");
		assertTrue(styledText.getText().equals("Styled text"));
		styledText.setText("Modified styled text");
		assertTrue(styledText.getText().equals("Modified styled text"));
	}

}
