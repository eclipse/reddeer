package org.jboss.reddeer.jface.test.viewer;

import static org.junit.Assert.*;

import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
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
import org.jboss.reddeer.jface.exception.JFaceLayerException;
import org.jboss.reddeer.jface.viewer.handler.TreeViewerHandler;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class TreeViewerHandlerTest {

	private static String title = "Testing shell";
	
	private TreeViewerHandler treeViewerHandler = TreeViewerHandler.getInstance();
	
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
				shell.setLayout(new GridLayout(2, false));
				
				Composite composite = createPartControl(shell);
				composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2,
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
				for (Shell shell : org.jboss.reddeer.swt.util.Display.getDisplay().getShells()) {
					if (shell.getText().equals(title)) {
						shell.dispose();
						break;
					}
				}
				new WaitWhile(new ShellWithTextIsActive(title));
			}
		});
	}
	
	
	@Test
	public void nonStyledTextTreeItem1Tree1() {
		String txt = treeViewerHandler.getNonStyledText(new DefaultTree(0).getItems().get(0));
		assertTrue("Non-styled was "+ "'" + txt + "', but should be '0nonstyled1'", txt.equals("0nonstyled1"));
	}
	
	@Test
	public void nonStyledTextTreeItem2Tree1() {
		String txt = treeViewerHandler.getNonStyledText(new DefaultTree(0).getItems().get(1));
		assertTrue("Non-styled was "+ "'" + txt + "', but should be '1nonstyled1'", txt.equals("1nonstyled1"));
	}
	
	@Test
	public void nonStyledTextTreeItem3Tree1() {
		String txt = treeViewerHandler.getNonStyledText(new DefaultTree(0).getItems().get(2));
		assertTrue("Non-styled was "+ "'" + txt + "', but should be '2nonstyled1'", txt.equals("2nonstyled1"));
	}
	
	@Test
	public void nonStyledTextTreeItem4Tree1() {
		String txt = treeViewerHandler.getNonStyledText(new DefaultTree(0).getItems().get(3));
		assertTrue("Non-styled was "+ "'" + txt + "', but should be '3nonstyled1'", txt.equals("3nonstyled1"));
	}
	
	@Test 
	public void getStyledPrefixTreeItem2Tree1() {
		String txt = treeViewerHandler.getStyledTexts(new DefaultTree(0).getItems().get(1))[0];
		assertTrue("Styled prefix was "+ "'" + txt + "', but should be '1spre1'", txt.equals("1spre1"));
	}
	
	@Test 
	public void getStyledPrefixTreeItem4Tree1() {
		String txt = treeViewerHandler.getStyledTexts(new DefaultTree(0).getItems().get(3))[0];		
		assertTrue("Styled prefix was "+ "'" + txt + "', but should be '3spre1'", txt.equals("3spre1"));
	}
	
	@Test
	public void getStyledPostfixTreeItem3Tree1() {
		String txt = treeViewerHandler.getStyledTexts(new DefaultTree(0).getItems().get(2))[0];
		assertTrue("Styled postfix was "+ "'" + txt + "', but should be '2spost1'", txt.equals("2spost1"));
	}
	
	@Test
	public void getStyledPostfixTreeItem4Tree1() {
		String txt = treeViewerHandler.getStyledTexts(new DefaultTree(0).getItems().get(3))[1];		
		assertTrue("Styled postfix was "+ "'" + txt + "', but should be '3spost1'", txt.equals("3spost1"));
	}
	
	@Test
	public void treeItem1Tree2() {
		try {
			treeViewerHandler.getTreeItem(new DefaultTree(1), "0nonstyled2");
		} catch (JFaceLayerException ex) {
			fail("Cannot get TreeItem 1 from second tree");
		}
	}
	
	@Test
	public void treeItem1Tree1() {
		try {
			treeViewerHandler.getTreeItem(new DefaultTree(0), new String[] {"0nonstyled1"});
		} catch (JFaceLayerException ex) {
			fail("Cannot get TreeItem 1 from first tree");
		}
	}
	
	@Test
	public void treeItem1PathTree2() {
		try {
			treeViewerHandler.getTreeItem(new DefaultTree(1), new String[] {"0nonstyled2"});
		} catch (JFaceLayerException ex) {
			fail("Cannot get TreeItem 1 from second tree");
		}
	}
	
	@Test
	public void treeItem2Tree2() {
		try {
			treeViewerHandler.getTreeItem(new DefaultTree(1), "1nonstyled2");
		} catch (JFaceLayerException ex) {
			fail("Cannot get TreeItem 2 from second tree");
		}
	}
	
	@Test
	public void treeItem3Tree2() {
		try {
			treeViewerHandler.getTreeItem(new DefaultTree(1), "2nonstyled2");
		} catch (JFaceLayerException ex) {
			fail("Cannot get TreeItem 3 from second tree");
		}
	}
	
	@Test
	public void treeItem4Tree2() {
		try {
			treeViewerHandler.getTreeItem(new DefaultTree(1), "3nonstyled2");
		} catch (JFaceLayerException ex) {
			fail("Cannot get TreeItem 4 from second tree");
		}
	}
	
	private Composite createPartControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);

		composite.setLayout(new GridLayout(2, true));

		Label label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		label.setText("Tree with decorators #1");
		
		Label label2 = new Label(composite, SWT.NONE);
		label2.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
		label2.setText("Tree with decorators #2");

		// First tree  viewer
		final TreeViewer treeViewer = new TreeViewer(composite, SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL);
		
		StyledTreeItemLabelProvider labelProvider = new StyledTreeItemLabelProvider(1);
		TreeItemProvider treeItemProvider = new TreeItemProvider();
		
		treeViewer.setLabelProvider(labelProvider);
		treeViewer.setContentProvider(treeItemProvider);

		GridData data = new GridData(GridData.BEGINNING, GridData.FILL, true, true);
		treeViewer.getControl().setLayoutData(data);
		treeViewer.setInput(new Object());

		// Second tree viewer
		final TreeViewer treeViewer2 = new TreeViewer(composite, SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL);
		
		StyledTreeItemLabelProvider labelProvider2 = new StyledTreeItemLabelProvider(2);
		TreeItemProvider treeItemProvider2 = new TreeItemProvider();
		
		treeViewer2.setLabelProvider(labelProvider2);
		treeViewer2.setContentProvider(treeItemProvider2);

		GridData data2 = new GridData(GridData.END, GridData.FILL, true, true);
		treeViewer2.getControl().setLayoutData(data2);
		treeViewer2.setInput(new Object());
		
		return composite;
	}

	private static class StyledTreeItemLabelProvider extends StyledCellLabelProvider {

		private int modifier;
		
		public StyledTreeItemLabelProvider(int modifier) {
			this.modifier = modifier;
		}

		@Override
		public void update(ViewerCell cell) {
			// result on given cell text
			String cellText = (String) cell.getElement();
			
			StyledString styledLabel = new StyledString();

			String customLabel;
			if (cellText.contains("1")) {
				if (cellText.contains("2")) {
					customLabel = "3";
				} else {
					customLabel = "1";
				}
			} else {
				if (cellText.contains("2")) {
					customLabel = "2";
				} else {
					customLabel = "0";
				}
			}
			
			if (cellText.contains("1")) {
				styledLabel.append(customLabel + "spre" + modifier, StyledString.COUNTER_STYLER);
				styledLabel.append(" ", null);
			}

			styledLabel.append(customLabel + "nonstyled" + modifier, null);
			
			if (cellText.contains("2")) {
				styledLabel.append(" ", null);
				styledLabel.append(customLabel + "spost" + modifier, StyledString.COUNTER_STYLER);
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
