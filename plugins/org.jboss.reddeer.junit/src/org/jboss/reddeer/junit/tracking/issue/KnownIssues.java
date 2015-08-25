package org.jboss.reddeer.junit.tracking.issue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for marking test methods about known issues.
 * 
 * @author mlabuda@redhat.com
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface KnownIssues {
	
	String[] value();
}
