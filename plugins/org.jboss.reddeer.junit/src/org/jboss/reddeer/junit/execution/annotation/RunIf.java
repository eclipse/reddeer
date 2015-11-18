package org.jboss.reddeer.junit.execution.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.junit.execution.TestMethodShouldRun;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RunIf {
	
	/**
	 * Condition class.
	 *
	 * @return the class<? extends test method should run>
	 */
	Class<? extends TestMethodShouldRun> conditionClass();
}
