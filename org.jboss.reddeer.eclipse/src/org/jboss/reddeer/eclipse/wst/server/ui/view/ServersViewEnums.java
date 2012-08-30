package org.jboss.reddeer.eclipse.wst.server.ui.view;

import java.util.Arrays;
import java.util.List;


/**
 * Contains enumeration used in {@link ServersView} for displaying
 * server or project state / status. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class ServersViewEnums {

	private ServersViewEnums() {
		// not meant for instantiation
	}

	public enum ServerState {

		STARTING("Starting"), STARTED("Started"), DEBUGGING("Debugging"), 
		PROFILING("Profiling"), STOPPING("Stopping"), STOPPED("Stopped");

		private String text;

		private ServerState(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}

		public static ServerState getByText(String text){
			for (ServerState state : ServerState.values()){
				if (state.getText().equals(text)){
					return state;
				}
			}
			throw new IllegalArgumentException("There is no enumeration with text " + text);
		}
		
		public static List<ServerState> getRunningStates(){
			return Arrays.asList(STARTED, DEBUGGING, PROFILING);
		}
		
		public boolean isRunningState(){
			return getRunningStates().contains(this);
		}
	}

	public enum ServerStatus {

		SYNCHRONIZED("Synchronized"), PUBLISHING("Publishing"), RESTART("Restart"),
		REPUBLISH("Republish"), RESTART_REPUBLISH("Restart and republish");

		private String text;

		private ServerStatus(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}

		public static ServerStatus getByText(String text){
			for (ServerStatus status : ServerStatus.values()){
				if (status.getText().equals(text)){
					return status;
				}
			}
			throw new IllegalArgumentException("There is no enumeration with text " + text);
		}
	}
	
	public enum ProjectState {

		STARTED("Started"), STOPPED("Stopped");

		private String text;

		private ProjectState(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}

		public static ProjectState getByText(String text){
			for (ProjectState state : ProjectState.values()){
				if (state.getText().equals(text)){
					return state;
				}
			}
			throw new IllegalArgumentException("There is no enumeration with text " + text);
		}
	}

	public enum ProjectStatus {

		SYNCHRONIZED("Synchronized"), REPUBLISHED("Republished");

		private String text;

		private ProjectStatus(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}

		public static ProjectStatus getByText(String text){
			for (ProjectStatus status : ProjectStatus.values()){
				if (status.getText().equals(text)){
					return status;
				}
			}
			throw new IllegalArgumentException("There is no enumeration with text " + text);
		}
	}
}
