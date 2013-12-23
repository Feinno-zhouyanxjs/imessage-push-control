package com.sunny.imessage.push.app;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	public final static String ID = "com.sunny.imessage.push.app.Perspective";

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(true);
		layout.setFixed(true);

		// String editorArea = layout.getEditorArea();
		// layout.addStandaloneView(NaviView.ID, false, IPageLayout.LEFT, 0.25f, editorArea);
	}
}
