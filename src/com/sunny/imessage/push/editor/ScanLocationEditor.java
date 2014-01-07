package com.sunny.imessage.push.editor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunny.imessage.push.action.GetPhoneNum;
import com.sunny.imessage.push.file.FileUtils;
import com.sunny.imessage.push.service.ScanCustomService;
import com.sunny.imessage.push.utils.StringUtils;

public class ScanLocationEditor extends EditorPart {
	public ScanLocationEditor() {
	}

	public final static String ID = "com.sunny.imessage.push.editor.ScanLocationEditor";
	private StyledText styledText;
	private Button startBut;

	private ScanCustomService service;

	private Logger logger = LoggerFactory.getLogger(getClass());

	private Combo shi;

	private Combo sheng;

	private Combo combo;

	private List<String> setList = new ArrayList<String>();

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
		label_1.setText("区域选择");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		Composite composite_3 = new Composite(composite, SWT.NONE);
		composite_3.setLayout(new GridLayout(6, false));
		composite_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 10, 1));

		Label lblSheng = new Label(composite_3, SWT.NONE);
		lblSheng.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSheng.setText("省");

		sheng = new Combo(composite_3, SWT.READ_ONLY);
		sheng.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String si = sheng.getText();
				File[] fs = FileUtils.files(StringUtils.getLocation() + "data" + File.separatorChar + si);
				addItem(shi, fs);
			}
		});
		File[] fs = FileUtils.files(StringUtils.getLocation() + "data");
		addItem(sheng, fs);
		sheng.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label label = new Label(composite_3, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText("市");

		shi = new Combo(composite_3, SWT.READ_ONLY);
		shi.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String si = shi.getText();
				File[] fs = FileUtils.files(StringUtils.getLocation() + "data" + File.separatorChar + sheng.getText() + File.separatorChar + si);
				addItem(combo, fs);
			}
		});
		shi.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label label_3 = new Label(composite_3, SWT.NONE);
		label_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_3.setText("运营商");

		combo = new Combo(composite_3, SWT.READ_ONLY);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayout(new GridLayout(4, false));
		composite_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 10, 1));

		final Button button = new Button(composite_2, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (sheng.getText().equals("") || shi.getText().equals("") || combo.getText().equals(""))
					return;

				String file = StringUtils.getLocation() + "data" + File.separatorChar + sheng.getText() + File.separatorChar + shi.getText() + File.separatorChar + combo.getText();
				try {
					List<Long> lst = FileUtils.readPhones(file);
					if (setList.size() + lst.size() > 5000) {
						styledText.append("添加所选运营商将累计超过5000万号码，放弃添加\n");
						return;
					}
					for (long phone : lst) {
						String start = phone + "0000";
						String end = phone + "9999";
						if (start.length() == 11) {
							if (!start.startsWith("86")) {
								start = "86" + start;
								end = "86" + end;
							}

							String section = start + "-" + end + ",";
							setList.add(section);
						} else {
							logger.error(phone + " error");
						}
					}
					styledText.append("累计添加" + setList.size() + "万号码\n");
				} catch (IOException e1) {
					logger.error("", e1);
					styledText.append("读取号段失败\n");
				}
			}
		});
		button.setText("添加");

		Button button_3 = new Button(composite_2, SWT.NONE);
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setList.clear();
				service.stop();
				styledText.append("任务已清理\n");
			}
		});
		button_3.setText("清空");

		startBut = new Button(composite_2, SWT.NONE);
		startBut.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				StringBuffer sb = new StringBuffer();
				for (String str : setList) {
					sb.append(str);
				}
				setList.clear();
				service.addinterval(sb.toString());

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
				setList.clear();
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
				FileDialog fileDialog = new FileDialog(getSite().getShell(), SWT.SAVE);
				String file = fileDialog.open();
				try {
					FileUtils.writePhones(service.getSuccess(), file);
				} catch (IOException e1) {
					logger.error("", e1);
				}
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

	private void addItem(Combo combo, File[] fs) {
		combo.removeAll();
		for (File f : fs) {
			String name = f.getName();
			if (name.startsWith("."))
				continue;
			combo.add(f.getName());
		}
		// if (fs.length > 0)
		// combo.select(0);
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
