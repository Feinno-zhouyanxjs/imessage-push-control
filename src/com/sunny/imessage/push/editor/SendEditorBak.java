/**
 * 
 */
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

/**
 * 
 * 发送
 * 
 * Create on Dec 22, 2013 2:05:46 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class SendEditorBak extends EditorPart {
	public SendEditorBak() {
	}

	public final static String ID = "com.sunny.imessage.push.editor.SendEditor";
	private Text fileText;
	private Text text_1;
	private StyledText styledText;
	private Button startBut;

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
		composite.setLayout(new GridLayout(8, false));

		Label label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText("文件");

		fileText = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		fileText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Button button = new Button(composite, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(getSite().getShell(), SWT.OPEN);
				String file = fileDialog.open();
				if (file != null)
					fileText.setText(file);

				final String filePath = fileText.getText().trim();
				if (filePath == null || filePath.equals("")) {
					return;
				} else {
					GetPhoneNum.instance.addFile(filePath);
					styledText.append("添加文件成功\n");
				}
			}
		});
		button.setText("添加");

		startBut = new Button(composite, SWT.NONE);
		startBut.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				styledText.setText("");
				final String text = text_1.getText();
				if (text == null || text.equals("")) {
					styledText.setText("发送内容不能为空");
					return;
				}
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							GetPhoneNum.instance.start(styledText, text, startBut);
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

		Button button_2 = new Button(composite, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				GetPhoneNum.instance.stop();
				startBut.setEnabled(true);
			}
		});
		button_2.setText("停止");

		Button button_3 = new Button(composite, SWT.NONE);
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(getSite().getShell(), SWT.SAVE);
				String file = fileDialog.open();
				try {
					FileUtils.writePhones(GetPhoneNum.instance.getSuccess(), file);
				} catch (IOException e1) {
					logger.error("", e1);
				}
			}
		});
		button_3.setText("导出成功");

		Button btnshibai = new Button(composite, SWT.NONE);
		btnshibai.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(getSite().getShell(), SWT.SAVE);
				String file = fileDialog.open();
				try {
					FileUtils.writePhones(GetPhoneNum.instance.getFailed(), file);
				} catch (IOException e1) {
					logger.error("", e1);
				}
			}
		});
		btnshibai.setText("导出失败");
		new Label(composite, SWT.NONE);

		Label label_1 = new Label(composite, SWT.NONE);
		label_1.setText("内容");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		text_1 = new Text(composite, SWT.BORDER | SWT.V_SCROLL);
		GridData gd_text_1 = new GridData(SWT.FILL, SWT.CENTER, true, false, 8, 1);
		gd_text_1.heightHint = 171;
		text_1.setLayoutData(gd_text_1);

		Label label_2 = new Label(composite, SWT.NONE);
		label_2.setText("输出");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		styledText = new StyledText(composite, SWT.BORDER | SWT.V_SCROLL);
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 8, 1));
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
		GetPhoneNum.instance.stop();
		super.dispose();
	}

}
