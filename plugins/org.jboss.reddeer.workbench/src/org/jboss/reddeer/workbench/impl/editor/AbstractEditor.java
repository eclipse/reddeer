package org.jboss.reddeer.workbench.impl.editor;

import java.util.List;

import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.part.WorkbenchPart;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.jface.text.contentassist.ContentAssistant;
import org.jboss.reddeer.common.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.keyboard.KeyboardFactory;
import org.jboss.reddeer.core.lookup.ShellLookup;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.common.wait.AbstractWait;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.workbench.api.Editor;
import org.jboss.reddeer.workbench.condition.ContentAssistantShellIsOpened;
import org.jboss.reddeer.workbench.exception.WorkbenchLayerException;
import org.jboss.reddeer.workbench.handler.EditorHandler;
import org.jboss.reddeer.core.handler.WorkbenchPartHandler;
import org.jboss.reddeer.core.lookup.EditorPartLookup;
import org.jboss.reddeer.core.matcher.EditorPartTitleMatcher;

/**
 * Abstract class for all Editor implementations.
 * @author rawagner
 */
public class AbstractEditor implements Editor {

    protected static final Logger log = Logger.getLogger(AbstractEditor.class);
    
    protected IEditorPart editorPart;

    /**
     * Initialize editor.
     */
    public AbstractEditor() {
       editorPart = EditorPartLookup.getInstance().getEditor();
       activate();
    }

    /**
     * Initialize editor with given title and activate it.
     * @param title Title of editor to initialize and activate
     */
    public AbstractEditor(String title) {
        this(new WithTextMatcher(title));
    }

    /**
     * Initialize editor with given title matcher.
     * @param title Title matcher of editor to initialize and activate
     */
    @SuppressWarnings("unchecked")
	public AbstractEditor(Matcher<String> title) {
        this(new EditorPartTitleMatcher(title));
    }
    
    /**
     * Initialize editor that matches given matchers
     * @param matchers Matchers the editor should match
     */
    @SuppressWarnings("unchecked")
	public AbstractEditor(Matcher<IEditorPart>... matchers) {
    	this(EditorPartLookup.getInstance().getEditor(matchers));
    }
    
    protected AbstractEditor(IEditorPart part){
    	this.editorPart = part;
    	activate();
    }

    @Override
    public String getTitle() {
        return WorkbenchPartHandler.getInstance().getTitle(editorPart);
    }

    @Override
    public String getTitleToolTip() {
        return WorkbenchPartHandler.getInstance().getTitleToolTip(editorPart);
    }

    @Override
    public boolean isDirty() {
        return EditorHandler.getInstance().isDirty(editorPart);
    }

    @Override
    public void save() {
    	activate();
    	log.info("Save editor");
        EditorHandler.getInstance().save(editorPart);
    }

    @Override
    public void close() {
        close(false);
    }

    @Override
    public void close(final boolean save) {
    	activate();
    	if (save){
    		log.info("Close editor and save it");
    	} else {
    		log.info("Close editor and do not save it");
    	}
        EditorHandler.getInstance().close(save, editorPart);
    }
    
    @Override
    public boolean isActive() {
        return EditorHandler.getInstance().isActive(editorPart);
    }

    @Override
    public void maximize() {
    	activate();
    	log.info("Maximize editor");
        WorkbenchPartHandler.getInstance()
                .performAction(ActionFactory.MAXIMIZE);
    }

    @Override
    public void minimize() {
    	activate();
    	log.info("Minimize editor");
        WorkbenchPartHandler.getInstance()
                .performAction(ActionFactory.MINIMIZE);
    }

