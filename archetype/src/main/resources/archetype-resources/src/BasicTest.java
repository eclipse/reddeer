#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import static org.junit.Assert.assertTrue;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.junit.Test;

public class BasicTest {
	
	@Test
	public void test(){
		assertTrue(new DefaultShell().getText() != null);
	}

}
