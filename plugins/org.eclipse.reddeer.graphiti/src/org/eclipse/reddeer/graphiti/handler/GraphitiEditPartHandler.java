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
package org.eclipse.reddeer.graphiti.handler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.graphiti.features.context.IDoubleClickContext;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.tb.IContextButtonEntry;
import org.eclipse.graphiti.tb.IContextButtonPadData;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.graphiti.GraphitiLayerException;
import org.eclipse.reddeer.graphiti.api.ContextButton;
import org.eclipse.reddeer.graphiti.impl.contextbutton.internal.BasicContextButton;
import org.eclipse.reddeer.graphiti.lookup.DiagramEditorLookup;

/**
 * Handler for Graphiti UI operations.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class GraphitiEditPartHandler {

	protected final Logger log = Logger.getLogger(this.getClass());
	private static GraphitiEditPartHandler instance;
	
	/**
	 * Gets instance of GraphitiEditPartHandler.
	 * 
	 * @return instance of GraphitiEditPartHandler
	 */
	public static GraphitiEditPartHandler getInstance(){
		if(instance == null){
			instance = new GraphitiEditPartHandler();
		}
		return instance;
	}

	/**
	 * Returns list of context buttons of the specified edit part.
	 * 
	 * @param editPart
	 *            Edit part
	 * @return List of context buttons
	 */
	public List<ContextButton> getContextButtons(final org.eclipse.gef.EditPart editPart) {
		return getContextButtons(DiagramEditorLookup.getInstance().findDiagramEditor(), editPart);
	}

	private List<ContextButton> getContextButtons(final org.eclipse.graphiti.ui.editor.DiagramEditor diagramEditor,
			final org.eclipse.gef.EditPart editPart) {
		return Display.syncExec(new ResultRunnable<List<ContextButton>>() {

			@Override
			public List<ContextButton> run() {
				List<IContextButtonEntry> entries = new ArrayList<IContextButtonEntry>();
				IToolBehaviorProvider[] tool = diagramEditor.getDiagramTypeProvider()
						.getAvailableToolBehaviorProviders();
				for (int i = 0; i < tool.length; i++) {
					IPictogramElementContext context = createPictogramContext(editPart);
					IContextButtonPadData pad = tool[i].getContextButtonPad(context);
					entries.addAll(pad.getDomainSpecificContextButtons());
					entries.addAll(pad.getGenericContextButtons());
				}
				List<ContextButton> contextButtonEntries = new ArrayList<ContextButton>();
				for (IContextButtonEntry entry : entries) {
					ContextButton contextButtonEntry = new BasicContextButton(entry);
					contextButtonEntries.add(contextButtonEntry);
				}
				return contextButtonEntries;
			}
		});
	}

	/**
	 * Calls double click feature on the specified edit part.
	 * 
	 * @param editPart
	 *            Edit Part
	 */
	public void doubleClick(final org.eclipse.gef.EditPart editPart) {
		doubleClick(DiagramEditorLookup.getInstance().findDiagramEditor(), editPart);
	}

	private void doubleClick(final org.eclipse.graphiti.ui.editor.DiagramEditor diagramEditor,
			final org.eclipse.gef.EditPart editPart) {
		List<ICustomFeature> features = Display.syncExec(new ResultRunnable<List<ICustomFeature>>() {

			@Override
			public List<ICustomFeature> run() {
				List<ICustomFeature> features = new ArrayList<ICustomFeature>();
				IToolBehaviorProvider[] tool = diagramEditor.getDiagramTypeProvider()
						.getAvailableToolBehaviorProviders();
				for (int i = 0; i < tool.length; i++) {
					IDoubleClickContext context = createPictogramContext(editPart);
					ICustomFeature feature = tool[i].getDoubleClickFeature(context);
					if (feature != null) {
						features.add(feature);
					}
				}
				return features;
			}
		});
		if (features.isEmpty()) {
			throw new GraphitiLayerException("Cannot call double click");
		}
		for (final ICustomFeature feature : features) {
			Display.getDisplay().asyncExec(new Runnable() {

				@Override
				public void run() {
					feature.execute(createPictogramContext(editPart));
				}
			});
		}
		Display.getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {

			}
		});
	}

	private PictogramContext createPictogramContext(final org.eclipse.gef.EditPart part) {
		PictogramElement pe = null;
		
		try {
			Method method = part.getClass().getMethod("getPictogramElement");
			Object obj = method.invoke(part);
			pe = (PictogramElement) obj;
		} catch (Exception e) {
			throw new RuntimeException("Cannot create PictogramElementContext", e);
		}
		
		if (pe == null) {
			throw new RuntimeException("Cannot create PictogramElementContext, pe is null");
		}
		return new PictogramContext(pe);
	}

	private class PictogramContext extends CustomContext implements IPictogramElementContext, IDoubleClickContext {

		public PictogramContext(PictogramElement pictogramElement) {
			super(new PictogramElement[] { pictogramElement });
		}

		@Override
		public PictogramElement getPictogramElement() {
			return getPictogramElements()[0];
		}

	}
}
