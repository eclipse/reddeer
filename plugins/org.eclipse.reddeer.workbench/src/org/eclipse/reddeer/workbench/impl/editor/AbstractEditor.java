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
package org.eclipse.reddeer.workbench.impl.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.exception.WaitTimeoutExpiredException;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.wait.AbstractWait;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.core.handler.MenuHandler;
import org.eclipse.reddeer.core.lookup.ShellLookup;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.jface.text.contentassist.ContentAssistant;
import org.eclipse.reddeer.swt.api.Menu;
import org.eclipse.reddeer.swt.impl.menu.ShellMenu;
import org.eclipse.reddeer.swt.keyboard.KeyboardFactory;
import org.eclipse.reddeer.workbench.api.Editor;
import org.eclipse.reddeer.workbench.api.EditorFile;
import org.eclipse.reddeer.workbench.condition.ContentAssistantShellIsOpened;
import org.eclipse.reddeer.workbench.core.lookup.EditorPartLookup;
import org.eclipse.reddeer.workbench.exception.WorkbenchLayerException;
import org.eclipse.reddeer.workbench.handler.EditorHandler;
import org.eclipse.reddeer.workbench.handler.WorkbenchPartHandler;
import org.eclipse.reddeer.workbench.matcher.EditorPartTitleMatcher;

/**
 * Abstract class for all Editor implementations.
 * @author rawagner
 */
public abstract class AbstractEditor implements Editor {

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

    @Override
    public ContentAssistant openContentAssistant() {
    	Menu assistMenu = null;
    	try{
    		assistMenu = new ShellMenu("Edit", "Content Assist");
    	}catch (RedDeerException e) {
    		log.info("Content assist menu not found, open via keyboard shortcut");
    		return openContentAssistantViaKeyboard();
		}
    	boolean hasAssistMenuChildren = MenuHandler.getInstance().getMenuFromMenuItem(assistMenu.getSWTWidget()) != null;
    	
    	if (hasAssistMenuChildren){
    		return openContentAssistant(ContentAssistantEnum.DEFAULT);
    	} 
    	return openContentAssistant("Edit", "Content Assist");
    }
    
	public ContentAssistant openContentAssistant(ContentAssistantEnum assistantType) {
		return openContentAssistant(assistantType.getLabel());
	}

	public ContentAssistant openContentAssistant(String assistantLabel) {
		return openContentAssistant("Edit", "Content Assist", assistantLabel);
	}
	
    private ContentAssistant openContentAssistant(String... assistantMenuPath) {
    	String assistantLabel = assistantMenuPath[assistantMenuPath.length - 1];
    	
    	activate();
    	log.info("Open editor's " + assistantLabel + " content assistant");
        AbstractWait.sleep(TimePeriod.SHORT);
        
        
        ShellMenu menu;
        try {
        	menu = new ShellMenu(assistantMenuPath);     
        } catch (RedDeerException e) {
        	throw new WorkbenchLayerException("Content assistant " + assistantLabel + " does not exist!", e);
        	
		}
        if(!menu.isEnabled()){
           	throw new WorkbenchLayerException("Content assistant " + assistantLabel + " is disabled!");
        }
        Shell[] shells = ShellLookup.getInstance().getShells();
        menu.select();  
        return getContentAssistantShell(shells);
    }
    
