package org.jboss.reddeer.eclipse.test.ui.dialogs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.jdt.ui.WorkbenchPreferenceDialog;
import org.jboss.reddeer.eclipse.ui.dialogs.FileEditorsPreferencePage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class FileEditorsPreferencePageTest {
	private static final String TEST_FILE_TYPE = "*.testfiletype";
	private static final String TEST_ASSOCIATED_EDITOR_0 = "Maven POM Editor";
	private static final String TEST_ASSOCIATED_EDITOR_1 = "XML Editor";
	
	private WorkbenchPreferenceDialog preferencesDialog;
	private FileEditorsPreferencePage fileEditorsPreferencePage;
	@Before
	public void setUp(){
		preferencesDialog = new WorkbenchPreferenceDialog();
		fileEditorsPreferencePage = new FileEditorsPreferencePage();
		preferencesDialog.open();
		preferencesDialog.select(fileEditorsPreferencePage);
	}
	@Test
	public void containsFileType(){
		final String fileType0 = "*.pom";
		final String fileType1 = "*.html";
		assertTrue("File types table has to contain these file types:\n"
				+ "'" + fileType0 + "\n"
				+ "'" + fileType1 + "'",
			fileEditorsPreferencePage.containsFileTypes(fileType0,fileType1));
	}
	@Test
	public void containsNonExistingFileType(){
		final String nonExistingFileType = "NON_EXISTING_FILE_TYPE";
		assertFalse("File Types should not contain file type: '"
				+ nonExistingFileType + "'",
			fileEditorsPreferencePage.containsFileTypes("*.pom",nonExistingFileType));
	}
	@Test
	public void containsAssociatedEditor(){
		final String associatedEditor0 = "Maven POM Editor";
		final String associatedEditor1 = "XML Editor";
		fileEditorsPreferencePage.selectFileTypes("*.pom");
		assertTrue("Associated editors has to contain these editors:\n"
				+ "'" + associatedEditor0 + "\n"
				+ "'" + associatedEditor1 + "'",
			fileEditorsPreferencePage.containsAssociatedEditors(associatedEditor0,associatedEditor0));
	}
	@Test
	public void containsNonExistingAssociatedEditor(){
		final String nonExistingAssociatedEditor = "NON_EXISTING_ASSOCCIATED_EDITOR";
		fileEditorsPreferencePage.selectFileTypes("*.pom");
		assertFalse("File Types should not contain file type: '"
				+ nonExistingAssociatedEditor + "'",
			fileEditorsPreferencePage.containsAssociatedEditors(nonExistingAssociatedEditor));
	}
	@Test
	public void addRemoveFileType () {
		
		fileEditorsPreferencePage.addFileType(FileEditorsPreferencePageTest.TEST_FILE_TYPE);
		assertTrue("File types table has to contain '" + FileEditorsPreferencePageTest.TEST_FILE_TYPE + "'",
			fileEditorsPreferencePage.containsFileTypes(FileEditorsPreferencePageTest.TEST_FILE_TYPE));
		
		fileEditorsPreferencePage.removeFileType(FileEditorsPreferencePageTest.TEST_FILE_TYPE);
		assertFalse("File types table cannot contain '" + FileEditorsPreferencePageTest.TEST_FILE_TYPE + "'",
				fileEditorsPreferencePage.containsFileTypes(FileEditorsPreferencePageTest.TEST_FILE_TYPE));
		
	}
	@Test
	public void addRemoveAssociatedEditor () {
		fileEditorsPreferencePage.addFileType(FileEditorsPreferencePageTest.TEST_FILE_TYPE);
		
		fileEditorsPreferencePage.addAssociatedEditor(FileEditorsPreferencePageTest.TEST_ASSOCIATED_EDITOR_0,true);
		assertTrue("Associated editors table has to contain '" + FileEditorsPreferencePageTest.TEST_ASSOCIATED_EDITOR_0 + "'",
				fileEditorsPreferencePage.containsAssociatedEditors(FileEditorsPreferencePageTest.TEST_ASSOCIATED_EDITOR_0));
		
		fileEditorsPreferencePage.addAssociatedEditor(FileEditorsPreferencePageTest.TEST_ASSOCIATED_EDITOR_1,true);
		assertTrue("Associated editors table has to contain '" + FileEditorsPreferencePageTest.TEST_ASSOCIATED_EDITOR_1 + "'",
				fileEditorsPreferencePage.containsAssociatedEditors(FileEditorsPreferencePageTest.TEST_ASSOCIATED_EDITOR_1));
		
		fileEditorsPreferencePage.setAssociatedEditorAsDefault(FileEditorsPreferencePageTest.TEST_ASSOCIATED_EDITOR_1);
		
		fileEditorsPreferencePage.removeAssociatedEditor(FileEditorsPreferencePageTest.TEST_ASSOCIATED_EDITOR_0);
		assertFalse("Associated editors table cannot contain '" + FileEditorsPreferencePageTest.TEST_ASSOCIATED_EDITOR_0 + "'",
				fileEditorsPreferencePage.containsAssociatedEditors(FileEditorsPreferencePageTest.TEST_ASSOCIATED_EDITOR_0));
		
		fileEditorsPreferencePage.removeAssociatedEditor(FileEditorsPreferencePageTest.TEST_ASSOCIATED_EDITOR_1);
		assertFalse("Associated editors table cannot contain '" + FileEditorsPreferencePageTest.TEST_ASSOCIATED_EDITOR_1 + "'",
				fileEditorsPreferencePage.containsAssociatedEditors(FileEditorsPreferencePageTest.TEST_ASSOCIATED_EDITOR_1));
		
		fileEditorsPreferencePage.removeFileType(FileEditorsPreferencePageTest.TEST_FILE_TYPE);
		
	}
	@After
	public void tearDown(){
		preferencesDialog.open();
		preferencesDialog.select(fileEditorsPreferencePage);
		if (fileEditorsPreferencePage.containsFileTypes(FileEditorsPreferencePageTest.TEST_FILE_TYPE)) {
			fileEditorsPreferencePage.selectFileTypes(FileEditorsPreferencePageTest.TEST_FILE_TYPE);
			if (fileEditorsPreferencePage.containsAssociatedEditors(FileEditorsPreferencePageTest.TEST_ASSOCIATED_EDITOR_0)){
				fileEditorsPreferencePage.removeAssociatedEditor(FileEditorsPreferencePageTest.TEST_ASSOCIATED_EDITOR_0);
			}
			if (fileEditorsPreferencePage.containsAssociatedEditors(FileEditorsPreferencePageTest.TEST_ASSOCIATED_EDITOR_1)){
				fileEditorsPreferencePage.removeAssociatedEditor(FileEditorsPreferencePageTest.TEST_ASSOCIATED_EDITOR_1);
			}
			fileEditorsPreferencePage.removeFileType(FileEditorsPreferencePageTest.TEST_FILE_TYPE);
		}
		preferencesDialog.ok();
	}
}
