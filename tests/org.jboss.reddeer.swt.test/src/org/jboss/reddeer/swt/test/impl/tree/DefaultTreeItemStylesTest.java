package org.jboss.reddeer.swt.test.impl.tree;

import static org.junit.Assert.assertTrue;

import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.test.utils.ShellTestUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class DefaultTreeItemStylesTest {

	private static String title = "Testing shell";
	
	@Before
	public void setUp() {
		org.jboss.reddeer.swt.util.Display.syncExec(new Runnable() {
			@Override
			public void run() {
				JFaceResources.getColorRegistry().put(JFacePreferences.COUNTER_COLOR,
						new RGB(0, 127, 174));

				Shell shell = new Shell(Display.getDefault(), SWT.CLOSE | SWT.RESIZE);
				shell.setText(title);
				shell.setSize(400, 400);
				shell.setLayout(new GridLayout(1, false));
				
				Composite composite = createPartControl(shell);
				composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
						1));
				
				shell.open();
			}
		});
	}

	@After
	public void cleanup() {
		org.jboss.reddeer.swt.util.Display.syncExec(new Runnable() {
			@Override
			public void run() {
				ShellTestUtils.closeShell(title);
			}
		});
	}
	
	
	@Test
	public void nonStyledTextTreeItem1() {
		String txt = new DefaultTree().getItems().get(0).getNonStyledText();
		assertTrue("Non-styled was "+ "'" + txt + "', but should be 'nonstyled'", txt.equals("nonstyledtext"));
	}
	
	@Test
	public void nonStyledTextTreeItem2() {
		String txt = new DefaultTree().getItems().get(1).getNonStyledText();
		assertTrue("Non-styled was "+ "'" + txt + "', but should be 'nonstyled'", txt.equals("nonstyledtext"));
	}
	
	@Test
	public void nonStyledTextTreeItem3() {
		String txt = new DefaultTree().getItems().get(2).getNonStyledText();
		assertTrue("Non-styled was "+ "'" + txt + "', but should be 'nonstyled'", txt.equals("nonstyledtext"));
	}
	
	@Test
	public void nonStyledTextTreeItem4() {
		String txt = new DefaultTree().getItems().get(3).getNonStyledText();
		assertTrue("Non-styled was "+ "'" + txt + "', but should be 'nonstyled'", txt.equals("nonstyledtext"));
	}
	
	@Test 
	public void getStyledPrefixTreeItem2() {
		String txt = new DefaultTree().getItems().get(1).getStyledTexts()[0];
		assertTrue("Styled prefix was "+ "'" + txt + "', but should be 'spre'", txt.equals("spre"));
	}
	
	@Test 
	public void getStyledPrefixTreeItem4() {
		String txt = new DefaultTree().getItems().get(3).getStyledTexts()[0];
		assertTrue("Styled prefix was "+ "'" + txt + "', but should be 'spre'", txt.equals("spre"));
	}
	
	@Test
	public void getStyledPostfixTreeItem3() {
		String txt = new DefaultTree().getItems().get(2).getStyledTexts()[0];
		assertTrue("Styled postfix was "+ "'" + txt + "', but should be 'spost'", txt.equals("spost"));
	}
	
	@Test
	public void getStyledPostfixTreeItem4() {
		String txt = new DefaultTree().getItems().get(3).getStyledTexts()[1];
		assertTrue("Styled postfix was "+ "'" + txt + "', but should be 'spost'", txt.equals("spost"));
	}
	
	private Composite createPartControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);

		composite.setLayout(new GridLayout(1, true));

		Label label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		label.setText("Viewer with a StyledCellLabelProvider:");

		final TreeViewer treeViewer = new TreeViewer(composite, SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL);
		
		StyledTreeItemLabelProvider labelProvider = new StyledTreeItemLabelProvider();
		TreeItemProvider treeItemProvider = new TreeItemProvider();
		
		treeViewer.setLabelProvider(labelProvider);
		treeViewer.setContentProvider(treeItemProvider);

		GridData data = new GridData(GridData.FILL, GridData.FILL, true, true);
		treeViewer.getControl().setLayoutData(data);
		treeViewer.setInput(new Object());

		return composite;
	}

	private static class StyledTreeItemLabelProvider extends StyledCellLabelProvider {

		public StyledTreeItemLabelProvider() {
		}

		@Override
		public void update(ViewerCell cell) {
			// result on given cell text
			String cellText = (String) cell.getElement();
			
			StyledString styledLabel = new StyledString();
			
			if (cellText.contains("1")) {
				styledLabel.append("spre", StyledString.COUNTER_STYLER);
				styledLabel.append(" ", null);
			}
	
			styledLabel.append("nonstyledtext", null);
			
			if (cellText.contains("2")) {
				styledLabel.append(" ", null);
				styledLabel.append("spost", StyledString.COUNTER_STYLER);
			}
			
			cell.setText(styledLabel.toString());
			cell.setStyleRanges(styledLabel.getStyleRanges());

			super.update(cell);
		}

		@Override
		protected void measure(Event event, Object element) {
			super.measure(event, element);
		}
	}
	
	private static class TreeItemProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(Object element) {
			String[] items = new String[4];
			// 1 mean styled prefix, 0 non-styled text, 2 styled postfix
			items[0] = "0";
			items[1] = "1 0";
			items[2] = "0 2";
			items[3] = "1 0 2";
			return items;
		}

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@Override
		public Object[] getChildren(Object arg0) {
			return null;
		}

		@Override
		public Object getParent(Object arg0) {
			return null;
		}

		@Override
		public boolean hasChildren(Object arg0) {
			return false;
		}
	}
}
