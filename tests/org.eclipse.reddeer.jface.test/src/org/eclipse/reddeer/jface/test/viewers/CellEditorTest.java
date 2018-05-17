/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.jface.test.viewers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.reddeer.jface.viewers.CellEditor;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.impl.table.DefaultTableItem;
import org.eclipse.reddeer.swt.impl.text.DefaultText;
import org.eclipse.reddeer.swt.test.SWTLayerTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class CellEditorTest extends SWTLayerTestCase {

	@Test
	public void tableEditorTest() {
		TableItem tableItem = new DefaultTableItem();

		CellEditor editor = new CellEditor(tableItem);
		editor.activate();
		new DefaultText(editor).setText("abc");
		editor.deactivate();

		assertEquals("abc", tableItem.getText());
	}

	private class Person {
		private String name;
		private String surname;

		public Person(String name, String surname) {
			this.name = name;
			this.surname = surname;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSurname() {
			return surname;
		}

		public void setSurname(String surname) {
			this.surname = surname;
		}

	}

	@Override
	protected void createControls(Shell shell) {
		shell.setLayout(new FillLayout());

		// define the TableViewer
		TableViewer viewer = new TableViewer(shell, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION
				| SWT.BORDER);

		// create a column for the first name
		TableViewerColumn colFirstName = new TableViewerColumn(viewer, SWT.NONE);
		colFirstName.getColumn().setWidth(200);
		colFirstName.getColumn().setText("Firstname");
		colFirstName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Person p = (Person) element;
				return p.getName();
			}
		});
		colFirstName.setEditingSupport(new FirstNameEditingSupport(viewer));
		TableViewerColumn colSurName = new TableViewerColumn(viewer, SWT.NONE);
		colSurName.getColumn().setWidth(200);
		colSurName.getColumn().setText("Surname");
		colSurName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Person p = (Person) element;
				return p.getSurname();
			}
		});

		// make lines and header visible
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		// set the content provider
		viewer.setContentProvider(ArrayContentProvider.getInstance());

		// provide the input to the viewer
		// setInput() calls getElements() on the
		// content provider instance
		List<Person> data = new ArrayList<Person>();
		data.add(new Person("Andrej", "Podhradsky"));
		data.add(new Person("John", "Doe"));

		viewer.setInput(data);

	}

	private class FirstNameEditingSupport extends EditingSupport {

		private final TableViewer viewer;
		private final org.eclipse.jface.viewers.CellEditor editor;

		public FirstNameEditingSupport(TableViewer viewer) {
			super(viewer);
			this.viewer = viewer;
			this.editor = new TextCellEditor(viewer.getTable());
		}

		@Override
		protected org.eclipse.jface.viewers.CellEditor getCellEditor(Object element) {
			return editor;
		}

		@Override
		protected boolean canEdit(Object element) {
			return true;
		}

		@Override
		protected Object getValue(Object element) {
			return ((Person) element).getName();
		}

		@Override
		protected void setValue(Object element, Object value) {
			((Person) element).setName(String.valueOf(value));
			viewer.update(element, null);
		}
	}

}
