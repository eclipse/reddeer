/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.requirement;

/**
 * Marks that {@link Requirement} class supports property based configuration. It means there's an entry in XML configuration file
 * and the instance contains setters for all properties mentioned in that configuration.  
 * 
 * @author Lucia Jelinkova
 *
 */
public interface PropertyConfiguration {

}
