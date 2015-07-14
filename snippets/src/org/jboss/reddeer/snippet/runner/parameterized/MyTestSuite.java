package org.jboss.reddeer.snippet.runner.parameterized;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(RedDeerSuite.class)
@SuiteClasses({MyParameterizedTestClass.class, MySimpleTestClass.class})
public class MyTestSuite {

}
