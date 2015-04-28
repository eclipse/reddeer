package org.jboss.reddeer.core.util;

/**
 * Result runnable should be implemented by any class whose instances are intended to be executed by a thread and
 * their run should return a result of execution.
 */
public interface ResultRunnable<T> {

	T run();
}