    /**
     * @see org.jboss.reddeer.workbench.api.Editor#openContentAssistant()
     */
    @Override
    public ContentAssistant openContentAssistant() {
    	activate();
    	log.info("Open editor's content assistant");
        AbstractWait.sleep(TimePeriod.SHORT);
        Shell[] shells1 = ShellLookup.getInstance().getShells();
        IBindingService bindingService = (IBindingService) PlatformUI
                .getWorkbench().getAdapter(IBindingService.class);
        TriggerSequence[] sequence = bindingService
                .getActiveBindingsFor(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
        if (sequence.length > 0 && sequence[0].getTriggers().length > 0) {
            if (sequence[0].getTriggers()[0] instanceof KeyStroke) {
                KeyStroke k = ((KeyStroke) sequence[0].getTriggers()[0]);
                KeyboardFactory.getKeyboard().invokeKeyCombination(
                        k.getModifierKeys(), k.getNaturalKey());
            } else {
                throw new WorkbenchLayerException(
                        "Unable to find key combination which invokes Content Assistant");
            }
        } else {
            throw new WorkbenchLayerException(
                    "Unable to find key combination which invokes Content Assistant");
        }
        ContentAssistantShellIsOpened caw = new ContentAssistantShellIsOpened(
                shells1);
        new WaitUntil(caw);
        return new ContentAssistant(caw.getContentAssistTable());
    }

    /**
     * @see org.jboss.reddeer.workbench.api.Editor#openOpenOnAssistant()
     */
    @Override
    public ContentAssistant openOpenOnAssistant() {
    	activate();
    	log.info("Open editor's open on assistant");
        AbstractWait.sleep(TimePeriod.SHORT);
        Shell[] shells1 = ShellLookup.getInstance().getShells();
        new ShellMenu("Navigate", "Open Hyperlink").select();
        try {
            ContentAssistantShellIsOpened caw = new ContentAssistantShellIsOpened(
                    shells1);
            new WaitUntil(caw);
            return new ContentAssistant(caw.getContentAssistTable());
        } catch (WaitTimeoutExpiredException ex) {
            return null;
        }
    }

    /**
     * @see org.jboss.reddeer.workbench.api.Editor#openQuickFixContentAssistant()
     */
    @Override
    public ContentAssistant openQuickFixContentAssistant() {
    	activate();
    	log.info("Open editor's quick fix assistant");
        AbstractWait.sleep(TimePeriod.SHORT);
        Shell[] shells1 = ShellLookup.getInstance().getShells();
        IBindingService bindingService = (IBindingService) PlatformUI
                .getWorkbench().getAdapter(IBindingService.class);
        TriggerSequence[] sequence = bindingService
                .getActiveBindingsFor(ITextEditorActionDefinitionIds.QUICK_ASSIST);
        if (sequence.length > 0 && sequence[0].getTriggers().length > 0) {
            if (sequence[0].getTriggers()[0] instanceof KeyStroke) {
                KeyStroke k = ((KeyStroke) sequence[0].getTriggers()[0]);
                KeyboardFactory.getKeyboard().invokeKeyCombination(
                        k.getModifierKeys(), k.getNaturalKey());
            } else {
                throw new WorkbenchLayerException(
                        "Unable to find key combination which invokes Quickfix Content Assistant");
            }
        } else {
            throw new WorkbenchLayerException(
                    "Unable to find key combination which invokes Quickfix Content Assistant");
        }
        ContentAssistantShellIsOpened caw = new ContentAssistantShellIsOpened(
                shells1);
        new WaitUntil(caw);
        return new ContentAssistant(caw.getContentAssistTable());
    }

    /**
     * @see org.jboss.reddeer.workbench.api.Editor#getMarkers()
     */
    @Override
    public List<Marker> getMarkers() {
    	activate();
        return EditorHandler.getInstance().getMarkers(editorPart);
    }

    /**
     * {@link WorkbenchPart.restore}
     */
    @Override
    public void restore() {
        // in order to restore maximized window maximized action has to be
        // called
        activate();
        log.info("Restore editor");
        WorkbenchPartHandler.getInstance()
                .performAction(ActionFactory.MAXIMIZE);
    }

    /**
     * {@link WorkbenchPart.activate}
     */
    @Override
    public void activate() {
    	log.info("Activate editor with title " + getTitle());
        EditorHandler.getInstance().activate(editorPart);
    }

    /**
     * {@link Editor#closeAll}
     * @see org.jboss.reddeer.workbench.api.Editor#closeAll(boolean)
     */
	@Override
	public void closeAll(boolean save) {
		if (save){
			log.info("Close all editors and save them");			
		} else {
			log.info("Close all editors and do not save them");
		}
		EditorHandler.getInstance().closeAll(save);
		
	}
	
    protected IEditorPart getEditorPart() {
        return editorPart;
    }
}