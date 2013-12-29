package com.sunny.imessage.push.editor;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jetty.util.ConcurrentHashSet;
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

import com.sunny.imessage.push.file.FileUtils;

public class UniqueEditor extends EditorPart {
	public UniqueEditor() {
	}

	public final static String ID = "com.sunny.imessage.push.editor.UniqueEditor";

	private Text fileText;
	private StyledText styledText;
	private Button startBut;

	private Logger logger = LoggerFactory.getLogger(getClass());

	private LinkedBlockingQueue<String> files = new LinkedBlockingQueue<String>();
	private ConcurrentHashSet<Long> phones = new ConcurrentHashSet<Long>();

	private class PrintFilePath implements Runnable {

		private String filePath;

		/**
		 * @param filePath
		 */
		public PrintFilePath(String filePath) {
			super();
			this.filePath = filePath;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			styledText.append("处理" + filePath + "完成\n");

		}

	}

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
		composite.setLayout(new GridLayout(11, false));

		Label label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText("文件");

		fileText = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		fileText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button button_1 = new Button(composite, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(getSite().getShell(), SWT.OPEN);
				String file = fileDialog.open();
				if (file != null)
					fileText.setText(file);
			}
		});
		button_1.setText("文件");

		final Button button = new Button(composite, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final String filePath = fileText.getText();
				if (filePath == null || filePath.equals("")) {
					styledText.append("请选择文件添加\n");
					return;
				} else {
					files.add(filePath);
					styledText.append("添加成功\n");
				}
			}
		});
		button.setText("添加");

		Button button_3 = new Button(composite, SWT.NONE);
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				files.clear();
				styledText.append("任务已清理\n");
			}
		});
		button_3.setText("清空");

		startBut = new Button(composite, SWT.NONE);
		startBut.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final String text = "。";
				if (text == null || text.equals("")) {
					styledText.append("发送内容不能为空\n");
					return;
				}
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
							String filePath = null;
							while ((filePath = files.poll()) != null) {
								List<Long> rs = FileUtils.readPhones(filePath);
								phones.addAll(rs);
								Display.getDefault().asyncExec(new PrintFilePath(filePath));
							}
							Display.getDefault().syncExec(new Runnable() {

								@Override
								public void run() {
									styledText.append("任务完成\n");
									startBut.setEnabled(true);
								}

							});
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
				files.clear();
				styledText.append("任务停止成功，请等待完成\n");
			}
		});
		button_2.setText("停止");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

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
		new Label(composite, SWT.NONE);

		styledText = new StyledText(composite, SWT.BORDER | SWT.V_SCROLL);
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 11, 1));

		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(new GridLayout(1, false));
		composite_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 11, 1));

		Button button_4 = new Button(composite_1, SWT.NONE);
		button_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(getSite().getShell(), SWT.SAVE);
				String file = fileDialog.open();
				try {
					FileUtils.writePhones(phones, file);
				} catch (IOException e1) {
					logger.error("", e1);
				}
			}
		});
		button_4.setText("导出成功");
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
		files.clear();
		super.dispose();
	}

}