#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.reddeer.test;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

        // The plug-in ID
        public static final String PLUGIN_ID = "${artifactId}.reddeer.test";

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
