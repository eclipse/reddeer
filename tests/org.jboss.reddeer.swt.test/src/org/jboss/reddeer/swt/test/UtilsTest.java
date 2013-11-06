package org.jboss.reddeer.swt.test;

import static org.junit.Assert.assertEquals;

import java.util.regex.Pattern;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.util.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class UtilsTest extends RedDeerTest {
	@Override
	protected void setUp() {
		super.setUp();
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCheckNotNullWithNull() {
		Utils.checkNotNull(null, "Evil non-existing object");
	}

	@Test
	public void testCheckNotNullWithObject() {
		Utils.checkNotNull(new String("foobar"), "Not-so-evil string");
	}

	@Test
	public void testShorten() {
		String s = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
		int l = s.length();

		assertEquals(s, Utils.shorten(s, l));
		assertEquals(s.substring(0, l - 2) + "<... shortened>", Utils.shorten(s, l - 2));
		assertEquals(s.substring(0, 10) + "<... shortened>", Utils.shorten(s, 10));
	}

	@Test
	public void testJoin() {
		String empty[] = {};
		String a[] = {"A", "B", "C", "DD", "E"};
		Pattern b[] = {Pattern.compile("A"), Pattern.compile(".*"), Pattern.compile("\\d+")};

		assertEquals("", Utils.join((String[])null, " "));
		assertEquals("", Utils.join(empty, " "));
		assertEquals("", Utils.join(a,  null));
		assertEquals("ABCDDE", Utils.join(a, ""));
		assertEquals("A > B > C > DD > E", Utils.join(a, " > "));
		assertEquals("A.*\\d+", Utils.join(b, ""));
		assertEquals("A > .* > \\d+", Utils.join(b, " > "));
	}
}
