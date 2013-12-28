package com.sunny.imessage.push.editor;

import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunny.imessage.push.action.GetPhoneNum;
import com.sunny.imessage.push.file.FileUtils;
import com.sunny.imessage.push.service.ScanCustomService;

public class ScanCustomEditor extends EditorPart {
	public ScanCustomEditor() {
	}

	public final static String ID = "com.sunny.imessage.push.editor.ScanCustomEditor";
	private Text text_1;
	private StyledText styledText;
	private Button startBut;

	private ScanCustomService service;

	private Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		this.setInput(input);
		this.setSite(site);
		service = new ScanCustomService();
		GetPhoneNum.instance.setService(service);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#isDirty()
	 */
	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite composite) {
		composite.setLayout(new GridLayout(10, false));

		Label label_1 = new Label(composite, SWT.NONE);
		label_1.setText("自定义号段");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		text_1 = new Text(composite, SWT.BORDER | SWT.V_SCROLL);
		GridData gd_text_1 = new GridData(SWT.FILL, SWT.CENTER, true, false, 10, 1);
		gd_text_1.heightHint = 100;
		text_1.setLayoutData(gd_text_1);

		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayout(new GridLayout(4, false));
		composite_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 10, 1));

		final Button button = new Button(composite_2, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final String interval = text_1.getText();
				if (interval == null || interval.equals("")) {
					styledText.append("请添加号码段\n");
					return;
				} else {
					service.addinterval(interval);
					styledText.append("添加号码段成功\n");
				}
			}
		});
		button.setText("添加");

		Button button_3 = new Button(composite_2, SWT.NONE);
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				service.stop();
				styledText.append("任务已清理\n");
			}
		});
		button_3.setText("清空");

		startBut = new Button(composite_2, SWT.NONE);
		startBut.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							Display.getDefault().syncExec(new Runnable() {

								@Override
								public void run() {
									styledText.setText("");
								}

							});
							service.start(styledText, "。", startBut);
						} catch (IOException e) {
							logger.error("", e);
						}

					}

				});
				t.start();
				startBut.setEnabled(false);
			}
		});
		startBut.setText("开始");

		Button button_2 = new Button(composite_2, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				service.stop();
				startBut.setEnabled(true);
				styledText.append("任务停止成功\n");
			}
		});
		button_2.setText("停止");

		Label label_2 = new Label(composite, SWT.NONE);
		label_2.setText("输出");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		styledText = new StyledText(composite, SWT.BORDER | SWT.V_SCROLL);
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 10, 1));

		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(new GridLayout(2, false));
		composite_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 10, 1));

		Button btnNewButton = new Button(composite_1, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnNewButton.setText("导出成功");

		Button btnshibai = new Button(composite_1, SWT.NONE);
		btnshibai.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(getSite().getShell(), SWT.SAVE);
				String file = fileDialog.open();
				try {
					FileUtils.writePhones(service.getFailed(), file);
				} catch (IOException e1) {
					logger.error("", e1);
				}
			}
		});
		btnshibai.setText("导出失败");
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		service.stop();
		super.dispose();
	}

}
