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
package org.eclipse.reddeer.common.properties;

/**
 * Distinguishes the various system property types (boolean, text, file etc.)
 * 
 * @author Lucia Jelinkova
 *
 */
public enum RedDeerPropertyType {

	BOOLEAN, ENUMERATION, TEXT, FLOAT;
}
