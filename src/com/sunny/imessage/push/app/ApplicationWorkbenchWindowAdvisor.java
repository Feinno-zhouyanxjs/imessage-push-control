package com.sunny.imessage.push.app;

import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.internal.UIPlugin;
import org.eclipse.ui.internal.part.NullEditorInput;
import org.eclipse.ui.presentations.AbstractPresentationFactory;

import com.sunny.imessage.push.editor.SendEditor;
import com.sunny.imessage.push.web.WebServer;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(1024, 640));
		configurer.setShowCoolBar(false);
		configurer.setShowStatusLine(false);
		configurer.setShowStatusLine(true);
		configurer.setTitle("IMessage Push Control"); //$NON-NLS-1$
		// 打開全屏
		// getWindowConfigurer().getWindow().getShell().setMaximized(true);

	}

	@Override
	public void postWindowOpen() {
		try {
			UIPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new NullEditorInput(), SendEditor.ID, true);
		} catch (PartInitException e) {
			e.printStackTrace();
		}

		WebServer server = new WebServer();
		try {
			server.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}

		getWindowConfigurer().getActionBarConfigurer().getStatusLineManager().setMessage("服务启动成功");

		// DBServer db = new DBServer();
		// try {
		// db.init();
		// } catch (ClassNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		super.postWindowOpen();
	}

}
