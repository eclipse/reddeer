package org.jboss.reddeer.common.userprofile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;
import org.jboss.reddeer.common.Activator;
/**
 * Handles User Profile logic
 * @author vlado pakan
 *
 */
public class UserProfile {
	
	public static final String VM_ARGS_KEY = "vmArgs";
	public static final String PROGRAM_ARGS_KEY = "programArgs";
	private Properties userProfileProps = null;
	private static UserProfile userProfile = null;
	
	private UserProfile() {
		File userProfileFile = new File(System.getProperty("user.home"), ".reddeer");
		if (userProfileFile.exists()) {
			StatusManager.getManager().handle(new Status(IStatus.INFO, Activator.PLUGIN_ID,
					"Loading RedDeer properties from user profile file: " + userProfileFile.getAbsolutePath()),StatusManager.LOG);
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(userProfileFile);
				userProfileProps = new Properties();
				userProfileProps.load(fis);
			} catch (IOException ioe) {
				StatusManager.getManager().handle(new Status(IStatus.INFO, Activator.PLUGIN_ID,
						"Loading RedDeer properties from user profile file: " + userProfileFile.getAbsolutePath(),
						ioe),StatusManager.LOG);
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException ioe) {
						StatusManager.getManager()
								.handle(new Status(IStatus.INFO, Activator.PLUGIN_ID,
										"Loading RedDeer properties from user profile file: "
												+ userProfileFile.getAbsolutePath(),
										ioe),StatusManager.LOG);
					}
				}
			}
		}
	}
	/**
	 * Returns USerProfile instance 
	 * @return
	 */
	public static UserProfile getInstance(){
		if (UserProfile.userProfile == null){
			UserProfile.userProfile = new UserProfile();
		}
		return UserProfile.userProfile;
	}
	/**
	 * Returns property value specified by key parameter if exists in use profile file
	 * otherwise returns null
	 * @param key
	 * @return
	 */
	public String getProperty (String key){
		String value = null;
		
		if(userProfileProps != null	&& userProfileProps.containsKey(key)){
			value = userProfileProps.getProperty(key);
		}
		return value;
	}

}
