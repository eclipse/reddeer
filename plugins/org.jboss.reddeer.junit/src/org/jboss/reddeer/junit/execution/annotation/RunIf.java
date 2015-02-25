package org.jboss.reddeer.junit.execution.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.junit.execution.TestMethodShouldRun;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RunIf {
	Class<? extends TestMethodShouldRun> conditionClass();
}
