#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(RedDeerSuite.class)
@Suite.SuiteClasses({
	BasicTest.class
})
public class AllRedDeerTests {

}
