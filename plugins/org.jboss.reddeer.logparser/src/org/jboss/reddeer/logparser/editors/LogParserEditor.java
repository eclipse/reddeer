package org.jboss.reddeer.logparser.editors;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.jboss.reddeer.logparser.LogParserActivator;
import org.jboss.reddeer.logparser.LogParserLog;
import org.jboss.reddeer.logparser.model.LogData;
import org.jboss.reddeer.logparser.model.ParseRule;

public class LogParserEditor extends MultiPageEditorPart {
	/**
	 * The ID of the editor as specified by the extension.
	 */
	public static final String ID = "org.jboss.reddeer.logparser.editors.LogParserEditor";
	private static Image findPrevImage;
	private static Image findNextImage;
	private static Image closeFindCompositeImage;
	
	private ReadOnlyTextEditor sourceTextEditor;
	private StyledText parsedLogStyledText;
	private TreeMap<Integer, Integer> parsedLineNumToOrigLineNum;
	private Text findText;
	private Label findStatusLabel;	
	private Composite findComposite;
    
	@Override
	public void doSave(IProgressMonitor monitor) {
		// do nothing it's read-only
	}

	@Override
	public void doSaveAs() {
		// do nothing it's read-only
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (!(input instanceof IURIEditorInput)) {
			throw new PartInitException("Invalid input it must be IURIEditorInput instance");
		}
		super.init(site, input);
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void setFocus() {
		switch (getActivePage()) {
		case 0:
			parsedLogStyledText.setFocus();
			break;
		case 1:
			sourceTextEditor.setFocus();
			break;
		}
	}
	
	public void refreshLog(){
		try {
			sourceTextEditor.getDocumentProvider().resetDocument(sourceTextEditor.getEditorInput());
		} catch (CoreException ce) {
			LogParserLog.logError("Unable to refresh editor content", ce);
		}
	}

	@Override
	protected void createPages() {
		createParsedLogPage();
		createSourcePage();
		updateTitle();
		setParsedLogFont();
		createContextMenu();
	}

	private void createParsedLogPage() {
		Composite parsedLogPageComposite = new Composite (getContainer(),SWT.NONE);
		parsedLogPageComposite.setLayout(new GridLayout(1,false));
		parsedLogPageComposite.setLayoutData(new GridData (GridData.FILL_BOTH));
		parsedLogStyledText = new StyledText(parsedLogPageComposite,
				SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION | SWT.READ_ONLY);
		parsedLogStyledText
				.setText("To parse Log select log within Log Parser view\n and click on 'Parse Log' tool item");
		parsedLogStyledText.setLayoutData(new GridData (GridData.FILL_BOTH));
		findComposite = new Composite(parsedLogPageComposite, SWT.BORDER);
		findComposite.setLayout(new GridLayout(6,false));
		findComposite.setLayoutData(new GridData (GridData.FILL_HORIZONTAL));
		Label label = new Label(findComposite,SWT.NONE);
		label.setText("Find: ");
		findText = new Text(findComposite,SWT.BORDER);
		GridData gdText= new GridData();
		gdText.widthHint = 250;
		findText.setLayoutData(gdText);
		initializeImages();
		final Button prevButton = new Button(findComposite, SWT.PUSH);
		prevButton.setImage(LogParserEditor.findPrevImage);
		prevButton.setToolTipText("Find previous occurrence");
		prevButton.setEnabled(false);
		prevButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				findString(false);
			}
		});
		final Button nextButton = new Button(findComposite, SWT.PUSH);
		nextButton.setImage(LogParserEditor.findNextImage);
		nextButton.setToolTipText("Find next occurrence");
		nextButton.setEnabled(false);
		nextButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				findString(true);
			}
		});
		findText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (findText.getText().length() > 0){
					prevButton.setEnabled(true);
					nextButton.setEnabled(true);
				}
				else{
					prevButton.setEnabled(false);
					nextButton.setEnabled(false);
				}
			}
		});
		findStatusLabel = new Label(findComposite,SWT.NONE);
		findStatusLabel.setLayoutData(new GridData (GridData.FILL_HORIZONTAL));
		Button closeFindButton = new Button(findComposite, SWT.PUSH);
		closeFindButton.setImage(LogParserEditor.closeFindCompositeImage);
		closeFindButton.setToolTipText("Close find composite");
		closeFindButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				closeFindComposite();
			}
		});
		int pageIndex = addPage(parsedLogPageComposite);
		setPageText(pageIndex, "Parsed Log");
	}

	private void createSourcePage() {
		sourceTextEditor = new ReadOnlyTextEditor();
		int pageIndex;
		try {
			pageIndex = addPage(sourceTextEditor, getEditorInput());
			setPageText(pageIndex, "Original Log");
		} catch (PartInitException pie) {
			LogParserLog.logError("Unable to initiliaze Original Log page of LogParser editor", pie);
		}
	}

	private void updateTitle() {
		IEditorInput editorInput = getEditorInput();
		setPartName(editorInput.getName() + " (read-only)");
		setTitleToolTip(editorInput.getToolTipText());
	}

	public void parseLog(LogData logData) {
		parsedLogStyledText.setText("");
		if (parsedLineNumToOrigLineNum == null) {
			parsedLineNumToOrigLineNum = new TreeMap<Integer, Integer>();
		} else {
			parsedLineNumToOrigLineNum.clear();
		}
		LineNumberReader lineNumberReader = new LineNumberReader(new StringReader(
				sourceTextEditor.getDocumentProvider().getDocument(sourceTextEditor.getEditorInput()).get()));
		String line;
		try {
			TreeMap<Integer, String> originalLogLines = new TreeMap<Integer, String>();
			TreeMap<Integer, ParseRule> parsedLogIncludeLines = new TreeMap<Integer, ParseRule>();
			while ((line = lineNumberReader.readLine()) != null) {
				int lineNumber = lineNumberReader.getLineNumber();
				originalLogLines.put(lineNumber, line);
				boolean notIncludeLine = true;
				ParseRule parseRule = getDefaultParseRule();
				if (logData.getParseRules() != null && !logData.getParseRules().isEmpty() && line != null
						&& line.length() > 0) {
					boolean excludeRegexNotApplied = true;
					Iterator<ParseRule> itParseRule = logData.getParseRules().iterator();
					while (notIncludeLine && excludeRegexNotApplied && itParseRule.hasNext()) {
						parseRule = itParseRule.next();
						boolean includeRegexMatches = true;
						if (parseRule.getIncludeRegex() != null && parseRule.getIncludeRegex().length() > 0) {
							includeRegexMatches = line.matches(parseRule.getIncludeRegex());
						}
						if (includeRegexMatches) {
							if (parseRule.getExcludeRegex() != null && parseRule.getExcludeRegex().length() > 0) {
								notIncludeLine = line.matches(parseRule.getExcludeRegex());
								if (notIncludeLine) {
									excludeRegexNotApplied = false;
								}
							} else {
								notIncludeLine = false;
							}
						}
					}
				} else {
					notIncludeLine = line == null || line.length() == 0;
				}
				if (!notIncludeLine) {
					int addLinesFrom = lineNumber;
					int addLinesTo = lineNumber;
					addLinesFrom -= parseRule.getDisplayLinesBefore();
					if (addLinesFrom < 0) {
						addLinesFrom = 0;
					}
					addLinesTo += parseRule.getDisplaylinesAfter();

					for (int addLineNumber = addLinesFrom; addLineNumber <= addLinesTo; addLineNumber++) {
						if (!parsedLogIncludeLines.containsKey(addLineNumber)) {
							parsedLogIncludeLines.put(addLineNumber, parseRule);
						}
					}
				}
			}
			Iterator<Integer> itIncludeLine = parsedLogIncludeLines.keySet().iterator();
			int addLineNumber = 0;
			while (itIncludeLine.hasNext()) {
				int originalLineNumber = itIncludeLine.next();
				parsedLogIncludeLines.get(originalLineNumber);
				String originalLine = originalLogLines.get(originalLineNumber);
				if (originalLine != null) {
					ParseRule appliedParseRule = parsedLogIncludeLines.get(originalLineNumber);
					String insertLine = originalLine;
					insertLine = fillString(appliedParseRule.getIndent(), ' ') + appliedParseRule.getPrefix()
							+ originalLine;
					parsedLogStyledText.append(insertLine + "\n");
					parsedLineNumToOrigLineNum.put(addLineNumber, originalLineNumber);
					addLineNumber++;
				}
			}
		} catch (IOException ioe) {
			LogParserLog.logError("Error while parsing log", ioe);
		}

	}

	public void showSelectedParsedLineInOriginalLog(){
		int selectedParsedLine = parsedLogStyledText.getLineAtOffset(parsedLogStyledText.getSelection().x);
		setActivePage(1);
		int selectedOriginalLine = 0;
		Integer integerLineNum = selectedOriginalLine = parsedLineNumToOrigLineNum.get(selectedParsedLine);
		if (integerLineNum != null){
			selectedOriginalLine = integerLineNum - 1;
		}
		getSourceEditorStyledText().setSelection(getSourceEditorStyledText().getOffsetAtLine(selectedOriginalLine));
	}
	
	public static void disposeImages(){
		if (LogParserEditor.findPrevImage != null) {
			LogParserEditor.findPrevImage.dispose();
			LogParserEditor.findPrevImage = null;
		}
		if (LogParserEditor.findNextImage != null) {
			LogParserEditor.findNextImage.dispose();
			LogParserEditor.findNextImage = null;
		}
		if (LogParserEditor.closeFindCompositeImage != null) {
			LogParserEditor.closeFindCompositeImage.dispose();
			LogParserEditor.closeFindCompositeImage = null;
		}
	}
	
	private static void initializeImages(){
		if (LogParserEditor.findPrevImage == null) {
			LogParserEditor.findPrevImage = LogParserActivator.getImageDescriptor("icons/prev.gif").createImage();
		}
		if (LogParserEditor.findNextImage == null) {
			LogParserEditor.findNextImage = LogParserActivator.getImageDescriptor("icons/next.gif").createImage();
		}
		if (LogParserEditor.closeFindCompositeImage == null) {
			LogParserEditor.closeFindCompositeImage = LogParserActivator.getImageDescriptor("icons/closefind.gif").createImage();
		}
	}
	
	private void findString(boolean forward){
		String searchFor = findText.getText();
		if (searchFor != null && searchFor.length() > 0 && parsedLogStyledText.getText().length() > 0) {
			int searchLineIndex = parsedLogStyledText.getLineAtOffset(parsedLogStyledText.getSelection().x);
			int searchColumnIndex = parsedLogStyledText.getSelection().y
					- parsedLogStyledText.getOffsetAtLine(searchLineIndex);
			String searchText = parsedLogStyledText.getLine(searchLineIndex);
			int foundIndex = -1;
			if (forward){
				searchText = searchText.substring(searchColumnIndex);
				foundIndex = searchText.indexOf(searchFor);
				if (foundIndex != - 1){
					foundIndex += searchColumnIndex;
				}
				else{
					searchLineIndex++;
				}
			}
			else{
				if (searchColumnIndex > 0){
					searchText = searchText.substring(0, searchColumnIndex - 1);
					foundIndex = searchText.lastIndexOf(searchFor);
				}
				if (foundIndex == - 1){
					searchLineIndex--;
				}
			}
			
			while (foundIndex == -1 && searchLineIndex >= 0 && searchLineIndex < parsedLogStyledText.getLineCount()){
				if (forward){
					foundIndex = parsedLogStyledText.getLine(searchLineIndex).indexOf(searchFor);
					if (foundIndex == - 1){
						searchLineIndex++;
					}
				}
				else{
					foundIndex = parsedLogStyledText.getLine(searchLineIndex).lastIndexOf(searchFor);
					if (foundIndex == - 1){
						searchLineIndex--;	
					}					
				}
			}
			
			if (foundIndex != -1){
				int start = parsedLogStyledText.getOffsetAtLine(searchLineIndex) + foundIndex; 
				parsedLogStyledText.setSelection(start, start + searchFor.length());
				findStatusLabel.setText("");
			}
			else{
				findStatusLabel.setText("Text not found.");
			}
			
		}
		
	}
	
	private void closeFindComposite(){
		((GridData)findComposite.getLayoutData()).exclude = true;
		findComposite.setVisible(false);		
		findComposite.getShell().layout(true,true);
	}
	
	public void showFindComposite(){
		if (!findComposite.isVisible()){
			((GridData)findComposite.getLayoutData()).exclude = false;
			findComposite.setVisible(true);		
			findComposite.getShell().layout(true,true);
		}
		findText.setText(parsedLogStyledText.getSelectionText());
	}
	
	private ParseRule getDefaultParseRule() {
		ParseRule defautlParseRule = new ParseRule();

		defautlParseRule.setIncludeRegex("");
		defautlParseRule.setExcludeRegex("");
		defautlParseRule.setIndent(0);
		defautlParseRule.setDisplayLinesBefore(0);
		defautlParseRule.setDisplaylinesAfter(0);
		defautlParseRule.setPrefix("");

		return defautlParseRule;
	}

	private String fillString(int length, char charToFill) {
		String result = "";
		if (length > 0) {
			char[] array = new char[length];
			Arrays.fill(array, charToFill);
			result = new String(array);
		}
		return result;
	}

	private void setParsedLogFont() {
		parsedLogStyledText.setFont(getSourceEditorStyledText().getFont());
	}

	private void createContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager m) {
				fillContextMenu(m);
			}
		});
		Menu menu = menuMgr.createContextMenu(parsedLogStyledText);
		parsedLogStyledText.setMenu(menu);
		getSite().registerContextMenu(menuMgr,null);
	}

	private void fillContextMenu(IMenuManager menuManager) {
		menuManager.add(new Separator("edit"));
		menuManager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private StyledText getSourceEditorStyledText(){
		Object control = sourceTextEditor.getAdapter(Control.class);
		return (StyledText)control;
	}
	
}
