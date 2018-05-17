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
#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

        // The plug-in ID
        public static final String PLUGIN_ID = "${artifactId}";

        // The shared instance
        private static Activator plugin;
        
        boolean started;
        /**
         * The constructor
         */
        public Activator() {
        }

        /*
         * (non-Javadoc)
         * @see org.eclipse.ui.plugin.AbstractUIPlugin${symbol_pound}start(org.osgi.framework.BundleContext)
         */
        public void start(BundleContext context) throws Exception {
                super.start(context);
                plugin  = this;
                started = true;
        }

        /*
         * (non-Javadoc)
         * @see org.eclipse.ui.plugin.AbstractUIPlugin${symbol_pound}stop(org.osgi.framework.BundleContext)
         */
        public void stop(BundleContext context) throws Exception {
                plugin = null;
                started = false;
                super.stop(context);
        }

        /**
         * Returns the shared instance
         *
         * @return the shared instance
         */
        public static Activator getDefault() {
                return plugin;
        }
}
