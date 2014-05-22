package org.jboss.reddeer.workbench.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.SimpleMarkerAnnotation;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.reddeer.workbench.impl.editor.Marker;

/**
 * Editor handler handles operations for Editor instances.
 * @author rawagner
 */
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
        log.info("Saving editor");
        Display.syncExec(new Runnable() {

            @Override
            public void run() {
                editor.doSave(new NullProgressMonitor());
            }
        });
    }

    /**
     * Checks if editor is active.
     * @param editor to be checked if is active
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
            log.info("Activating editor "
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

        log.info("Closing editor "
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
            new WaitWhile(new ShellWithTextIsActive("Save Resource"));
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
        log.info("Editor "
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
        Iterator<?> it = model.getAnnotationIterator();
        boolean found = false;
        while (it.hasNext() && !found) {
            Object o = it.next();

            if (o instanceof SimpleMarkerAnnotation) {
                SimpleMarkerAnnotation annotation = (SimpleMarkerAnnotation) o;
                markers.add(new Marker(annotation));
            }
        }
        return markers;
    }

}
