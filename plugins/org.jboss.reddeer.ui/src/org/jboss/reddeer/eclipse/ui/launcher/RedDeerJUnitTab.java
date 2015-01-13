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
	public void initializeFrom(ILaunchConfiguration configuration) {
		RedDeerLauncherProperties[] currentInput = getDefaultRedDeerLauncherProperties();
		for (RedDeerLauncherProperties property : currentInput){
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
		RedDeerLauncherProperties[] currentInput = (RedDeerLauncherProperties[]) propertiesViewer.getInput();
		for (RedDeerLauncherProperties property : currentInput){
			property.save(config);
		}
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy config) {
		RedDeerLauncherProperties[] currentInput = (RedDeerLauncherProperties[]) propertiesViewer.getInput();
		for (RedDeerLauncherProperties property : currentInput){
			property.setDefaults(config);
		}
		propertiesViewer.refresh();
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		boolean isDoubleDefined = false;

		try {
			List<RedDeerLauncherProperties> properties = RedDeerLauncherProperties.loadAll(launchConfig);
			for (RedDeerLauncherProperties property : properties){
				isDoubleDefined = isDoubleDefined || property.isDoubleDefined();
			}
		} catch (CoreException e) {
			throw new RedDeerException("Cannot read launcher configuration");
		}

		if (isDoubleDefined){
			setErrorMessage("Some RedDeer parameters are defined on Arguments tab, VM arguments section. You need to remove them.");
		} else {
			setErrorMessage(null);
		}

		return super.isValid(launchConfig) && !isDoubleDefined;
	}

	private void setLayout(Composite composite){
		GridLayout layout = new GridLayout(1, true);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		composite.setLayout(layout);
		composite.setLayoutData(layoutData);
	}

	private void setLayout(Table table){
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 90;
		table.setLayoutData(gd);

		ColumnLayoutData[] layouts= {
				new ColumnWeightData(150,150),
				new ColumnWeightData(120,120)
		};

		TableLayout layout = new TableLayout();
		for (int i = 0; i < layouts.length; i++) {
			layout.addColumnData(layouts[i]);
		}
		table.setLayout(layout);
	}

	private TableViewer createPropertiesSection(Composite parent) {
		TableViewer viewer = new TableViewer(parent, SWT.SINGLE | SWT.FULL_SELECTION | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER);

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

		viewer.setContentProvider(new  ArrayContentProvider());
		viewer.setInput(getDefaultRedDeerLauncherProperties());
		return viewer;
	}

	private RedDeerLauncherProperties[] getDefaultRedDeerLauncherProperties(){
		RedDeerProperties[] properties = RedDeerProperties.values();
		RedDeerLauncherProperties[] tabProperties = new RedDeerLauncherProperties[properties.length];

		for (int i = 0; i < properties.length; i++){
			tabProperties[i] = new RedDeerLauncherProperties(properties[i]);
		}
		return tabProperties;
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
			if (element instanceof RedDeerLauncherProperties){
				RedDeerLauncherProperties property = (RedDeerLauncherProperties) element;

				if (property.getProperty().getType() == RedDeerPropertyType.TEXT){
					return new TextCellEditor((Composite) getViewer().getControl());
				} else {
					ComboBoxViewerCellEditor cellEditor = new ComboBoxViewerCellEditor((Composite) getViewer().getControl(), SWT.READ_ONLY);
					cellEditor.setLabelProvider(new ColumnLabelProvider());
					cellEditor.setContentProvider(new ArrayContentProvider());
					cellEditor.setInput(property.getProperty().getSupportedValues());
					return cellEditor;
				}
			}
			return null;
		}

		@Override
		protected Object getValue(Object element) {
			if (element instanceof RedDeerLauncherProperties){
				RedDeerLauncherProperties property = (RedDeerLauncherProperties) element;
				return property.getCurrentValue();
			}

			return null;
		}

		@Override
		protected void setValue(Object element, Object value) {
			if (element instanceof RedDeerLauncherProperties){
				RedDeerLauncherProperties property = (RedDeerLauncherProperties) element;
				property.setCurrentValue(value.toString());
			}

			getViewer().update(element, null);
			setDirty(true);
			updateLaunchConfigurationDialog();
		}
	}
}
