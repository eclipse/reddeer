/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.workbench.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ILineDiffInfo;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.condition.WidgetIsFound;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.swt.api.Button;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.workbench.impl.editor.Marker;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Editor handler handles operations for Editor instances.
 * @author rawagner
 */
@SuppressWarnings("restriction")
public class EditorHandler extends WorkbenchPartHandler {

    protected final Logger log = Logger.getLogger(this.getClass());

	private static EditorHandler instance;
	
	/**
	 * Gets instance of EditorHandler.
	 * 
	 * @return instance of EditorHandler
	 */
	public static EditorHandler getInstance(){
		if(instance == null){
			instance = new EditorHandler();
		}
		return instance;
	}

    /**
     * Save editor.
     * @param editor to save
     */
    public void save(final IEditorPart editor) {
        log.debug("Saving editor");
        Display.syncExec(new Runnable() {

            @Override
            public void run() {
                editor.doSave(new NullProgressMonitor());
            }
        });
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

        log.debug("Closing editor "+WorkbenchPartHandler.getInstance().getTitle(editor));
        if (isDirty(editor) && save) {
            Display.asyncExec(new Runnable() {

                @Override
                public void run() {
                    IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
                    activeWorkbenchWindow.getActivePage().closeEditor(editor, save);
                }
            });
            Shell saveShell = new DefaultShell(new WithTextMatcher(new RegexMatcher("Save .*")));
            
            WidgetIsFound openButton = new WidgetIsFound(org.eclipse.swt.widgets.Button.class,
            		new WithMnemonicTextMatcher("Save"));
    		
    		
    		Button button;
    		if(openButton.test()){
    			button = new PushButton("Save"); //oxygen changed button text
    		} else {
    			button = new PushButton("Yes");	
    		}
    		button.click();
    		
            new WaitWhile(new ShellIsAvailable(saveShell));
        } else {
            Display.syncExec(new Runnable() {

                @Override
                public void run() {
                    IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
                    activeWorkbenchWindow.getActivePage().closeEditor(editor, save);
                }
            });
        }
        log.debug("Editor " + WorkbenchPartHandler.getInstance().getTitle(editor) + " is closed");

    }

    /**
     * Returns validation markers.
     * @param editor to extract validation markers from
     * @return list of validation markers
     */
    public List<Marker> getMarkers(final IEditorPart editor) {
        List<Marker> markers = new ArrayList<Marker>();
        ITextEditor textEditor = (ITextEditor) editor.getAdapter(ITextEditor.class);
        if (textEditor == null) {
            return markers;
        }
        final IDocumentProvider documentProvider = textEditor.getDocumentProvider();
        if (documentProvider == null) {
            return markers;
        }
        IAnnotationModel model = documentProvider.getAnnotationModel(textEditor.getEditorInput());
        Iterator<?> it = model.getAnnotationIterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof Annotation && !(o instanceof ILineDiffInfo)) {
            	Annotation annotation = (Annotation) o;
            	markers.add(new Marker(annotation, editor));
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
		IEditorReference[] editors = Display.syncExec(new ResultRunnable<IEditorReference[]>() {

			@Override
			public IEditorReference[] run() {
				IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				IEditorReference[] editors = activeWorkbenchWindow.getActivePage().getEditorReferences();
				log.debug(editors.length + " editor(s) found");
				return editors;
			}
		});
		if(editors != null){
			for (IEditorReference editor : editors) {
				close(save, (IEditorPart) editor.getPart(true));
			}
		}
	}
}