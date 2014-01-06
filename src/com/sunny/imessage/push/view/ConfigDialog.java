/**
 * 
 */
package com.sunny.imessage.push.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

/**
 * 
 * 配置虚假参数窗口
 * 
 * Create on Dec 30, 2013 8:18:58 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class ConfigDialog extends Dialog {

	protected Shell shell;

	public static int sendCount = 0;
	public static int taskCount = 0;
	public static int successCount = 0;
	public static int waitingTime = 0;

	private Spinner sendSpin;
	private Spinner taskSpin;
	private Spinner succSpin;
	private Spinner waiting;

	/**
	 * @param parent
	 * @param style
	 */
	public ConfigDialog(Shell parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param parent
	 */
	public ConfigDialog(Shell parent) {
		super(parent);
	}

	public void open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	protected void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setSize(252, 201);
		shell.setLayout(new GridLayout(22, false));
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

		Label label = new Label(shell, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText("下发数");

		sendSpin = new Spinner(shell, SWT.BORDER);
		sendSpin.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 20, 1));
		new Label(shell, SWT.NONE);

		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setText("任务数");

		taskSpin = new Spinner(shell, SWT.BORDER);
		taskSpin.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 20, 1));
		new Label(shell, SWT.NONE);

		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_2.setText("成功数");

		succSpin = new Spinner(shell, SWT.BORDER);
		succSpin.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 20, 1));
		new Label(shell, SWT.NONE);

		Label label_3 = new Label(shell, SWT.NONE);
		label_3.setText("下发延时(秒)");

		waiting = new Spinner(shell, SWT.BORDER);
		waiting.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 20, 1));
		new Label(shell, SWT.NONE);

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 21, 1));

		Button button = new Button(composite, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sendCount = Integer.valueOf(sendSpin.getText());
				taskCount = Integer.valueOf(taskSpin.getText());
				successCount = Integer.valueOf(succSpin.getText());
				waitingTime = Integer.valueOf(waiting.getText());
				shell.close();
			}
		});
		button.setText("确定");

		Button button_1 = new Button(composite, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		button_1.setText("取消");
	}
}
