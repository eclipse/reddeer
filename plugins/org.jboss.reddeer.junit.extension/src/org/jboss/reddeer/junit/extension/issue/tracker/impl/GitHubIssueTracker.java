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
package org.jboss.reddeer.junit.extension.issue.tracker.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.junit.extensionpoint.IIssueTracker;

/**
 * Basic implementation of an issue tracker for GitHub issues. It uses GitHub
 * REST API by concatenating {@value #GITHUB_API_URL} and the value of a given
 * {@link GitHubIssue}.
 * 
 * @author apodhrad
 *
 */
public class GitHubIssueTracker implements IIssueTracker {

	public static final String GITHUB_API_URL = "https://api.github.com/";

	@Override
	public boolean isIssueSupported(Object issue) {
		return issue instanceof GitHubIssue;
	}

	@Override
	public boolean isIssueResolved(Object issue) throws IOException {
		if (issue instanceof GitHubIssue) {
			GitHubIssue githubIssue = (GitHubIssue) issue;
			try {
				String response = get(githubIssue.value());
				Pattern pattern = Pattern.compile("\"state\"\\s*:\\s*\"(open|closed)\"");
				Matcher matcher = pattern.matcher(response);
				if (matcher.find()) {
					return "closed".equals(matcher.group(1));
				}
			} catch (IOException ioe) {
				throw ioe;
			} catch (Exception e) {
				throw new RedDeerException("An exception occured during getting a state of an issue.", e);
			}
		}
		throw new IllegalArgumentException("Issue " + issue + " is not supported!");
	}

	@Override
	public String getIssueStateMessage(Object issue) {
		if (issue instanceof GitHubIssue) {
			GitHubIssue gitHubIssue = (GitHubIssue) issue;
			return "Issue '" + gitHubIssue.value() + "' is still open";
		}
		throw new IllegalArgumentException("Issue " + issue + " is not supported!");
	}

	protected String get(String path) throws MalformedURLException, IOException {
		StringBuffer response = new StringBuffer();
		HttpURLConnection con = (HttpURLConnection) new URL(GITHUB_API_URL + path).openConnection();
		con.setRequestMethod("GET");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String line = null;
		while ((line = in.readLine()) != null) {
			response.append(line);
		}
		in.close();
		return response.toString();
	}

}
