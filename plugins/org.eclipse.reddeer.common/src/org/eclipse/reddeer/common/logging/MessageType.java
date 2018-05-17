/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.common.logging;

/**
 * Message filter for RedDeer Logger
 * 
 * @author Jiri Peterka
 *
 */
public class MessageType {
	public static final int NONE = 0;
	public static final int DUMP = 1;
	public static final int TRACE = 2;
	public static final int DEBUG = 4;
	public static final int INFO = 8;
	public static final int WARN = 16;
	public static final int ERROR = 32;
	public static final int FATAL = 64;
	public static final int STEP = 128;
	public static final int ALL = DUMP | TRACE | DEBUG | INFO | WARN | ERROR | FATAL | STEP;
}