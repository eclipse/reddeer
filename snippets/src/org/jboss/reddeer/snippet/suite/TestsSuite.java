package org.jboss.reddeer.snippet.suite;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.snippet.test.Test1;
import org.jboss.reddeer.snippet.test.Test2;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(RedDeerSuite.class)
@SuiteClasses({
	Test1.class,
    Test2.class
})
public class TestsSuite {

}