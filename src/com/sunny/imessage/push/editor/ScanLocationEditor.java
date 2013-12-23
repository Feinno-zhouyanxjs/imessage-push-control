package com.sunny.imessage.push.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class ScanLocationEditor extends EditorPart {

	public final static String ID = "com.sunny.imessage.push.editor.ScanLocationEditor";

	public ScanLocationEditor() {
	}

	@Override
	public void doSave(IProgressMonitor arg0) {
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		this.setInput(input);
		this.setSite(site);
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite composite) {
		composite.setLayout(new GridLayout(1, false));
		// TODO Auto-generated method stub

	}

	@Override
	public void setFocus() {
	}

}