    private ContentAssistant openContentAssistantViaKeyboard(){
    	Shell[] shells = ShellLookup.getInstance().getShells();
    	IBindingService bindingService = (IBindingService) PlatformUI.getWorkbench().getAdapter(IBindingService.class);
        TriggerSequence[] sequence = 
        		 bindingService.getActiveBindingsFor(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
         
         if (sequence.length > 0 && sequence[0].getTriggers().length > 0) {
             if (sequence[0].getTriggers()[0] instanceof KeyStroke) {
                 KeyStroke k = ((KeyStroke) sequence[0].getTriggers()[0]);
                 KeyboardFactory.getKeyboard().invokeKeyCombination(k.getModifierKeys(), k.getNaturalKey());
             } else {
                 throw new WorkbenchLayerException("Unable to find key combination which invokes Content Assistant");
             }
         } else {
             throw new WorkbenchLayerException("Unable to find key combination which invokes Content Assistant");
         }
         return getContentAssistantShell(shells);
    }
    
    private ContentAssistant getContentAssistantShell(Shell[] shells){
    	ContentAssistantShellIsOpened caw = new ContentAssistantShellIsOpened(shells);
        new WaitUntil(caw);
        return new ContentAssistant(caw.getContentAssistTable());
    }
    
	/**
	 * Retrieves callable content assistants.
	 * @return List of assistants enums.
	 */
	public List<ContentAssistantEnum> getAvailableContentAssistants() {
    	activate();
        AbstractWait.sleep(TimePeriod.SHORT);

        List<Menu> availableMenus = new ShellMenu("Edit", "Content Assist").getAvailableChildItems();
        List<ContentAssistantEnum> result = new ArrayList<>(availableMenus.size());

        for (Menu availableMenu : availableMenus) {
        	String label = MenuHandler.getInstance().getLabelFromText(availableMenu.getText());
			ContentAssistantEnum assistantType = ContentAssistantEnum.resolveLabel(label);
			if(assistantType != null)
				result.add(assistantType);
		}
        
        return result;
    }

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

    @Override
    public List<Marker> getMarkers() {
    	activate();
        return EditorHandler.getInstance().getMarkers(editorPart);
    }

    @Override
    public void restore() {
        // in order to restore maximized window maximized action has to be
        // called
        activate();
        log.info("Restore editor");
        WorkbenchPartHandler.getInstance()
                .performAction(ActionFactory.MAXIMIZE);
    }

    @Override
    public void activate() {
    	log.info("Activate editor with title " + getTitle());
        EditorHandler.getInstance().activate(editorPart);
    }

	@Override
	public void closeAll(boolean save) {
		if (save){
			log.info("Close all editors and save them");			
		} else {
			log.info("Close all editors and do not save them");
		}
		EditorHandler.getInstance().closeAll(save);
		
	}
	
	@Override
	public ContentAssistant getAutoContentAssistant(Runnable execute){
        Shell[] shells1 = ShellLookup.getInstance().getShells();
        execute.run();
        ContentAssistantShellIsOpened caw = new ContentAssistantShellIsOpened(
                shells1);
        new WaitUntil(caw,TimePeriod.DEFAULT,false);
        return caw.getContentAssistTable() == null ? null : new ContentAssistant(caw.getContentAssistTable());
	}

	@Override
	public EditorFile getAssociatedFile() {
		IEditorInput editorInput = editorPart.getEditorInput();
		IFile iFile = editorInput.getAdapter(IFile.class);
		if (iFile == null) {
			throw new WorkbenchLayerException("No file is associated to the editor");
		}
		return new DefaultEditorFile(iFile);
	}

	public IEditorPart getEditorPart() {
		return editorPart;
	}

    public enum ContentAssistantEnum {
    	DEFAULT("Default"),
    	JAVA_TYPE("Java Type Proposals"),
    	JAVA_NON_TYPE("Java Non-Type Proposals"),
    	JAVA("Java Proposals"),
    	TEMPLATE("Template Proposals"),
    	WORD("Word Proposals"),
    	SWT_TEMPLATE("SWT Template Proposals"),
    	JAXB("JAXB Proposals"),
    	JPA("JPA Proposals"),
    	JAX_RS("JAX-WS Proposals"),
    	API_TOOLS("API Tools Proposals"),
    	ADAPTIVE_TEMPLATE_RECOMMENDERS("Adaptive Template Proposals (Code Recommenders)"),
    	CHAIN_RECOMMENDERS("Chain Proposals (Code Recommenders)"),
    	JAVA_RECOMMENDERS("Java Proposals (Code Recommenders)"),
    	PARAMETER_HINTS("Parameter Hints");

    	private String label;

		public String getLabel() {
			return label;
		}

    	ContentAssistantEnum(String label) { 
    		this.label = label; 
    	}

    	public static ContentAssistantEnum resolveLabel(String label){
    		for (ContentAssistantEnum value : ContentAssistantEnum.values())
    			if (value.getLabel().equals(label)) 
    				return value;				
    		
    		return null;
    	}

    }
}
