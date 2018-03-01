/*******************************************************************************
 * Copyright (c) 2018 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.test.rules;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.eclipse.reddeer.junit.rules.ErrorCollectorWithScreenshot;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.runners.model.MultipleFailureException;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ErrorCollectorWithScreenshotTest {

	@Rule
	public ErrorCollectorWithScreenshot errorCollector = new ErrorCollectorWithScreenshot();

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Before
	@After
	public void deleteScreenshots() {
		File[] files = getFiles(errorCollector.getScreenshotDirPath(),
				".*failAtTheEndTest.*\\.png|.*screenshotCapturerTest.*\\.png");
		if (files == null) {
			//Screenshot directory doesn't exist.
			return; 
		}
		for (File file : files) {
			file.delete();
		}
	}

	@Test
	public void failAtTheEndTest() {
		errorCollector.addError(new ErrorCollectorTestException("ErrorCollector Test - Exception 1/2."));
		errorCollector.addError(new ErrorCollectorTestException("ErrorCollector Test - Exception 2/2."));
		exception.expect(MultipleFailureException.class);
	}

	@Test
	public void screenshotCapturerTest() {
		errorCollector.addError(new ErrorCollectorTestException("ErrorCollector Test - Exception 1/2."));
		errorCollector.addError(new ErrorCollectorTestException("ErrorCollector Test - Exception 2/2."));
		exception.expect(MultipleFailureException.class);
		assertEquals(2, getFiles(errorCollector.getScreenshotDirPath(), ".*screenshotCapturerTest.*\\.png").length);
	}

	/**
	 * Return array of files which name matches the pattern.
	 * 
	 * @param directory
	 *            directory to be scanned (not recursively)
	 * @param pattern
	 *            regular expression used to match filename
	 * @return array of files or null if given directory doesn't exist
	 */
	public File[] getFiles(String directory, String pattern) {
		return new File(directory).listFiles(new FileNameFilter(pattern));
	}

	/**
	 * Allows to filter files by filenames.
	 * 
	 * @see java.io.FilenameFilter
	 */
	public class FileNameFilter implements java.io.FilenameFilter {

		String regexp;

		public FileNameFilter(String regexp) {
			super();
			this.regexp = regexp;
		}

		@Override
		public boolean accept(File dir, String name) {
			return name.matches(regexp);
		}
	}

	public class ErrorCollectorTestException extends Exception {

		private static final long serialVersionUID = 1L;

		public ErrorCollectorTestException(String message) {
			super(message);
		}
	}
}
