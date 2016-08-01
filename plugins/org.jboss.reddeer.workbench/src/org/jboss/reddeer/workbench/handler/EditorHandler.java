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
package org.jboss.reddeer.workbench.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitDocumentProvider.ProblemAnnotation;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.SimpleMarkerAnnotation;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.core.handler.WorkbenchPartHandler;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.workbench.exception.WorkbenchLayerException;
import org.jboss.reddeer.workbench.impl.editor.Marker;

/**
 * Editor handler handles operations for Editor instances.
 * @author rawagner
 */
@SuppressWarnings("restriction")
public class EditorHandler {

    protected final Logger log = Logger.getLogger(this.getClass());

    private static EditorHandler instance;

    private EditorHandler() {

    }

    /**
     * Creates or returns already existing instance.
     * @return EditorHandler instance
     */
    public static EditorHandler getInstance() {
        if (instance == null) {
            instance = new EditorHandler();
        }
        return instance;
    }

    /**
     * Save editor.
     * @param editor to save
     */
    public void save(final IEditorPart editor) {
        activate(editor);
        log.debug("Saving editor");
        Display.syncExec(new Runnable() {

            @Override
            public void run() {
                editor.doSave(new NullProgressMonitor());
            }
        });
    }

    /**
     * Checks if editor is active.
     * @param editor to be checked if it is active
     * @return true if editor is active, false otherwise
     */
    public boolean isActive(final IEditorPart editor) {
        return Display.syncExec(new ResultRunnable<Boolean>() {
            @Override
            public Boolean run() {
                return (editor == PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getActivePage()
                        .getActivePart());
            }
        });
    }

    /**
     * Activates editor.
     * @param editor to activate
     */
    public void activate(final IEditorPart editor) {
        if (!isActive(editor)) {
            log.debug("Activating editor "
                    + WorkbenchPartHandler.getInstance().getTitle(editor));
            Display.syncExec(new Runnable() {

                @Override
                public void run() {
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage().activate(editor);
                    editor.setFocus();
                }
            });
        }
    }

    /**
     * Check if editor is dirty.
     * @param editor to check
     * @return true if editor is dirty, false otherwise
     */
    public boolean isDirty(final IEditorPart editor) {
        return Display.syncExec(new ResultRunnable<Boolean>() {

            @Override
            public Boolean run() {
                return editor.isDirty();
            }
        });
    }
    
    /**
     * Close editor.
     * @param save true if editor should be saved when closing, false otherwise
     * @param editor to close
     */
    public void close(final boolean save, final IEditorPart editor) {

        log.debug("Closing editor "
                + WorkbenchPartHandler.getInstance().getTitle(editor));
        if (isDirty(editor) && save) {
            Display.asyncExec(new Runnable() {

                @Override
                public void run() {
                    IWorkbenchWindow activeWorkbenchWindow = PlatformUI
                            .getWorkbench().getActiveWorkbenchWindow();
                    activeWorkbenchWindow.getActivePage().closeEditor(editor,
                            save);
                }
            });
            new DefaultShell("Save Resource");
            new PushButton("Yes").click();
            new WaitWhile(new ShellWithTextIsAvailable("Save Resource"));
        } else {
            Display.syncExec(new Runnable() {

                @Override
                public void run() {
                    IWorkbenchWindow activeWorkbenchWindow = PlatformUI
                            .getWorkbench().getActiveWorkbenchWindow();
                    activeWorkbenchWindow.getActivePage().closeEditor(editor,
                            save);
                }
            });
        }
        log.debug("Editor "
                + WorkbenchPartHandler.getInstance().getTitle(editor)
                + " is closed");

    }

    /**
     * Returns validation markers.
     * @param editor to extract validation markers from
     * @return list of validation markers
     */
    public List<Marker> getMarkers(final IEditorPart editor) {
        List<Marker> markers = new ArrayList<Marker>();
        ITextEditor textEditor = (ITextEditor) editor
                .getAdapter(ITextEditor.class);
        if (textEditor == null) {
            return markers;
        }
        final IDocumentProvider documentProvider = textEditor
                .getDocumentProvider();
        if (documentProvider == null) {
            return markers;
        }
        IAnnotationModel model = documentProvider.getAnnotationModel(textEditor
                .getEditorInput());
        List<Marker> problemAnnotationMarkers = new ArrayList<Marker>();
        List<Marker> simpleAnnotationMarkers = new ArrayList<Marker>();
        Iterator<?> it = model.getAnnotationIterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof SimpleMarkerAnnotation) {
                simpleAnnotationMarkers.add(new Marker((SimpleMarkerAnnotation) o));
            } else if (o instanceof ProblemAnnotation){
            	ProblemAnnotation annotation = (ProblemAnnotation) o;
            	IDocument doc = documentProvider.getDocument(editor.getEditorInput());
            	int offset = model.getPosition(annotation).getOffset();
            	int line = -1;
            	try {
            		line = doc.getLineOfOffset(offset) + 1;
            	} catch (BadLocationException e) {
            		throw new WorkbenchLayerException("Unable to find line number for AYT marker", e);
            	}
            	problemAnnotationMarkers.add(new Marker(annotation, line));
            }
        }
        // add found SimpleMarkerAnnotation to result list
        markers.addAll(simpleAnnotationMarkers);
        // add found ProblemAnnotation to result but exclude duplicates from simpleAnnotationMarkers
        for (Marker problemAnnotationMarker : problemAnnotationMarkers){
        	if (simpleAnnotationMarkers.contains(problemAnnotationMarker)){
        		simpleAnnotationMarkers.remove(problemAnnotationMarker);
        	}
        	else{
        		markers.add(problemAnnotationMarker);
        	}
        }
        return markers;
    }
    
    /**
     * Closes all editors.
     * @param save performs save operation before closing if true
     */
	public void closeAll(final boolean save) {
		log.trace("Closing all editors");
		IEditorReference[] editors = Display
				.syncExec(new ResultRunnable<IEditorReference[]>() {

					@Override
					public IEditorReference[] run() {
						IWorkbenchWindow activeWorkbenchWindow = PlatformUI
								.getWorkbench().getActiveWorkbenchWindow();
						IEditorReference[] editors = activeWorkbenchWindow
								.getActivePage().getEditorReferences();
						log.debug(editors.length + " editor(s) found");
						return editors;
					}
				});

		for (IEditorReference editor : editors) {
			close(save, (IEditorPart) editor.getPart(true));
		}
	}
}
