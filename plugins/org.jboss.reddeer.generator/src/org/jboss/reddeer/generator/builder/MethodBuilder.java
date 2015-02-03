package org.jboss.reddeer.generator.builder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Helper class for building a method.
 * 
 * @author apodhrad
 *
 */
public class MethodBuilder {

	private static final String SPACE = " ";
	private static final String COMMA = ",";
	private static final String TAB = "\t";
	private static final String NEW_LINE = "\n";

	private String visibility;
	private String type;
	private String name;
	private List<String> parameters;
	private List<String> commands;

	/**
	 * Constructor which initializes the following attributes
	 * <ul>
	 * <li>visibility to "public"
	 * <li>type to "void"
	 * <li>name to "foo"
	 * </ul>
	 * 
	 */
	public MethodBuilder() {
		visibility = "public";
		type = "void";
		name = "foo";
		parameters = new ArrayList<String>();
		commands = new ArrayList<String>();
	}

	/**
	 * Static method for creating default code builder.
	 * 
	 * @return code builder
	 */
	public static MethodBuilder method() {
		return new MethodBuilder();
	}

	/**
	 * Sets visibility.
	 * 
	 * @param visibility
	 *            visibility
	 * @return current code builder
	 */
	public MethodBuilder visibility(String visibility) {
		this.visibility = visibility;
		return this;
	}

	/**
	 * Sets return type.
	 * 
	 * @param type
	 *            return type
	 * @return current code builder
	 */
	public MethodBuilder type(String type) {
		this.type = type;
		return this;
	}

	/**
	 * Sets method name.
	 * 
	 * @param name
	 *            method name
	 * @return current code builder
	 */
	public MethodBuilder name(String name) {
		this.name = name.replaceAll("\\W", "");
		return this;
	}

	/**
	 * Sets get method name, e.g. get("Name") returns "getName".
	 * 
	 * @param name
	 *            method name
	 * @return current code builder
	 */
	public MethodBuilder get(String name) {
		return name("get" + name);
	}

	/**
	 * Sets method parameter, e.g. "String name".
	 * 
	 * @param parameter
	 *            parameter
	 * @return current code builder
	 */
	public MethodBuilder parameter(String parameter) {
		parameters.add(parameter);
		return this;
	}

	/**
	 * Adds a given command into the method.
	 * 
	 * @param command
	 *            command
	 * @return current code builder
	 */
	public MethodBuilder command(String command) {
		if (!command.endsWith(";")) {
			command += ";";
		}
		commands.add(command);
		return this;
	}

	/**
	 * Adds a return command to the method.
	 * 
	 * @param command
	 *            command
	 * @return current code builder
	 */
	public MethodBuilder returnCommand(String command) {
		return command("return " + command);
	}

	@Override
	public String toString() {
		StringBuffer code = new StringBuffer();
		code.append(visibility).append(SPACE).append(type).append(SPACE).append(name).append("(");
		Iterator<String> parameterIterator = parameters.iterator();
		while (parameterIterator.hasNext()) {
			code.append(parameterIterator.next());
			if (parameterIterator.hasNext()) {
				code.append(COMMA).append(SPACE);
			}
		}
		code.append(")").append(SPACE).append("{").append(NEW_LINE);
		for (String command : commands) {
			code.append(TAB).append(command).append(NEW_LINE);
		}
		code.append("}");
		return code.toString();
	}

}
