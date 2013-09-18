package org.jboss.reddeer.junit.requirement.inject;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotates injection points where proper requirements should be injected to   
 * 
 * @author jjankovi
 *
 */
@Target({ FIELD })
@Retention(RUNTIME)
public @interface InjectRequirement {

}
