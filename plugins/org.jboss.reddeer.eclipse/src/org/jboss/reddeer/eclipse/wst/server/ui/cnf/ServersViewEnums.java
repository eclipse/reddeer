/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.eclipse.wst.server.ui.cnf;

import java.util.Arrays;
import java.util.List;


/**
 * Contains enumeration used in {@link ServersView2} for displaying
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
		PROFILING("Profiling"), STOPPING("Stopping"), STOPPED("Stopped"), 
		NONE("");

		private String text;

		private ServerState(String text) {
			this.text = text;
		}

		/**
		 * Gets the text.
		 *
		 * @return the text
		 */
		public String getText() {
			return text;
		}

		/**
		 * Gets the by text.
		 *
		 * @param text the text
		 * @return the by text
		 */
		public static ServerState getByText(String text){
			for (ServerState state : ServerState.values()){
				if (state.getText().equals(text)){
					return state;
				}
			}
			throw new IllegalArgumentException("There is no enumeration with text " + text);
		}
		
		/**
		 * Gets the running states.
		 *
		 * @return the running states
		 */
		public static List<ServerState> getRunningStates(){
			return Arrays.asList(STARTED, DEBUGGING, PROFILING);
		}
		
		/**
		 * Checks if is running state.
		 *
		 * @return true, if is running state
		 */
		public boolean isRunningState(){
			return getRunningStates().contains(this);
		}
	}

	public enum ServerPublishState {

		SYNCHRONIZED("Synchronized"), PUBLISHING("Publishing..."), RESTART("Restart"),
		REPUBLISH("Republish"), RESTART_REPUBLISH("Restart and republish"), NONE("");

		private String text;

		private ServerPublishState(String text) {
			this.text = text;
		}

		/**
		 * Gets the text.
		 *
		 * @return the text
		 */
		public String getText() {
			return text;
		}

		/**
		 * Gets the by text.
		 *
		 * @param text the text
		 * @return the by text
		 */
		public static ServerPublishState getByText(String text){
			for (ServerPublishState status : ServerPublishState.values()){
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

		/**
		 * Gets the text.
		 *
		 * @return the text
		 */
		public String getText() {
			return text;
		}

		/**
		 * Gets the by text.
		 *
		 * @param text the text
		 * @return the by text
		 */
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

		/**
		 * Gets the text.
		 *
		 * @return the text
		 */
		public String getText() {
			return text;
		}

		/**
		 * Gets the by text.
		 *
		 * @param text the text
		 * @return the by text
		 */
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
