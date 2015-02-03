package org.jboss.reddeer.generator.test.builder;

import org.jboss.reddeer.generator.builder.MethodBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author apodhrad
 *
 */
public class CodeBuilderTest {

	@Test
	public void codeBuilderWithDefaultsTest() {
		MethodBuilder code = new MethodBuilder();
		Assert.assertEquals("public void foo() {\n}", code.toString());
	}

	@Test
	public void codeBuilderWithCommandTest() {
		MethodBuilder code = new MethodBuilder().command("System.out.println();");
		Assert.assertEquals("public void foo() {\n\tSystem.out.println();\n}", code.toString());
	}

	@Test
	public void codeBuilderWithTwoCommandsTest() {
		MethodBuilder code = new MethodBuilder().command("System.out.println();").command("System.out.println();");
		Assert.assertEquals("public void foo() {\n\tSystem.out.println();\n\tSystem.out.println();\n}", code.toString());
	}

	@Test
	public void codeBuilderWithNameTest() {
		MethodBuilder code = new MethodBuilder().name("print").command("System.out.println();");
		Assert.assertEquals("public void print() {\n\tSystem.out.println();\n}", code.toString());
	}

	@Test
	public void codeBuilderWithIncorrectNameTest() {
		MethodBuilder code = new MethodBuilder().name("print to doc:").command("System.out.println();");
		Assert.assertEquals("public void printtodoc() {\n\tSystem.out.println();\n}", code.toString());
	}

	@Test
	public void codeBuilderWithParameterTest() {
		MethodBuilder code = new MethodBuilder().parameter("String name").command("System.out.println(name);");
		Assert.assertEquals("public void foo(String name) {\n\tSystem.out.println(name);\n}", code.toString());
	}

	@Test
	public void codeBuilderWithTwoParametersTest() {
		MethodBuilder code = new MethodBuilder().parameter("String name").parameter("Object obj")
				.command("System.out.println(name);");
		Assert.assertEquals("public void foo(String name, Object obj) {\n\tSystem.out.println(name);\n}",
				code.toString());
	}

	@Test
	public void codeBuilderWithTypeTest() {
		MethodBuilder code = new MethodBuilder().type("String");
		Assert.assertEquals("public String foo() {\n}", code.toString());
	}

	@Test
	public void codeBuilderWithTypeAndNameAndCommandTest() {
		MethodBuilder code = new MethodBuilder().type("String").name("getName").command("return name;");
		Assert.assertEquals("public String getName() {\n\treturn name;\n}", code.toString());
	}

	@Test
	public void codeBuilderWithGetTest() {
		MethodBuilder code = new MethodBuilder().type("String").get("Name").command("return name;");
		Assert.assertEquals("public String getName() {\n\treturn name;\n}", code.toString());
	}

	@Test
	public void codeBuilderWithCommandWithoutCommaTest() {
		MethodBuilder code = new MethodBuilder().type("String").get("Name").command("return name");
		Assert.assertEquals("public String getName() {\n\treturn name;\n}", code.toString());
	}

	@Test
	public void codeBuilderWithReturnCommandTest() {
		MethodBuilder code = new MethodBuilder().type("String").name("getName").returnCommand("name");
		Assert.assertEquals("public String getName() {\n\treturn name;\n}", code.toString());
	}
}
