/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.reddeer.eclipse.ui.launcher;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnLayoutData;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.PlatformUI;
import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.common.properties.RedDeerProperties;
import org.jboss.reddeer.common.properties.RedDeerPropertyType;
import org.jboss.reddeer.ui.Activator;

/**
 * Specialized tab for configuring Red Deer properties
 * 
 * @author Lucia Jelinkova
 *
 */
public class RedDeerJUnitTab extends AbstractLaunchConfigurationTab {

	private TableViewer propertiesViewer;

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		setLayout(composite);
		composite.setFont(parent.getFont());
		setControl(composite);

		Group propertiesGroup = new Group(composite, SWT.NONE);
		setLayout(propertiesGroup);
		propertiesGroup.setText("Configuration properties");

		propertiesViewer = createPropertiesSection(propertiesGroup);

		Dialog.applyDialogFont(composite);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), getHelpContextId());
	}

	@Override
	public String getName() {
		return "Red Deer";
	}
	
	@Override
	public Image getImage() {
		return Activator.getDefault().getImageRegistry().get(Activator.REDDEER_RUNNER);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		RedDeerLauncherProperties[] currentInput = RedDeerLauncherProperties.getInitialRedDeerLauncherProperties();
		for (RedDeerLauncherProperties property : currentInput) {
			try {
				property.load(configuration);
			} catch (CoreException e) {
				throw new RedDeerException("Cannot read launcher configuration");
			}
		}
		propertiesViewer.setInput(currentInput);
		propertiesViewer.refresh();
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy config) {
		RedDeerJUnitTab.savePropertiesToLaunchConfiguration(config,
				(RedDeerLauncherProperties[]) propertiesViewer.getInput());
	}

	/**
	 * Saves specified RedDeer Launcher properties to launch configuration
	 * 
	 * @param config
	 * @param properties
	 */
	static void savePropertiesToLaunchConfiguration(ILaunchConfigurationWorkingCopy config,
			RedDeerLauncherProperties[] properties) {
		for (RedDeerLauncherProperties property : properties) {
			property.save(config);
		}
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy config) {
		// no need to set defaults to config, table is initialized with right
		// values
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		boolean isDoubleDefined = false;

		try {
			List<RedDeerLauncherProperties> properties = RedDeerLauncherProperties.loadAll(launchConfig);
			for (RedDeerLauncherProperties property : properties) {
				isDoubleDefined = isDoubleDefined || property.isDoubleDefined();
			}
		} catch (CoreException e) {
			throw new RedDeerException("Cannot read launcher configuration");
		}

		if (isDoubleDefined) {
			setErrorMessage(
					"Some RedDeer parameters are defined on Arguments tab, VM arguments section. You need to remove them.");
		} else {
			setErrorMessage(null);
		}

		return super.isValid(launchConfig) && !isDoubleDefined;
	}

	private void setLayout(Composite composite) {
		GridLayout layout = new GridLayout(1, true);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		composite.setLayout(layout);
		composite.setLayoutData(layoutData);
	}

	private void setLayout(Table table) {
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 90;
		table.setLayoutData(gd);

		ColumnLayoutData[] layouts = { new ColumnWeightData(150, 150), new ColumnWeightData(120, 120) };

		TableLayout layout = new TableLayout();
		for (int i = 0; i < layouts.length; i++) {
			layout.addColumnData(layouts[i]);
		}
		table.setLayout(layout);
	}

	private TableViewer createPropertiesSection(Composite parent) {
		TableViewer viewer = new TableViewer(parent,
				SWT.SINGLE | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

		Table table = viewer.getTable();
		setLayout(table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setFont(parent.getFont());

		TableViewerColumn nameColumn = new TableViewerColumn(viewer, SWT.NONE);
		nameColumn.setLabelProvider(new NameLabelProvider());
		nameColumn.getColumn().setText("Name");
		nameColumn.getColumn().setResizable(true);
		nameColumn.getColumn().setMoveable(true);

		TableViewerColumn valueColumn = new TableViewerColumn(viewer, SWT.NONE);
		valueColumn.setLabelProvider(new ValueLabelProvider());
		valueColumn.getColumn().setText("Value");
		valueColumn.getColumn().setResizable(true);
		valueColumn.getColumn().setMoveable(true);
		valueColumn.setEditingSupport(new RedDeerEditingSupport(viewer));

		viewer.setContentProvider(new ArrayContentProvider());

		return viewer;
	}

	private class NameLabelProvider extends ColumnLabelProvider {

		@Override
		public Image getImage(Object element) {
			return null;
		}

		@Override
		public String getText(Object element) {
			if (element instanceof RedDeerLauncherProperties) {
				RedDeerLauncherProperties property = (RedDeerLauncherProperties) element;
				return property.getProperty().getName();
			}
			return null;
		}
	}

	private class ValueLabelProvider extends ColumnLabelProvider {

		@Override
		public Image getImage(Object element) {
			return null;
		}

		@Override
		public String getText(Object element) {
			if (element instanceof RedDeerLauncherProperties) {
				RedDeerLauncherProperties property = (RedDeerLauncherProperties) element;
				return property.getCurrentValue();
			}
			return null;
		}
	}
	
	private class RedDeerEditingSupport extends EditingSupport {

		public RedDeerEditingSupport(ColumnViewer viewer) {
			super(viewer);
		}

		@Override
		protected boolean canEdit(Object element) {
			return true;
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			if (element instanceof RedDeerLauncherProperties) {
				RedDeerLauncherProperties property = (RedDeerLauncherProperties) element;
				RedDeerProperties prop = ((RedDeerLauncherProperties) element).getProperty();
				
				//use DialogTextEditor for directory properties 
				if (prop.equals(RedDeerProperties.CONFIG_FILE)
						|| prop.equals(RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY)) {
					
					DialogTextEditor dte = new DialogTextEditor((Composite) getViewer().getControl());
					dte.addListener(new CustomCellEditorListener(dte, element));
					return dte;
				} else if (property.getProperty().getType() == RedDeerPropertyType.TEXT) {
					TextCellEditor te = new TextCellEditor((Composite) getViewer().getControl());
					te.addListener(new CustomCellEditorListener(te, element));
					return te;
				} else if (property.getProperty().getType() == RedDeerPropertyType.FLOAT) {
					TextCellEditor te = new TextCellEditor((Composite) getViewer().getControl());
					te.addListener(new CustomCellEditorListener(te, element));
					return te;
				} else {
					ComboBoxViewerCellEditor cellEditor = new ComboBoxViewerCellEditor(
							(Composite) getViewer().getControl(), SWT.READ_ONLY);
					cellEditor.setLabelProvider(new ColumnLabelProvider());
					cellEditor.setContentProvider(new ArrayContentProvider());
					cellEditor.setInput(property.getProperty().getSupportedValues());
					cellEditor.addListener(new CustomCellEditorListener(cellEditor, element));
					return cellEditor;
				}
			}
			return null;
		}

		@Override
		protected Object getValue(Object element) {
			if (element instanceof RedDeerLauncherProperties) {
				RedDeerLauncherProperties property = (RedDeerLauncherProperties) element;
				if (property.getCurrentValue() == null) {
					return "";
				}
				return property.getCurrentValue();
			}

			return null;
		}

		@Override
		protected void setValue(Object element, Object value) {
			if (element instanceof RedDeerLauncherProperties) {
				RedDeerLauncherProperties property = (RedDeerLauncherProperties) element;
				if (value == null || value.equals("")) {
					property.setCurrentValue(null);
				} else {
					property.setCurrentValue(value.toString());
				}
			}

			getViewer().update(element, null);
			setDirty(true);
			updateLaunchConfigurationDialog();
		}

		private class CustomCellEditorListener implements ICellEditorListener {

			private CellEditor editor;
			private Object element;

			public CustomCellEditorListener(CellEditor editor, Object element) {
				this.editor = editor;
				this.element = element;
			}

			@Override
			public void applyEditorValue() {

			}

			@Override
			public void cancelEditor() {

			}

			@Override
			public void editorValueChanged(boolean arg0, boolean arg1) {
				RedDeerEditingSupport.this.setValue(element, editor.getValue());
			}

		}
	}
}
