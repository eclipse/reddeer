package org.jboss.reddeer.junit.tracking.issue;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.common.properties.RedDeerProperties;

/**
 * Issue link resolver helps to resolve link to issue tracker of specified issue.
 * If you want to add a new issue tracker add property <i>reddeer.issue.trackers</i> to your test execution 
 * with values in format PREFIX:URL separated by char "|". ID from prefix (part after dash) is appended to
 * URL. Example:
 * <pre>
 * -Dreddeer.issue.trackers="CUSTOM:https://issue.tracker.com/id=" 
 * </pre>
 * 
 * @author mlabuda@redhat.com
 * @since 1.0.0 
 */
public class IssueLinkResolver {
	
	private static final String REDDEER_ISSUE_TRACKER_LINK = "https://github.com/jboss-reddeer/reddeer/issues/";
	private static final String ECLIPSE_ISSUE_TRACKER_LINK = "https://bugs.eclipse.org/bugs/show_bug.cgi?id=";
	
	private static List<IssueTracker> issueMapping = initializeMapping();
	
	/**
	 * Gets a link to the specific issue if there is an existing mapping of the issue to its issue tracker.
	 * 
	 * @param issue issue to resolve its link
	 * @return link to the issue in specific issue tracker or null if issue does not have mapping or URL
	 * of specific issue tracking system is invalid
	 */
	public static String getIssueLink(String issue) {
		for (IssueTracker tracker: issueMapping) {
			if (tracker.getIssuePrefix().equals(issue.split("-")[0])) {
				return tracker.getLink() + issue.split("-")[1];
			}
		}
		return null;
	}

	private static List<IssueTracker> initializeMapping() {
		List<IssueTracker> list = new ArrayList<IssueTracker>();
		list.add(IssueTracker.getIssueTracker("REDDEER", REDDEER_ISSUE_TRACKER_LINK));
		list.add(IssueTracker.getIssueTracker("ECLIPSE", ECLIPSE_ISSUE_TRACKER_LINK));
		addIssueTrackersSpecifiedByUser(list);
		return list;
	}
	
	private static void addIssueTrackersSpecifiedByUser(List<IssueTracker> list) {
		String userIssueTrackers = RedDeerProperties.ISSUE_TRACKERS.getValue();
		if (userIssueTrackers == null || userIssueTrackers.equals("")) {
			return;
		}
		for (String issueTracker: userIssueTrackers.split("\\|")) {
			String[] splittedIssueTracker = issueTracker.split(":", 2);
			// If user provides incorrect entry, nothing happens
			if (splittedIssueTracker.length == 2) {
				String issuePrefix = splittedIssueTracker[0];
				String link = splittedIssueTracker[1];
				list.add(IssueTracker.getIssueTracker(issuePrefix, link));
			}			
		}
	}
	
	private static class IssueTracker {
		String issuePrefix;
		String link;
		
		private IssueTracker() { }
		
		public static IssueTracker getIssueTracker(String issuePrefix, String link) {
			IssueTracker tracker = new IssueTracker();
			tracker.issuePrefix = issuePrefix;
			tracker.link = link;
			return tracker;
		}
		
		public String getLink() {
			return link;
		}
		
		public String getIssuePrefix() {
			return issuePrefix;
		}
	}
}
