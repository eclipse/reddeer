package org.jboss.reddeer.junit.integration.runner.order.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(RequirementsTestSequenceSuite.class)
@SuiteClasses({RequirementsTestCase.class, NoRequirementsTestCase.class})
public class RequirementsSuite {

}
