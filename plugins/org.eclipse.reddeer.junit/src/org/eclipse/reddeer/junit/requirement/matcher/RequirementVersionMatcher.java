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
package org.eclipse.reddeer.junit.requirement.matcher;

import java.util.stream.Collectors;

import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration;
import org.hamcrest.Description;

/**
 * Requirement version attribute matcher. Useful for matching version of
 * attributes with various operators. Supported operators and formats:<br>
 * 
 * <pre>
 * &gt;X.Y.Z
 * &gt;=X.Z.Z.Y
 * &lt;X.X
 * &lt;=X.X
 * X.X
 * (X.X;X.Y.X)
 * [X.Y.Z;X.X)
 * (X.X;X]
 * [X.Y;X.X.Z]
 * </pre>
 * 
 * @author mlabuda@redhat.com
 *
 */
public class RequirementVersionMatcher extends RequirementMatcher {

	public RequirementVersionMatcher(Class<? extends RequirementConfiguration> clazz, String attribute, String matchingValue) {
		super(clazz, attribute, matchingValue);
	}

	@Override
	public boolean matches(Object item) {
		if (getConfigurationClass().isAssignableFrom(item.getClass())) {
			RequirementConfiguration config = (RequirementConfiguration) item;
			String matchingValue = getMatchingValue();
			String version = config.getNestedAttribute(getAttributeName());
			return compareVersions(version, extractOperands(matchingValue), extractOperator(matchingValue));
		}
		return false;
	}

	/**
	 * Gets extracted operand from matched value.
	 * 
	 * @param matchedValue
	 *            value to be matched, contains operand(s) and operator.
	 * @return "&gt;", "&gt;=", "&lt;", "&lt;=", "[]", "[)", "(]", "()", ""
	 */
	private static String extractOperator(String matchedValue) {
		if (matchedValue.startsWith(">") || matchedValue.startsWith("<")) {
			if (matchedValue.substring(1, 2).equals("=")) {
				return matchedValue.substring(0, 2);
			} else {
				return matchedValue.substring(0, 1);
			}
		}
		if (matchedValue.startsWith("[") || matchedValue.startsWith("(")) {
			if (matchedValue.endsWith("]") || matchedValue.endsWith(")")) {
				return matchedValue.substring(0, 1) + matchedValue.substring(matchedValue.length() - 1);
			}
		}
		return "";
	}

	/**
	 * Extracts operand(s) from matched value.
	 * 
	 * @param matchedValue matched value
	 * @param operators operator to substract from matched value to get operands
	 * @return operands for matching
	 */
	private static String[] extractOperands(String matchedValue) {
		 String numbers = matchedValue.chars().filter(c -> !"<>=[()]".contains(String.valueOf((char) c))).
			mapToObj(c -> Character.toString((char) c)).collect(Collectors.joining());
		 return numbers.split(";");
	}
	
	/**
	 * Compare versions. Checks whether a number fits operands and operator. Operands 
	 * are versions.
	 * 
	 * Given:<br>
	 * number = 5.0.0<br>
	 * operands = { 1.1.0 }<br>
	 * operator = "&gt;="<br>
	 * <br>
	 * then operation is 5.0.0 &gt;= 1.1.0 and it returns true 
	 * 
	 * @param version version to operate with operands and operator
	 * @param operands operands to work with operator and version
	 * @param operator operator
	 * @return true if version satisfy operation defined by itself, operands and operator
	 */
	private static boolean compareVersions(String version, String[] operands, String operator) {
		VersionComparator comparator = new VersionComparator();
		switch (operator) {
		case ">":
			return comparator.compare(version, operands[0]) > 0;
		case ">=":
			return comparator.compare(version, operands[0]) >= 0;
		case "<":
			return comparator.compare(version, operands[0]) < 0;
		case "<=":
			return comparator.compare(version, operands[0]) <= 0;
		case "[]":
			return comparator.compare(version, operands[0]) >= 0 &&
					comparator.compare(version, operands[1]) <= 0;
		case "[)":
			return comparator.compare(version, operands[0]) >= 0 &&
					comparator.compare(version, operands[1]) < 0;
		case "(]":
			return comparator.compare(version, operands[0]) > 0 &&
					comparator.compare(version, operands[1]) <= 0;
		case "()":
			return comparator.compare(version, operands[0]) > 0 &&
					comparator.compare(version, operands[1]) < 0;
		case "":
			return version.equals(operands[0]);
		default:
			return false;
		}
	}
	
	@Override
	public void describeTo(Description description) {
		description.appendText(" matches requirement's attribute to version.");
	}

}
